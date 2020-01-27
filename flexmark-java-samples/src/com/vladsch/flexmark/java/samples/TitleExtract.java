package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.ext.anchorlink.internal.AnchorLinkNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlRenderer.HtmlRendererExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.DelegatingNodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.profile.pegdown.Extensions;
import com.vladsch.flexmark.profile.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class TitleExtract {
    final private static DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL & ~(Extensions.HARDWRAPS)
            , HeadingExtension.create()).toMutable()
            .set(HtmlRenderer.INDENT_SIZE, 2);

    static class HeadingExtension implements HtmlRendererExtension {
        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {
            // add any configuration settings to options you want to apply to everything, here
        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            htmlRendererBuilder.nodeRendererFactory(new HeadingNodeRenderer.Factory());
        }

        static HeadingExtension create() {
            return new HeadingExtension();
        }
    }

    static class HeadingNodeRenderer implements NodeRenderer {
        public HeadingNodeRenderer(DataHolder options) {
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            return new HashSet<>(Arrays.asList(
                    new NodeRenderingHandler<>(AnchorLink.class, this::render),
                    new NodeRenderingHandler<>(Heading.class, this::render)
            ));
        }

        void render(AnchorLink node, NodeRendererContext context, HtmlWriter html) {
            Node parent = node.getParent();

            if (parent instanceof Heading && ((Heading) parent).getLevel() == 1) {
                // render without anchor link
                context.renderChildren(node);
            } else {
                context.delegateRender();
            }
        }

        static boolean haveExtension(int extensions, int flags) {
            return (extensions & flags) != 0;
        }

        static boolean haveAllExtensions(int extensions, int flags) {
            return (extensions & flags) == flags;
        }

        void render(Heading node, NodeRendererContext context, HtmlWriter html) {
            if (node.getLevel() == 1) {
                // render without anchor link
                int extensions = ParserEmulationProfile.PEGDOWN_EXTENSIONS.get(context.getOptions());
                if (context.getHtmlOptions().renderHeaderId || haveExtension(extensions, Extensions.ANCHORLINKS) || haveAllExtensions(extensions, Extensions.EXTANCHORLINKS | Extensions.EXTANCHORLINKS_WRAP)) {
                    String id = context.getNodeId(node);
                    if (id != null) {
                        html.attr("id", id);
                    }
                }

                if (context.getHtmlOptions().sourcePositionParagraphLines) {
                    html.srcPos(node.getChars()).withAttr().tagLine("h" + node.getLevel(), () -> {
                        html.srcPos(node.getText()).withAttr().tag("span");
                        context.renderChildren(node);
                        html.tag("/span");
                    });
                } else {
                    html.srcPos(node.getText()).withAttr().tagLine("h" + node.getLevel(), () -> context.renderChildren(node));
                }
            } else {
                context.delegateRender();
            }
        }

        public static class Factory implements DelegatingNodeRendererFactory {
            @NotNull
            @Override
            public NodeRenderer apply(@NotNull DataHolder options) {
                return new HeadingNodeRenderer(options);
            }

            @Override
            public Set<Class<?>> getDelegates() {
                Set<Class<?>> delegates = new HashSet<>();
                delegates.add(AnchorLinkNodeRenderer.Factory.class);
                return delegates;
            }
        }
    }

    private static String findTitle(Node root) {
        if (root instanceof Heading) {
            Heading h = (Heading) root;
            if (h.getLevel() == 1 && h.hasChildren()) {
                TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
                return collectingVisitor.collectAndGetText(h);
            }
        }

        if (root instanceof Block && root.hasChildren()) {
            Node child = root.getFirstChild();
            while (child != null) {
                String title = findTitle(child);
                if (title != null) {
                    return title;
                }
                child = child.getNext();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Parser parser = Parser.builder(OPTIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("\n" +
                "# Plugin commit-message-length-validator\n" +
                "\n" +
                "Vendor\n" +
                ":  Gerrit Code Review\n" +
                "\n" +
                "Version\n" +
                ":  v2.15-rc2\n" +
                "\n" +
                "commitmessage.maxSubjectLength\n" +
                ":       Maximum length of the commit message's subject line.  Defaults\n" +
                "        to 50 if not specified or less than 0.\n" +
                "\n" +
                "## About\n" +
                "\n" +
                "This plugin checks the length of a commit's commit message subject and body, and reports warnings and errors to the git client if the lentghts are exceeded.\n" +
                "\n" +
                "## Documentation\n" +
                "\n" +
                "* [Commit Message Length Configuration](config.md)\n" +
                "* More Items\n" +
                "\n" +
                "");

        String title = findTitle(document);
        System.out.println("Title: " + title + "\n");
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

        System.out.println(html);
    }
}
