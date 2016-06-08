package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.*;

public class AbbreviationNodeRenderer implements NodeRenderer {

    private final NodeRendererContext context;
    private final HtmlWriter html;
    private final boolean useLinks;

    public AbbreviationNodeRenderer(NodeRendererContext context, boolean useLinks) {
        this.context = context;
        this.html = context.getHtmlWriter();
        this.useLinks = useLinks;
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<Class<? extends Node>>(Arrays.asList(
                Abbreviation.class,
                AbbreviationBlock.class
        ));
    }

    @Override
    public void render(Node node) {
        if (node instanceof Abbreviation) {
            renderAbbreviation((Abbreviation) node);
        } else if (node instanceof AbbreviationBlock) {
            renderAbbreviationBlock((AbbreviationBlock) node);
        }

    }

    private void renderAbbreviationBlock(AbbreviationBlock node) {
        
    }

    private void renderAbbreviation(Abbreviation node) {
        Map<String, String> attrs = new LinkedHashMap<>();
        String text = node.getChars().unescaped();
        String abbreviation = node.getAbbreviation();
        String tag;
        
        if (useLinks) {
            attrs.put("href", "#");
            tag = "a";
        } else {
            tag = "abbr";
        }
        
        attrs.put("title", abbreviation);
        html.tag(tag, getAttrs(node, attrs));
        html.text(text);
        html.tag("/" + tag);
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
