package com.vladsch.flexmark.ext.media.tags.internal;

import com.vladsch.flexmark.ext.media.tags.AudioLink;
import com.vladsch.flexmark.ext.media.tags.EmbedLink;
import com.vladsch.flexmark.ext.media.tags.PictureLink;
import com.vladsch.flexmark.ext.media.tags.VideoLink;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class MediaTagsNodeRenderer implements NodeRenderer {
    public MediaTagsNodeRenderer(DataHolder options) {
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        final MediaTagsNodeRenderer self = this;

        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();

        // AudioLink Rendering
        set.add(new NodeRenderingHandler<>(AudioLink.class, new CustomNodeRenderer<AudioLink>() {
            @Override
            public void render(AudioLink node, NodeRendererContext context, HtmlWriter html) {
                self.renderAudioLink(node, context, html);
            }
        }));

        // EmbedLink Rendering
        set.add(new NodeRenderingHandler<>(EmbedLink.class, new CustomNodeRenderer<EmbedLink>() {
            @Override
            public void render(EmbedLink node, NodeRendererContext context, HtmlWriter html) {
                self.renderEmbedLink(node, context, html);
            }
        }));

        // PictureLink Rendering
        set.add(new NodeRenderingHandler<>(PictureLink.class, new CustomNodeRenderer<PictureLink>() {
            @Override
            public void render(PictureLink node, NodeRendererContext context, HtmlWriter html) {
                self.renderPictureLink(node, context, html);
            }
        }));

        // VideoLink Rendering
        set.add(new NodeRenderingHandler<>(VideoLink.class, new CustomNodeRenderer<VideoLink>() {
            @Override
            public void render(VideoLink node, NodeRendererContext context, HtmlWriter html) {
                self.renderVideoLink(node, context, html);
            }
        }));
        return set;
    }

    private void renderAudioLink(final AudioLink node, final NodeRendererContext context, final HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), false);
            String[] sources = resolvedLink.getUrl().split("\\|");
            html.attr("title", node.getText())
                    .attr("controls", null)
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

    private void renderEmbedLink(final EmbedLink node, final NodeRendererContext context, final HtmlWriter html) {
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

    private void renderPictureLink(final PictureLink node, final NodeRendererContext context, final HtmlWriter html) {
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

    private void renderVideoLink(final VideoLink node, final NodeRendererContext context, final HtmlWriter html) {
        if (context.isDoNotRenderLinks()) {
            context.renderChildren(node);
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.LINK, node.getUrl().unescape(), false);
            String[] sources = resolvedLink.getUrl().split("\\|");
            html.attr("title", node.getText())
                    .attr("controls", null)
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
        @Override
        public NodeRenderer apply(final DataHolder options) {
            return new MediaTagsNodeRenderer(options);
        }
    }
}
