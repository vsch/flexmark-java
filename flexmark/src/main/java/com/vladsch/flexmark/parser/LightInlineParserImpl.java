package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;

import java.util.ArrayList;
import java.util.BitSet;
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
    protected final BitSet EXCLUDED_0_TO_SPACE_CHARS;
    protected final BitSet JEKYLL_EXCLUDED_CHARS;
    protected final BitSet PAREN_EXCLUDED_CHARS;
    protected final BitSet PAREN_ESCAPABLE_CHARS;
    protected final BitSet PAREN_QUOTE_CHARS;

    public LightInlineParserImpl(DataHolder dataOptions) {
        this.options = new InlineParserOptions(dataOptions);
        this.myParsing = new Parsing(dataOptions);

        //String EXCLUDED_0_TO_SPACE = options.intellijDummyIdentifier ? "\u0000-\u001e\u0020" : "\u0000-\u0020";
        //String LINK_DESTINATION_MATCHED_PARENS_EXPANDED =
        //        "^(?:" + (options.parseJekyllMacrosInUrls ? ("\\{\\{(?:[^{}\\\\" + EXCLUDED_0_TO_SPACE + "]| |\t)*\\}\\}") + "|" : "")
        //                + (options.spaceInLinkUrls ? "(?:" + ("[^\\\\()" + EXCLUDED_0_TO_SPACE + "]| (?![\"'])") + ")|" : ("[^\\\\()" + EXCLUDED_0_TO_SPACE + "]") + "|") +
        //                ("\\\\" + Escaping.ESCAPABLE) + "|\\\\|\\(|\\))*";

        /*
        hand rolled code is on par or better and no stack overflow:
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 333 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 239 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 79 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalTest) took 302 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 86 ms
longImageLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 634 ms
longLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 70 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalTest) took 389 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalTest) took 58 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalTest) took 7 ms
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 106 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 112 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 63 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 194 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 75 ms
longImageLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 598 ms
longLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 64 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 336 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 60 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 4 ms

       regex:
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 349 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 217 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 79 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalTest) took 299 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 83 ms
StackOverflow longImageLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 101 ms
longLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 114 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalTest) took 247 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalTest) took 86 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalTest) took 9 ms
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 189 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 107 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 44 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 195 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 111 ms
StackOverflow longImageLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 114 ms
StackOverflow longLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 54 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 194 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 48 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 5 ms
`
         */

        // needed for hand rolled link parser
        EXCLUDED_0_TO_SPACE_CHARS = getCharSet('\u0000','\u0020');
        if (options.intellijDummyIdentifier) EXCLUDED_0_TO_SPACE_CHARS.clear(TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR);

        JEKYLL_EXCLUDED_CHARS = getCharSet("{}\\");
        JEKYLL_EXCLUDED_CHARS.or(EXCLUDED_0_TO_SPACE_CHARS);
        JEKYLL_EXCLUDED_CHARS.clear(' ');
        JEKYLL_EXCLUDED_CHARS.clear('\t');

        PAREN_EXCLUDED_CHARS = getCharSet("()\\");
        PAREN_EXCLUDED_CHARS.or(EXCLUDED_0_TO_SPACE_CHARS);

        PAREN_ESCAPABLE_CHARS = getCharSet(Escaping.ESCAPABLE_CHARS);
        PAREN_QUOTE_CHARS = getCharSet("\"'");
    }

    @Override
    public ArrayList<BasedSequence> getCurrentText() {
        if (currentText == null) {
            currentText = new ArrayList<BasedSequence>();
        }

        return currentText;
    }

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

    @Override
    public Document getDocument() {
        return document;
    }

    @Override
    public void setDocument(Document document) {
        this.document = document;
    }

    @Override
    public InlineParserOptions getOptions() {
        return options;
    }

    @Override
    public Parsing getParsing() {
        return myParsing;
    }

    @Override
    public Node getBlock() {
        return block;
    }

    @Override
    public void setBlock(Node block) {
        this.block = block;
    }

    @Override
    public void moveNodes(Node fromNode, Node toNode) {
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
    public void appendText(BasedSequence text) {
        getCurrentText().add(text);
    }

    @Override
    public void appendText(BasedSequence text, int beginIndex, int endIndex) {
        getCurrentText().add(text.subSequence(beginIndex, endIndex));
    }

    @Override
    public void appendNode(Node node) {
        flushTextNode();
        block.appendChild(node);
    }

    // In some cases, we don't want the text to be appended to an existing node, we need it separate
    @Override
    public Text appendSeparateText(BasedSequence text) {
        Text node = new Text(text);
        appendNode(node);
        return node;
    }

    @Override
    public boolean flushTextNode() {
        if (currentText != null) {
            block.appendChild(new Text(SegmentedSequence.of(currentText)));
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
            return '\0';
        }
    }

    @Override
    public char peek(int ahead) {
        if (index + ahead < input.length()) {
            return input.charAt(index + ahead);
        } else {
            return '\0';
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
    @Override
    public BasedSequence toEOL() {
        return match(myParsing.REST_OF_LINE);
    }

    private BitSet getCharSet(CharSequence chars) {
        BitSet charSet = new BitSet(chars.length());
        int iMax = chars.length();
        for (int i = 0; i < iMax; i++) {
            charSet.set(chars.charAt(i));
        }
        return charSet;
    }

    private BitSet getCharSet(char charFrom, char charTo) {
        BitSet charSet = new BitSet();
        for (int i = charFrom; i <= charTo; i++) {
            charSet.set(i);
        }
        return charSet;
    }
}
