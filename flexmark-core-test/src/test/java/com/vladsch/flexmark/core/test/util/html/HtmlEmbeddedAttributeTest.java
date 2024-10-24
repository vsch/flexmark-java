package com.vladsch.flexmark.core.test.util.html;

import static org.junit.Assert.assertEquals;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.html.EmbeddedAttributeProvider;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.MutableAttributes;
import java.util.Collections;
import org.junit.Before;
import org.junit.Test;

public class HtmlEmbeddedAttributeTest {
  private MutableDataSet options;
  private Parser parser;
  private HtmlRenderer renderer;

  @Before
  public void setUp() {
    options = new MutableDataSet();
    options.set(Parser.EXTENSIONS, Collections.singletonList(new TestNodePostProcessorExtension()));

    parser = Parser.builder(options).build();
    renderer = HtmlRenderer.builder(options).build();
  }

  @Test
  public void embeddedAttributeProvider1() {
    assertEquals(
        "<p class=\"caption\"><img src=\"http://example.com/image.png\" alt=\"Figure 1. Some description here.\" /></p>\n",
        renderer.render(
            parser.parse("![Figure 1. Some description here.](http://example.com/image.png)\n")));
  }

  @Test
  public void embeddedAttributeProvider2() {
    assertEquals(
        "<p class=\"caption\"><img src=\"http://example.com/image.png\" alt=\"bar\" title=\"Image Title\" /></p>\n",
        renderer.render(
            parser.parse("![bar]\n" + "\n[bar]: http://example.com/image.png 'Image Title'")));
  }

  @Test
  public void embeddedAttributeProvider3() {
    assertEquals(
        "<p class=\"caption\"><img src=\"http://example.com/image.png\" alt=\"Figure 1. Some description here.\" title=\"Image Title\" /></p>\n",
        renderer.render(
            parser.parse(
                "![Figure 1. Some description here.][bar]\n"
                    + "\n[bar]: http://example.com/image.png 'Image Title'")));
  }

  private static class TestNodePostProcessor extends NodePostProcessor {
    private static class TestNodeFactory extends NodePostProcessorFactory {
      TestNodeFactory() {
        super();
        addNodes(Paragraph.class);
      }

      @Override
      public NodePostProcessor apply(Document document) {
        return new TestNodePostProcessor();
      }
    }

    static NodePostProcessorFactory Factory() {
      return new TestNodeFactory();
    }

    @Override
    public void process(NodeTracker state, Node node) {
      if (node instanceof Paragraph) { // [foo](http://example.com)
        MutableAttributes attributes = new MutableAttributes();
        attributes.addValue("class", "caption");

        node.appendChild(new EmbeddedAttributeProvider.EmbeddedNodeAttributes(node, attributes));
      }
    }
  }

  /**
   * An extension that registers a post processor which intentionally strips (replaces) specific
   * link and image-link tokens after parsing.
   */
  private static class TestNodePostProcessorExtension
      implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
    TestNodePostProcessorExtension() {}

    @Override
    public void rendererOptions(MutableDataHolder options) {
      // add any configuration settings to options you want to apply to everything, here
    }

    @Override
    public void extend(HtmlRenderer.Builder htmlRendererBuilder, String rendererType) {
      htmlRendererBuilder.attributeProviderFactory(EmbeddedAttributeProvider.Factory);
    }

    @Override
    public void parserOptions(MutableDataHolder options) {}

    @Override
    public void extend(Parser.Builder parserBuilder) {
      parserBuilder.postProcessorFactory(TestNodePostProcessor.Factory());
    }
  }
}
