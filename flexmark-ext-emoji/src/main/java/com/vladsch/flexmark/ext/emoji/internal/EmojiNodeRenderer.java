package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.html.CustomNodeRenderer;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.HashSet;
import java.util.Set;

public class EmojiNodeRenderer implements NodeRenderer {
    private final String rootImagePath;
    private final boolean useImageURL;
    private final String attrImageSize;
    private final String attrAlign;

    public EmojiNodeRenderer(DataHolder options) {
        this.rootImagePath = options.get(EmojiExtension.ROOT_IMAGE_PATH);
        this.useImageURL = options.get(EmojiExtension.USE_IMAGE_URLS);
        this.attrImageSize = options.get(EmojiExtension.ATTR_IMAGE_SIZE);
        this.attrAlign = options.get(EmojiExtension.ATTR_ALIGN);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Emoji.class, new CustomNodeRenderer<Emoji>() {
            @Override
            public void render(Emoji node, NodeRendererContext context, HtmlWriter html) {
                EmojiNodeRenderer.this.render(node, context, html);
            }
        }));
        return set;
    }

    private void render(Emoji node, NodeRendererContext context, HtmlWriter html) {
        Emoji emoji = (Emoji) node;
        EmojiCheatSheet.EmojiShortcut shortcut = EmojiCheatSheet.shortCutMap.get(emoji.getText().toString());
        if (shortcut == null) {
            // output as text
            html.text(":");
            context.renderChildren(node);
            html.text(":");
        } else {
            ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, useImageURL ? shortcut.url : rootImagePath + shortcut.image, null);

            html.attr("src", resolvedLink.getUrl());
            html.attr("alt", "emoji " + shortcut.category + ":" + shortcut.name);
            if (!attrImageSize.isEmpty()) html.attr("height", attrImageSize).attr("width", attrImageSize);
            if (!attrAlign.isEmpty()) html.attr("align", attrAlign);
            html.withAttr(resolvedLink);
            html.tagVoid("img");
        }
    }
}
