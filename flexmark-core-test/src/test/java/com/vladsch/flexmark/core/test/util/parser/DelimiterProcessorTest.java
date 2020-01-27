package com.vladsch.flexmark.core.test.util.parser;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.InlineParser;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.core.delimiter.Delimiter;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.RenderingTestCase;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.DelimitedNode;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

final public class DelimiterProcessorTest extends RenderingTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(TestUtils.NO_FILE_EOL, false)
            .toImmutable();
    final private static Parser PARSER = Parser.builder(OPTIONS).customDelimiterProcessor(new AsymmetricDelimiterProcessor()).build();
    final private static HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).nodeRendererFactory(new UpperCaseNodeRendererFactory()).build();

    @Test
    public void delimiterProcessorWithInvalidDelimiterUse() {
        Parser parser = Parser.builder(OPTIONS)
                .customDelimiterProcessor(new CustomDelimiterProcessor(':', 0))
                .customDelimiterProcessor(new CustomDelimiterProcessor(';', -1))
                .build();
        assertEquals("<p>:test:</p>\n", RENDERER.render(parser.parse(":test:")));
        assertEquals("<p>;test;</p>\n", RENDERER.render(parser.parse(";test;")));
    }

    @Test
    public void asymmetricDelimiter() {
        assertRendering("{foo} bar", "<p>FOO bar</p>\n");
        assertRendering("f{oo ba}r", "<p>fOO BAr</p>\n");
        assertRendering("{{foo} bar", "<p>{FOO bar</p>\n");
        assertRendering("{foo}} bar", "<p>FOO} bar</p>\n");
        assertRendering("{{foo} bar}", "<p>FOO BAR</p>\n");
        assertRendering("{foo bar", "<p>{foo bar</p>\n");
        assertRendering("foo} bar", "<p>foo} bar</p>\n");
        assertRendering("}foo} bar", "<p>}foo} bar</p>\n");
        assertRendering("{foo{ bar", "<p>{foo{ bar</p>\n");
        assertRendering("}foo{ bar", "<p>}foo{ bar</p>\n");
    }

    @Override
    public @Nullable DataHolder options(@NotNull String option) {
        return null;
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = DataSet.aggregate(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, PARSER, RENDERER, true);
    }

    private static class CustomDelimiterProcessor implements DelimiterProcessor {
        final private char delimiterChar;
        final private int delimiterUse;

        CustomDelimiterProcessor(char delimiterChar, int delimiterUse) {
            this.delimiterChar = delimiterChar;
            this.delimiterUse = delimiterUse;
        }

        @Override
        public char getOpeningCharacter() {
            return delimiterChar;
        }

        @Override
        public char getClosingCharacter() {
            return delimiterChar;
        }

        @Override
        public int getMinLength() {
            return 1;
        }

        @Override
        public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
            return delimiterUse;
        }

        @Override
        public boolean canBeOpener(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
            return leftFlanking;
        }

        @Override
        public boolean canBeCloser(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
            return rightFlanking;
        }

        @Override
        public boolean skipNonOpenerCloser() {
            return false;
        }

        @Override
        public Node unmatchedDelimiterNode(InlineParser inlineParser, DelimiterRun delimiter) {
            return null;
        }

        @Override
        public void process(Delimiter opener, Delimiter closer, int delimitersUsed) {

        }
    }

    private static class AsymmetricDelimiterProcessor implements DelimiterProcessor {

        AsymmetricDelimiterProcessor() {}

        @Override
        public char getOpeningCharacter() {
            return '{';
        }

        @Override
        public char getClosingCharacter() {
            return '}';
        }

        @Override
        public int getMinLength() {
            return 1;
        }

        @Override
        public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
            return 1;
        }

        @Override
        public Node unmatchedDelimiterNode(InlineParser inlineParser, DelimiterRun delimiter) {
            return null;
        }

        @Override
        public boolean canBeOpener(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
            return leftFlanking;
        }

        @Override
        public boolean canBeCloser(String before, String after, boolean leftFlanking, boolean rightFlanking, boolean beforeIsPunctuation, boolean afterIsPunctuation, boolean beforeIsWhitespace, boolean afterIsWhiteSpace) {
            return rightFlanking;
        }

        @Override
        public boolean skipNonOpenerCloser() {
            return false;
        }

        @Override
        public void process(Delimiter opener, Delimiter closer, int delimitersUsed) {
            UpperCaseNode content = new UpperCaseNode(opener.getTailChars(delimitersUsed), BasedSequence.NULL, closer.getLeadChars(delimitersUsed));
            opener.moveNodesBetweenDelimitersTo(content, closer);
        }
    }

    private interface UpperCaseNodeVisitor {
        void visit(UpperCaseNode node);
    }

    private static class UpperCaseNode extends Node implements DelimitedNode {
        protected BasedSequence openingMarker = BasedSequence.NULL;
        protected BasedSequence text = BasedSequence.NULL;
        protected BasedSequence closingMarker = BasedSequence.NULL;

        @NotNull
        @Override
        public BasedSequence[] getSegments() {
            return new BasedSequence[] { openingMarker, text, closingMarker };
        }

        public UpperCaseNode() {
        }

        public UpperCaseNode(BasedSequence chars) {
            super(chars);
        }

        public UpperCaseNode(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
            super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
            this.openingMarker = openingMarker;
            this.text = text;
            this.closingMarker = closingMarker;
        }

        public BasedSequence getOpeningMarker() {
            return openingMarker;
        }

        public void setOpeningMarker(BasedSequence openingMarker) {
            this.openingMarker = openingMarker;
        }

        public BasedSequence getText() {
            return text;
        }

        public void setText(BasedSequence text) {
            this.text = text;
        }

        public BasedSequence getClosingMarker() {
            return closingMarker;
        }

        public void setClosingMarker(BasedSequence closingMarker) {
            this.closingMarker = closingMarker;
        }
    }

    private static class UpperCaseNodeRendererFactory implements NodeRendererFactory {
        UpperCaseNodeRendererFactory() {}

        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new UpperCaseNodeRenderer(options);
        }
    }

    private static class UpperCaseNodeRenderer implements NodeRenderer {
        UpperCaseNodeRenderer(DataHolder options) {
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(UpperCaseNode.class, UpperCaseNodeRenderer.this::render));
            return set;
        }

        private void render(UpperCaseNode node, NodeRendererContext context, HtmlWriter html) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNext()) {
                if (child instanceof Text) {
                    Text text = (Text) child;
                    text.setChars(text.getChars().toUpperCase());
                }
                context.render(child);
            }
        }
    }
}
