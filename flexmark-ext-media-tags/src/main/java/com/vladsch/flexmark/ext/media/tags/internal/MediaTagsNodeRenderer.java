package com.vladsch.flexmark.ext.media.tags.internal;

import com.vladsch.flexmark.ext.media.tags.AudioLink;
import com.vladsch.flexmark.ext.media.tags.EmbedLink;
import com.vladsch.flexmark.ext.media.tags.PictureLink;
import com.vladsch.flexmark.ext.media.tags.VideoLink;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class MediaTagsNodeRenderer implements NodeRenderer {
    public MediaTagsNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {

        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(AudioLink.class, this::renderAudioLink));
        set.add(new NodeRenderingHandler<>(EmbedLink.class, this::renderEmbedLink));
        set.add(new NodeRenderingHandler<>(PictureLink.class, this::renderPictureLink));
        set.add(new NodeRenderingHandler<>(VideoLink.class, this::renderVideoLink));
        return set;
    }

    private void renderAudioLink(AudioLink node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), false);
            String[] sources = resolvedLink.getUrl().split("\\|");
            html.attr("title", node.getText())
                    .attr("controls", "")
                    .withAttr()
                    .tag("audio");
            for (String source : sources) {
                String encoded = context.getHtmlOptions().percentEncodeUrls ? context.encodeUrl(source) : source;
                String type = Utilities.resolveAudioType(source);
                html.attr("src", encoded);
                if (type != null) html.attr("type", type);
                html.withAttr().tag("source", true);
            }
            html.text("Your browser does not support the audio element.");
            html.tag("/audio");
        }
    }

    private void renderEmbedLink(EmbedLink node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), null);

            html.attr("title", node.getText())
                    .attr("src", resolvedLink.getUrl())
                    .withAttr()
                    .tag("embed", true);
        }
    }

    private void renderPictureLink(PictureLink node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), false);
            String[] sources = resolvedLink.getUrl().split("\\|");
            html.tag("picture");
            for (int index = 0; index < sources.length - 1; index++) {
                String source = sources[index];
                String encoded = context.getHtmlOptions().percentEncodeUrls ? context.encodeUrl(source) : source;
                html.attr("srcset", encoded)
                        .withAttr()
                        .tag("source", true);
            }
            int last = sources.length - 1;
            if (last >= 0) {
                String source = sources[last];
                String encoded = context.getHtmlOptions().percentEncodeUrls ? context.encodeUrl(source) : source;
                html.attr("src", encoded)
                        .attr("alt", node.getText())
                        .withAttr()
                        .tag("img", true);
            }
            html.tag("/picture");
        }
    }

    private void renderVideoLink(VideoLink node, NodeRendererContext context, HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), false);
            String[] sources = resolvedLink.getUrl().split("\\|");
            html.attr("title", node.getText())
                    .attr("controls", "")
                    .withAttr()
                    .tag("video");
            for (String source : sources) {
                String encoded = context.getHtmlOptions().percentEncodeUrls ? context.encodeUrl(source) : source;
                String type = Utilities.resolveVideoType(source);
                html.attr("src", encoded);
                if (type != null) html.attr("type", type);
                html.withAttr().tag("source", true);
            }
            html.text("Your browser does not support the video element.");
            html.tag("/video");
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new MediaTagsNodeRenderer(options);
        }
    }
}
