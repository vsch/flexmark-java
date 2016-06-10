package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class EmojiNodeRenderer implements NodeRenderer {

    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final String rootImagePath;

    public EmojiNodeRenderer(NodeRendererContext context, String rootImagePath) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.rootImagePath = rootImagePath;
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Emoji.class);
    }

    @Override
    public void render(Node node) {
        Emoji emoji = (Emoji) node;
        EmojiCheatSheet.EmojiShortcut shortcut = EmojiCheatSheet.shortCutMap.get(emoji.getText().toString());
        if (shortcut == null) {
            // output as text
            html.text(":");
            renderChildren(node);
            html.text(":");
        } else {
            Map<String, String> attrs = new LinkedHashMap<>();
            attrs.put("src", rootImagePath == null ? shortcut.url : rootImagePath + shortcut.image);
            attrs.put("alt", "emoji " + shortcut.category + ":" + shortcut.name);

            html.tag("img", getAttrs(node, attrs), true);
            Map<String, String> attributes = context.extendAttributes(node, Collections.<String, String>emptyMap());
        }
    }

    private Map<String, String> getAttrs(Node node) {
        return context.extendAttributes(node, Collections.<String, String>emptyMap());
    }

    private Map<String, String> getAttrs(Node node, Map<String, String> defaultAttributes) {
        return context.extendAttributes(node, defaultAttributes);
    }

    private void renderChildren(Node parent) {
        Node node = parent.getFirstChild();
        while (node != null) {
            Node next = node.getNext();
            context.render(node);
            node = next;
        }
    }
}
