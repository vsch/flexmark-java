package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.Set;

public class EmojiNodeRenderer implements NodeRenderer {
    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final String rootImagePath;
    private final boolean useImageURL;

    public EmojiNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
        Document document = context.getDocument();

        this.rootImagePath = document.get(EmojiExtension.ROOT_IMAGE_PATH);
        this.useImageURL = document.get(EmojiExtension.USE_IMAGE_URLS);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.singleton(Emoji.class);
    }

    @Override
    public void render(Node node) {
        Emoji emoji = (Emoji) node;
        EmojiCheatSheet.EmojiShortcut shortcut = EmojiCheatSheet.shortCutMap.get(emoji.getText().toString());
        if (shortcut == null) {
            // output as text
            html.text(":");
            context.renderChildren(node);
            html.text(":");
        } else {
            html.attr("src", useImageURL ? shortcut.url : rootImagePath + shortcut.image);
            html.attr("alt", "emoji " + shortcut.category + ":" + shortcut.name);
            html.withAttr();
            html.tagVoid("img");
        }
    }
}
