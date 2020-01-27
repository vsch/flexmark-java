package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.renderer.DelegatingNodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.Escaping;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class InlineCodeCustomRenderingSample {
    final private static DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
            TocExtension.create(),
            CustomExtension.create()
    ));

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).indentSize(2).build();

    static class CustomNodeRenderer implements NodeRenderer {
        final private boolean codeSoftLineBreaks;

        public CustomNodeRenderer(DataHolder options) {
            codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.get(options);
        }

        public static class Factory implements DelegatingNodeRendererFactory {
            @NotNull
            @Override
            public NodeRenderer apply(@NotNull DataHolder options) {
                return new CustomNodeRenderer(options);
            }

            @Override
            public Set<Class<?>> getDelegates() {
                // NOTE: add node renderer factory classes to which this renderer will delegate some rendering.
                //       No need to add the CoreNodeRenderer, it is assumed to be depended on by all.
//                Set<Class<?>> set = new HashSet<>();
//                set.add(TocNodeRenderer.Factory.class);
//                return set;

                // return null if renderer does not delegate or delegates only to CoreNodeRenderer
                return null;
            }
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(Code.class, (node, context, html) -> {
                // test the node to see if it needs overriding
                if (node.getOpeningMarker().length() == 3) {
                    if (context.getHtmlOptions().sourcePositionParagraphLines) {
                        html.withAttr().tag("pre");
                    } else {
                        html.srcPos(node.getText()).withAttr().tag("pre");
                    }
                    if (codeSoftLineBreaks && !context.getHtmlOptions().isSoftBreakAllSpaces) {
                        for (Node child : node.getChildren()) {
                            if (child instanceof Text) {
                                html.text(Escaping.collapseWhitespace(child.getChars(), true));
                            } else {
                                context.render(child);
                            }
                        }
                    } else {
                        html.text(Escaping.collapseWhitespace(node.getText(), true));
                    }
                    html.tag("/pre");
                } else {
                    context.delegateRender();
                }
            }));

            return set;
        }
    }

    static class CustomExtension implements HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {

        }

        @Override
        public void extend(@NotNull Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.nodeRendererFactory(new CustomNodeRenderer.Factory());
        }

        static CustomExtension create() {
            return new CustomExtension();
        }
    }

    // use the PARSER to parse and RENDERER to render with pegdown compatibility
    public static void main(String[] args) {
        // You can re-use parser and renderer instances
        Node document = PARSER.parse("" +
                "`test`\n" +
                "\n" +
                "``test``\n" +
                "\n" +
                "```test```\n" +
                "\n" +
                "````test````\n" +
                "");
        String html = RENDERER.render(document);
        System.out.println("``````markdown");
        System.out.println(document.getChars());
        System.out.println("``````");
        System.out.println("");
        System.out.println("``````html");
        System.out.println(html);
        System.out.println("``````");
    }
}
