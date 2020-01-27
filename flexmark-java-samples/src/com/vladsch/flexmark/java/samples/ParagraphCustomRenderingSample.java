package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Paragraph;
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
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class ParagraphCustomRenderingSample {
    final private static DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Collections.singletonList(
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
                Set<Class<?>> set = new HashSet<>();
                // add node renderer factory classes to which this renderer will delegate some of its rendering
                // core node renderer is assumed to have all depend it so there is no need to add it
                //set.add(TocNodeRenderer.Factory.class);
                //return set;

                // return null if renderer does not delegate or delegates only to core node renderer
                return null;
            }
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(Paragraph.class, (node, context, html) -> {
                html.withAttr().tag("div");
                context.delegateRender();
                html.tag("/div");
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
                "text\n" +
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
