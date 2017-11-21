package com.vladsch.flexmark.youtube.embedded;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.HashSet;
import java.util.Set;

public class YouTubeLinkTransformer {

    public static class YouTubeLink extends InlineLinkNode {
        public YouTubeLink() {
        }

        public YouTubeLink(final Link other) {
            super(other.getChars().baseSubSequence(other.getChars().getStartOffset() - 1, other.getChars().getEndOffset()),
                    other.getChars().baseSubSequence(other.getChars().getStartOffset() - 1, other.getTextOpeningMarker().getEndOffset()),
                    other.getText(),
                    other.getTextClosingMarker(),
                    other.getLinkOpeningMarker(),
                    other.getUrl(),
                    other.getTitleOpeningMarker(),
                    other.getTitle(),
                    other.getTitleClosingMarker(),
                    other.getLinkClosingMarker()
            );
        }

        @Override
        public void setTextChars(final BasedSequence textChars) {
            int textCharsLength = textChars.length();
            this.textOpeningMarker = textChars.subSequence(0, 1);
            this.text = textChars.subSequence(1, textCharsLength - 1).trim();
            this.textClosingMarker = textChars.subSequence(textCharsLength - 1, textCharsLength);
        }
    }

    public static class YouTubeLinkNodePostProcessor extends NodePostProcessor {
        public YouTubeLinkNodePostProcessor(DataHolder options) {
        }

        @Override
        public void process(NodeTracker state, Node node) {
            if (node instanceof Link) {
                Node previous = node.getPrevious();

                if (previous instanceof Text) {
                    final BasedSequence chars = previous.getChars();
                    if (chars.endsWith("@") && chars.isContinuedBy(node.getChars())) {
                        // trim previous chars to remove '@'
                        previous.setChars(chars.subSequence(0, chars.length() - 1));

                        YouTubeLink youTubeLink = new YouTubeLink((Link) node);
                        youTubeLink.takeChildren(node);
                        node.unlink();
                        previous.insertAfter(youTubeLink);
                        state.nodeRemoved(node);
                        state.nodeAddedWithChildren(youTubeLink);
                    }
                }
            }
        }

        public static class Factory extends NodePostProcessorFactory {
            public Factory(DataHolder options) {
                super(false);

                addNodes(Link.class);
            }

            @Override
            public NodePostProcessor create(Document document) {
                return new YouTubeLinkNodePostProcessor(document);
            }
        }
    }

    public static class YouTubeLinkNodeRenderer implements NodeRenderer {

        public YouTubeLinkNodeRenderer(DataHolder options) {
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            final YouTubeLinkNodeRenderer self = this;

            HashSet<NodeRenderingHandler<?>> set = new HashSet<NodeRenderingHandler<?>>();
            set.add(new NodeRenderingHandler<Link>(Link.class, new CustomNodeRenderer<Link>() {
                @Override
                public void render(Link node, NodeRendererContext context, HtmlWriter html) {
                    self.render(node, context, html);
                }
            }));
            return set;
        }

        private void render(final Link node, final NodeRendererContext context, final HtmlWriter html) {
            if (context.isDoNotRenderLinks()) {
                context.renderChildren(node);
            } else {
                // standard Link Rendering
                ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null);

                if (resolvedLink.getUrl().contains("www.youtube.com/watch")) {

                    html.attr("src", resolvedLink.getUrl().replace("watch?v=".toLowerCase(), "embed/"));
                    html.attr("width", "420");
                    html.attr("height", "315");
                    html.attr("class", "youtube-embedded");
                    html.attr("allowfullscreen", "true");
                    html.attr("frameborder", "0");
                    html.srcPos(node.getChars()).withAttr(resolvedLink).tag("iframe");
                    //context.renderChildren(node);
                    html.tag("/iframe");

                } else {
                    html.attr("href", resolvedLink.getUrl());
                    if (node.getTitle().isNotNull()) {
                        html.attr("title", node.getTitle().unescape());
                    }
                    html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                    context.renderChildren(node);
                    html.tag("/a");
                }

            }
        }

        public static class Factory implements NodeRendererFactory {
            @Override
            public NodeRenderer create(final DataHolder options) {
                return new YouTubeLinkNodeRenderer(options);
            }
        }
    }

    public static class YouTubeLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
        private YouTubeLinkExtension() {
        }

        public static Extension create() {
            return new YouTubeLinkExtension();
        }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(new YouTubeLinkNodePostProcessor.Factory(parserBuilder));
        }

        @Override
        public void rendererOptions(final MutableDataHolder options) {

        }

        @Override
        public void parserOptions(final MutableDataHolder options) {

        }

        @Override
        public void extend(HtmlRenderer.Builder rendererBuilder, String rendererType) {
            if (rendererType.equals("HTML")) {
                rendererBuilder.nodeRendererFactory(new YouTubeLinkNodeRenderer.Factory());
            } else if (rendererType.equals("JIRA") || rendererType.equals("YOUTRACK")) {
            }
        }
    }

}
