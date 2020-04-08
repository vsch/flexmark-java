package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Emphasis;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.SoftLineBreak;
import com.vladsch.flexmark.ast.StrongEmphasis;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.AstCollectingVisitor;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NodeRendererSample2 {
    static class SampleExtension implements HtmlRenderer.HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.nodeRendererFactory(new SampleNodeRenderer.Factory());
        }

        static SampleExtension create() {
            return new SampleExtension();
        }
    }

    static class SampleNodeRenderer implements NodeRenderer {
        public SampleNodeRenderer(DataHolder options) {

        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            return new HashSet<>(Arrays.asList(
                    new NodeRenderingHandler<>(Paragraph.class, this::render),
                    new NodeRenderingHandler<>(SoftLineBreak.class, this::render),
                    new NodeRenderingHandler<>(StrongEmphasis.class, this::render),
                    new NodeRenderingHandler<>(Emphasis.class, this::render)
            ));
        }

        private void render(Paragraph node, NodeRendererContext context, HtmlWriter html) {
            context.renderChildren(node);
            if (node.getNext() != null) {
                html.tag("br/").line();
            }
        }

        private void render(SoftLineBreak node, NodeRendererContext context, HtmlWriter html) {
            html.tag("br/").line();
        }

        private void render(StrongEmphasis node, NodeRendererContext context, HtmlWriter html) {
            html.tag("b");
            context.renderChildren(node);
            html.tag("/b");
        }

        private void render(Emphasis node, NodeRendererContext context, HtmlWriter html) {
            html.tag("i");
            context.renderChildren(node);
            html.tag("/i");
        }

        public static class Factory implements NodeRendererFactory {
            @NotNull
            @Override
            public NodeRenderer apply(@NotNull DataHolder options) {
                return new SampleNodeRenderer(options);
            }
        }
    }

    static String commonMark(String markdown) {
        MutableDataHolder options = new MutableDataSet();
        options.set(Parser.EXTENSIONS, Arrays.asList(AutolinkExtension.create(), SampleExtension.create()));

        // change soft break to hard break
        options.set(HtmlRenderer.SOFT_BREAK, "<br/>");

        Parser parser = Parser.builder(options).build();
        Node document = parser.parse(markdown);
        String ast = new AstCollectingVisitor().collectAndGetAstText(document);
        System.out.println(markdown);
        System.out.println("---------------------------------");
        System.out.println(ast);
        System.out.println("---------------------------------");

        HtmlRenderer renderer = HtmlRenderer.builder(options).build();
        String html = renderer.render(document);
        return html;
    }

    public static void main(String[] args) {
        String html = commonMark("" +
                "**bold text**\n" +
                "\n" +
                "*italic text*\n" +
                "");
        System.out.println(html);
        System.out.println("---------------------------------");
        String html2 = commonMark("" +
                "**bold text**\n" +
                "*italic text*\n" +
                "");
        System.out.println(html2);
    }
}
