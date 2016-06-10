package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class FootnoteNodeRenderer implements NodeRenderer {

    private final NodeRendererContext context;
    private final HtmlWriter html;

    public FootnoteNodeRenderer(NodeRendererContext context) {
        this.context = context;
        this.html = context.getHtmlWriter();
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return Collections.<Class<? extends Node>>singleton(Footnote.class);
    }

    @Override
    public void render(Node node) {
        Footnote footnote = (Footnote) node;
        
        if (footnote == null) {
            // output as text
            html.text(":");
            renderChildren(node);
            html.text(":");
        } else {
            //Map<String, String> attrs = new LinkedHashMap<>();
            //attrs.put("src", rootImagePath == null ? shortcut.url : rootImagePath + shortcut.image);
            //attrs.put("alt", "emoji " + shortcut.category + ":" + shortcut.name);
            //
            //html.tag("img", getAttrs(node, attrs), true);
            //Map<String, String> attributes = context.extendAttributes(node, Collections.<String, String>emptyMap());
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
