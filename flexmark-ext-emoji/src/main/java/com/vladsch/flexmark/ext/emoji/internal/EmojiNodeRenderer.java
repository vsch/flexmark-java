package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.internal.util.options.DataHolder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class EmojiNodeRenderer implements NodeRenderer {
    private final String rootImagePath;
    private final boolean useImageURL;

    public EmojiNodeRenderer(DataHolder options) {
        this.rootImagePath = options.get(EmojiExtension.ROOT_IMAGE_PATH);
        this.useImageURL = options.get(EmojiExtension.USE_IMAGE_URLS);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Collections.singletonList(
                new NodeRenderingHandler<>(Emoji.class, this::render)
        ));
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
            html.withAttr(resolvedLink);
            html.tagVoid("img");
        }
    }
}
