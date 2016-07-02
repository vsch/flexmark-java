package com.vladsch.flexmark.test;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderHandler;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.internal.util.sequence.SubSequence;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.DelimiterProcessor;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class DelimiterProcessorTest extends RenderingTestCase {

    private static final Parser PARSER = Parser.builder().customDelimiterProcessor(new AsymmetricDelimiterProcessor()).build();
    private static final HtmlRenderer RENDERER = HtmlRenderer.builder().nodeRendererFactory(new UpperCaseNodeRendererFactory()).build();

    @Override
    protected SpecExample example() {
        return null;
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
    protected Parser parser() {
        return PARSER;
    }

    @Override
    protected HtmlRenderer renderer() {
        return RENDERER;
    }

    private static class AsymmetricDelimiterProcessor implements DelimiterProcessor {

        @Override
        public char getOpeningDelimiterChar() {
            return '{';
        }

        @Override
        public char getClosingDelimiterChar() {
            return '}';
        }

        @Override
        public int getMinDelimiterCount() {
            return 1;
        }

        @Override
        public int getDelimiterUse(int openerCount, int closerCount) {
            return 1;
        }

        @Override
        public void process(Delimiter opener, Delimiter closer, int delimiterUse) {
            UpperCaseNode content = new UpperCaseNode(opener.getTailChars(delimiterUse), SubSequence.NULL, closer.getLeadChars(delimiterUse));
            opener.moveNodesBetweenDelimitersTo(content, closer);
        }
    }

    private static class UpperCaseNode extends CustomNode implements DelimitedNode {
        protected BasedSequence openingMarker = SubSequence.NULL;
        protected BasedSequence text = SubSequence.NULL;
        protected BasedSequence closingMarker = SubSequence.NULL;

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
            super(new SubSequence(openingMarker.getBase(), openingMarker.getStartOffset(), closingMarker.getEndOffset()));
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

        @Override
        public void accept(Visitor visitor) {
            visitor.visit(this);
        }
    }

    private static class UpperCaseNodeRendererFactory implements NodeRendererFactory {

        @Override
        public NodeRenderer create(DataHolder options) {
            return new UpperCaseNodeRenderer(options);
        }
    }

    private static class UpperCaseNodeRenderer implements NodeRenderer {
        private UpperCaseNodeRenderer(DataHolder options) {
        }

        @Override
        public Set<NodeRenderHandler<?>> getNodeRenderers() {
            return new HashSet<>(Collections.singletonList(
                    new NodeRenderHandler<>(UpperCaseNode.class, this::render)
            ));
        }


        private void render(UpperCaseNode node, NodeRendererContext context, HtmlWriter html) {
            for (Node child = node.getFirstChild(); child != null; child = child.getNext()) {
                if (child instanceof Text) {
                    Text text = (Text) child;
                    text.setChars(text.getChars().toUpperCase(Locale.ENGLISH));
                }
                context.render(child);
            }
        }
    }
}
