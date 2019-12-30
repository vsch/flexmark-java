package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ast.InlineLinkNode;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class YouTubeLinkSample {
    public static class YouTubeLink extends InlineLinkNode {
        public YouTubeLink() {
        }

        public YouTubeLink(Link other) {
            super(other.baseSubSequence(other.getStartOffset() - 1, other.getEndOffset()),
                    other.baseSubSequence(other.getStartOffset() - 1, other.getTextOpeningMarker().getEndOffset()),
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
        public void setTextChars(BasedSequence textChars) {
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
        public void process(@NotNull NodeTracker state, @NotNull Node node) {
            if (node instanceof Link) {
                Node previous = node.getPrevious();

                if (previous instanceof Text) {
                    BasedSequence chars = previous.getChars();
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

            @NotNull
            @Override
            public NodePostProcessor apply(@NotNull Document document) {
                return new YouTubeLinkNodePostProcessor(document);
            }
        }
    }

    public static class YouTubeLinkNodeRenderer implements NodeRenderer {

        public YouTubeLinkNodeRenderer(DataHolder options) {
        }

        @Override
        public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
            HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
            set.add(new NodeRenderingHandler<>(YouTubeLink.class, this::render));
            return set;
        }

        private void render(YouTubeLink node, NodeRendererContext context, HtmlWriter html) {
            if (context.isDoNotRenderLinks()) {
                context.renderChildren(node);
            } else {
                // standard Link Rendering
                ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null);

                html.attr("href", resolvedLink.getUrl());
                if (node.getTitle().isNotNull()) {
                    html.attr("title", node.getTitle().unescape());
                }
                html.srcPos(node.getChars()).withAttr(resolvedLink).tag("a");
                context.renderChildren(node);
                html.tag("/a");
            }
        }

        public static class Factory implements NodeRendererFactory {
            @NotNull
            @Override
            public NodeRenderer apply(@NotNull DataHolder options) {
                return new YouTubeLinkNodeRenderer(options);
            }
        }
    }

    public static class YouTubeLinkExtension implements Parser.ParserExtension, HtmlRenderer.HtmlRendererExtension {
        private YouTubeLinkExtension() {
        }

        public static YouTubeLinkExtension create() {
            return new YouTubeLinkExtension();
        }

        @Override
        public void extend(Parser.Builder parserBuilder) {
            parserBuilder.postProcessorFactory(new YouTubeLinkNodePostProcessor.Factory(parserBuilder));
        }

        @Override
        public void rendererOptions(@NotNull MutableDataHolder options) {

        }

        @Override
        public void parserOptions(MutableDataHolder options) {

        }

        @Override
        public void extend(@NotNull HtmlRenderer.Builder htmlRendererBuilder, @NotNull String rendererType) {
            if (htmlRendererBuilder.isRendererType("HTML")) {
                htmlRendererBuilder.nodeRendererFactory(new YouTubeLinkNodeRenderer.Factory());
            } else if (htmlRendererBuilder.isRendererType("JIRA")) {
            }
        }
    }
}
