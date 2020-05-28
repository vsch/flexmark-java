package com.vladsch.flexmark.core.test.util.html;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.html.EmbeddedAttributeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

final public class HtmlEmbeddedAttributeTest {
    MutableDataSet OPTIONS;
    Parser PARSER;
    HtmlRenderer RENDERER;

    @Before
    public void setUp() throws Exception {
        OPTIONS = new MutableDataSet();
        OPTIONS.set(Parser.EXTENSIONS, Collections.singletonList(TestNodePostProcessorExtension.create()));

        PARSER = Parser.builder(OPTIONS).build();
        RENDERER = HtmlRenderer.builder(OPTIONS).build();
    }

    @Test
    public void embeddedAttributeProvider1() {
        assertEquals("<p class=\"caption\"><img src=\"http://example.com/image.png\" alt=\"Figure 1. Some description here.\" /></p>\n", RENDERER.render(PARSER.parse("![Figure 1. Some description here.](http://example.com/image.png)\n")));
    }

    @Test
    public void embeddedAttributeProvider2() {
        assertEquals("<p class=\"caption\"><img src=\"http://example.com/image.png\" alt=\"bar\" title=\"Image Title\" /></p>\n", RENDERER.render(PARSER.parse(
                "![bar]\n" +
                        "\n[bar]: http://example.com/image.png 'Image Title'")));
    }

    @Test
    public void embeddedAttributeProvider3() {
        assertEquals("<p class=\"caption\"><img src=\"http://example.com/image.png\" alt=\"Figure 1. Some description here.\" title=\"Image Title\" /></p>\n", RENDERER.render(PARSER.parse(
                "![Figure 1. Some description here.][bar]\n" +
                        "\n[bar]: http://example.com/image.png 'Image Title'")));
    }

    static class TestNodePostProcessor extends NodePostProcessor {
        private static class TestNodeFactory extends NodePostProcessorFactory {
            TestNodeFactory(DataHolder options) {
                super(false);
                addNodes(Paragraph.class);
            }

            @NotNull
            @Override
            public NodePostProcessor apply(@NotNull Document document) {
                return new TestNodePostProcessor();
            }
        }

        public static NodePostProcessorFactory Factory(DataHolder options) {
            return new TestNodeFactory(options);
        }

        @Override
        public void process(@NotNull NodeTracker state, @NotNull Node node) {
            BasedSequence paragraphText = BasedSequence.NULL;
            if (node instanceof Paragraph) { // [foo](http://example.com)
                MutableAttributes attributes = new MutableAttributes();
                attributes.addValue("class", "caption");

                node.appendChild(new EmbeddedAttributeProvider.EmbeddedNodeAttributes(node, attributes));
            }
        }
    }

    /**
     * An extension that registers a post processor which intentionally strips (replaces)
     * specific link and image-link tokens after parsing.
     */
    static class TestNodePostProcessorExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
        private TestNodePostProcessorExtension() { }

        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.attributeProviderFactory(EmbeddedAttributeProvider.Factory);
        }

        @Override
        public void parserOptions(MutableDataHolder options) { }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(TestNodePostProcessor.Factory(parserBuilder));
        }

        public static TestNodePostProcessorExtension create() {
            return new TestNodePostProcessorExtension();
        }
    }
}
