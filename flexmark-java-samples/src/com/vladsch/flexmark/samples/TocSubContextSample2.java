package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ext.toc.TocBlock;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.toc.internal.TocNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TocSubContextSample2 {
    static final DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
            TocExtension.create(),
            CustomExtension.create()
    ));

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).indentSize(2).build();
    public static final DataKey<String> TOC_HTML = new DataKey<>("TOC_HTML", "");

    static class CustomNodeRenderer implements NodeRenderer {
        public static class Factory implements DelegatingNodeRendererFactory {
            @Override
            public NodeRenderer create(DataHolder options) {
                return new CustomNodeRenderer();
            }

            @Override
            public Set<Class<? extends NodeRendererFactory>> getDelegates() {
                Set<Class<? extends NodeRendererFactory>> set = new HashSet<Class<? extends NodeRendererFactory>>();
                // add node renderer factory classes to which this renderer will delegate some of its rendering
                // core node renderer is assumed to have all depend it so there is no need to add it
                set.add(TocNodeRenderer.Factory.class);
                return set;

                // return null if renderer does not delegate or delegates only to core node renderer
                //return null;
            }
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
            set.add(new NodeRenderingHandler<TocBlock>(TocBlock.class, new com.vladsch.flexmark.html.CustomNodeRenderer<TocBlock>() {
                @Override
                public void render(TocBlock node, NodeRendererContext context, HtmlWriter html) {
                    // test the node to see if it needs overriding
                    NodeRendererContext subContext = context.getDelegatedSubContext(true);
                    subContext.delegateRender();
                    String tocText = subContext.getHtmlWriter().toString(0);

                    context.getDocument().set(TOC_HTML, tocText);
                    //html.tagLineIndent("div", () -> html.append(subContext.getHtmlWriter()));
                }
            }));

            return set;
        }
    }

    static class CustomExtension implements HtmlRendererExtension {
        @Override
        public void rendererOptions(MutableDataHolder options) {

        }

        @Override
        public void extend(Builder rendererBuilder, String rendererType) {
            rendererBuilder.nodeRendererFactory(new CustomNodeRenderer.Factory());
        }

        static Extension create() {
            return new CustomExtension();
        }
    }

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
    public static void main(String[] args) {
        // You can re-use parser and renderer instances
        Document document = PARSER.parse("" +
                "[TOC] \n" +
                "\n" +
                "# Heading **some bold** 1\n" +
                "## Heading 1.1 _some italic_\n" +
                "### Heading 1.1.1\n" +
                "### Heading 1.1.2  **_some bold italic_**\n" +
                "");
        String html = RENDERER.render(document);
        String toc = document.get(TOC_HTML);

        System.out.println("<div class=\"toc\">");
        System.out.print(toc);
        System.out.println("</div>");

        System.out.println("<div class=\"body\">");
        System.out.print(html);
        System.out.println("</div>");
    }
}
