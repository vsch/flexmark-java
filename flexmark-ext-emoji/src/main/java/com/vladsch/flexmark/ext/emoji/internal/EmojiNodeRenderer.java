package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.*;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class EmojiNodeRenderer implements NodeRenderer {
    final EmojiOptions myOptions;

    public EmojiNodeRenderer(DataHolder options) {
        myOptions = new EmojiOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        HashSet<NodeRenderingHandler<?>> set = new HashSet<>();
        set.add(new NodeRenderingHandler<>(Emoji.class, this::render));
        return set;
    }

    private void render(Emoji node, NodeRendererContext context, HtmlWriter html) {
        EmojiResolvedShortcut shortcut = EmojiResolvedShortcut.getEmojiText(node, myOptions.useShortcutType, myOptions.useImageType, myOptions.rootImagePath, myOptions.useUnicodeFileNames);

        if (shortcut.emoji == null || shortcut.emojiText == null) {
            // output as text
            html.text(":");
            context.renderChildren(node);
            html.text(":");
        } else {
            if (shortcut.isUnicode) {
                html.text(shortcut.emojiText);
            } else {
                ResolvedLink resolvedLink = context.resolveLink(LinkType.IMAGE, shortcut.emojiText, null);

                html.attr("src", resolvedLink.getUrl());
                html.attr("alt", shortcut.alt);
                if (!myOptions.attrImageSize.isEmpty()) html.attr("height", myOptions.attrImageSize).attr("width", myOptions.attrImageSize);
                if (!myOptions.attrAlign.isEmpty()) html.attr("align", myOptions.attrAlign);
                if (!myOptions.attrImageClass.isEmpty()) html.attr("class", myOptions.attrImageClass);
                html.withAttr(resolvedLink);
                html.tagVoid("img");
            }
        }
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new EmojiNodeRenderer(options);
        }
    }
}
