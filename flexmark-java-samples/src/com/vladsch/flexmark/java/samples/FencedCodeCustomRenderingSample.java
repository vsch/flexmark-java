package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.renderer.CoreNodeRenderer;
import com.vladsch.flexmark.html.renderer.DelegatingNodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class FencedCodeCustomRenderingSample {
    final private static DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
            TocExtension.create(),
            CustomExtension.create()
    ));

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).indentSize(2).build();

    static class CustomNodeRenderer implements NodeRenderer {
        final private boolean codeContentBlock;

        public CustomNodeRenderer(DataHolder options) {
            codeContentBlock = Parser.FENCED_CODE_CONTENT_BLOCK.get(options);
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
            set.add(new NodeRenderingHandler<>(FencedCodeBlock.class, (node, context, html) -> {
                // test the node to see if it needs overriding
                BasedSequence info = node.getInfo();

                if (info.equals("my-fenced-code")) {
                    // standard fenced code rendering from CoreNodeRenderer with addition of converting class attribute of code tag to uppercase, customize it according to need
                    html.line();
                    html.srcPosWithTrailingEOL(node.getChars()).withAttr().tag("pre").openPre();

                    if (info.isNotNull() && !info.isBlank()) {
                        BasedSequence language = node.getInfoDelimitedByAny(CharPredicate.SPACE_TAB);
                        // CUSTOMIZATION: uppercase the code tag class string
                        html.attr("class", (context.getHtmlOptions().languageClassPrefix + language.unescape()).toUpperCase());
                    } else {
                        String noLanguageClass = context.getHtmlOptions().noLanguageClass.trim();
                        if (!noLanguageClass.isEmpty()) {
                            html.attr("class", noLanguageClass);
                        }
                    }

                    html.srcPosWithEOL(node.getContentChars()).withAttr(CoreNodeRenderer.CODE_CONTENT).tag("code");
                    if (codeContentBlock) {
                        context.renderChildren(node);
                    } else {
                        html.text(node.getContentChars().normalizeEOL());
                    }
                    html.tag("/code");
                    html.tag("/pre").closePre();
                    html.lineIf(context.getHtmlOptions().htmlBlockCloseTagEol);
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
                "```my-fenced-code\n" +
                "some text\n" +
                "```\n" +
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
