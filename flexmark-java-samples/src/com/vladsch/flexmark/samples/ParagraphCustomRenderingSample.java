package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRenderer.Builder;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ParagraphCustomRenderingSample {
    static final DataHolder OPTIONS = new MutableDataSet().set(Parser.EXTENSIONS, Arrays.asList(
            CustomExtension.create()
    ));

    static final Parser PARSER = Parser.builder(OPTIONS).build();
    static final HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).indentSize(2).build();

    static class CustomNodeRenderer implements NodeRenderer {
        private final boolean codeSoftLineBreaks;

        public CustomNodeRenderer(final DataHolder options) {
            codeSoftLineBreaks = Parser.CODE_SOFT_LINE_BREAKS.getFrom(options);
        }

        public static class Factory implements DelegatingNodeRendererFactory {
            @Override
            public NodeRenderer create(final DataHolder options) {
                return new CustomNodeRenderer(options);
            }

            @Override
            public Set<Class<? extends NodeRendererFactory>> getDelegates() {
                Set<Class<? extends NodeRendererFactory>> set = new HashSet<Class<? extends NodeRendererFactory>>();
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
            HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
            set.add(new NodeRenderingHandler<Paragraph>(Paragraph.class, new com.vladsch.flexmark.html.CustomNodeRenderer<Paragraph>() {
                @Override
                public void render(Paragraph node, NodeRendererContext context, final HtmlWriter html) {
                    html.withAttr().tag("div");
                    context.delegateRender();
                    html.tag("/div");
                }
            }));

            return set;
        }
    }

    static class CustomExtension implements HtmlRendererExtension {
        @Override
        public void rendererOptions(final MutableDataHolder options) {

        }

        @Override
        public void extend(final Builder rendererBuilder, final String rendererType) {
            rendererBuilder.nodeRendererFactory(new CustomNodeRenderer.Factory());
        }

        static Extension create() {
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
