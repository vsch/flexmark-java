package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LightInlineParserImpl implements LightInlineParser {

    final protected InlineParserOptions options;
    final protected Parsing myParsing;
    protected Node block;
    protected BasedSequence input;
    protected int index;
    protected ArrayList<BasedSequence> currentText;
    protected Document document;

    public LightInlineParserImpl(DataHolder dataOptions) {
        this.options = new InlineParserOptions(dataOptions);
        this.myParsing = new Parsing(dataOptions);
    }

    @NotNull
    @Override
    public ArrayList<BasedSequence> getCurrentText() {
        if (currentText == null) {
            currentText = new ArrayList<>();
        }

        return currentText;
    }

    @NotNull
    @Override
    public BasedSequence getInput() {
        return input;
    }

    @Override
    public void setInput(BasedSequence input) {
        this.input = input;
    }

    @Override
    public int getIndex() {
        return index;
    }

    @Override
    public void setIndex(int index) {
        this.index = index;
    }

    @NotNull
    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public void setDocument(@NotNull Document document) {
        this.document = document;
    }

    @NotNull
    @Override
    public InlineParserOptions getOptions() {
        return options;
    }

    @NotNull
    @Override
    public Parsing getParsing() {
        return myParsing;
    }

    @NotNull
    @Override
    public Node getBlock() {
        return block;
    }

    @Override
    public void setBlock(@NotNull Node block) {
        this.block = block;
    }

    @Override
    public void moveNodes(@NotNull Node fromNode, @NotNull Node toNode) {
        if (fromNode != toNode) {
            Node next = fromNode.getNext();
            while (next != null) {
                Node nextNode = next.getNext();
                next.unlink();
                fromNode.appendChild(next);
                if (next == toNode) break;
                next = nextNode;
            }
        }

        fromNode.setCharsFromContent();
    }

    @Override
    public void appendText(@NotNull BasedSequence text) {
        getCurrentText().add(text);
    }

    @Override
    public void appendText(@NotNull BasedSequence text, int beginIndex, int endIndex) {
        getCurrentText().add(text.subSequence(beginIndex, endIndex));
    }

    @Override
    public void appendNode(@NotNull Node node) {
        flushTextNode();
        block.appendChild(node);
    }

    // In some cases, we don't want the text to be appended to an existing node, we need it separate
    @NotNull
    @Override
    public Text appendSeparateText(@NotNull BasedSequence text) {
        Text node = new Text(text);
        appendNode(node);
        return node;
    }

    @Override
    public boolean flushTextNode() {
        if (currentText != null) {
            block.appendChild(new Text(SegmentedSequence.create(input, currentText)));
            currentText = null;
            return true;
        }
        return false;
    }

    /**
     * If RE matches at current index in the input, advance index and return the match; otherwise return null.
     *
     * @param re pattern to match
     * @return sequence matched or null
     */
    @Override
    public BasedSequence match(Pattern re) {
        if (index >= input.length()) {
            return null;
        }
        Matcher matcher = re.matcher(input);
        matcher.region(index, input.length());
        boolean m = matcher.find();
        if (m) {
            index = matcher.end();
            MatchResult result = matcher.toMatchResult();
            return input.subSequence(result.start(), result.end());
        } else {
            return null;
        }
    }

    /**
     * If RE matches at current index in the input, advance index and return the match; otherwise return null.
     *
     * @param re pattern to match
     * @return sequence matched or null
     */
    @Override
    public BasedSequence[] matchWithGroups(Pattern re) {
        if (index >= input.length()) {
            return null;
        }
        Matcher matcher = re.matcher(input);
        matcher.region(index, input.length());
        boolean m = matcher.find();
        if (m) {
            index = matcher.end();
            MatchResult result = matcher.toMatchResult();
            int iMax = matcher.groupCount() + 1;
            BasedSequence[] results = new BasedSequence[iMax];
            results[0] = input.subSequence(result.start(), result.end());
            for (int i = 1; i < iMax; i++) {
                if (matcher.group(i) != null) {
                    results[i] = input.subSequence(result.start(i), result.end(i));
                } else {
                    results[i] = null;
                }
            }
            return results;
        } else {
            return null;
        }
    }

    /**
     * If RE matches at current index in the input, advance index and return the match; otherwise return null.
     *
     * @param re pattern to match
     * @return matched matcher or null
     */
    @Override
    public Matcher matcher(Pattern re) {
        if (index >= input.length()) {
            return null;
        }
        Matcher matcher = re.matcher(input);
        matcher.region(index, input.length());
        boolean m = matcher.find();
        if (m) {
            index = matcher.end();
            return matcher;
        } else {
            return null;
        }
    }

    /**
     * @return the char at the current input index, or {@code '\0'} in case there are no more characters.
     */
    @Override
    public char peek() {
        if (index < input.length()) {
            return input.charAt(index);
        } else {
            return SequenceUtils.NUL;
        }
    }

    @Override
    public char peek(int ahead) {
        if (index + ahead < input.length()) {
            return input.charAt(index + ahead);
        } else {
            return SequenceUtils.NUL;
        }
    }

    /**
     * Parse zero or more space characters, including at most one newline and zero or more spaces.
     *
     * @return true
     */
    @Override
    public boolean spnl() {
        match(myParsing.SPNL);
        return true;
    }

    /**
     * Parse zero or more non-indent spaces
     *
     * @return true
     */
    @Override
    public boolean nonIndentSp() {
        match(myParsing.SPNI);
        return true;
    }

    /**
     * Parse zero or more spaces
     *
     * @return true
     */
    @Override
    public boolean sp() {
        match(myParsing.SP);
        return true;
    }

    /**
     * Parse zero or more space characters, including at one newline.
     *
     * @return true
     */
    @Override
    public boolean spnlUrl() {
        return match(myParsing.SPNL_URL) != null;
    }

    /**
     * Parse to end of line, including EOL
     *
     * @return characters parsed or null if no end of line
     */
    @Nullable
    @Override
    public BasedSequence toEOL() {
        return match(myParsing.REST_OF_LINE);
    }
}
