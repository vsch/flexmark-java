package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

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
        String text = node.getChars().unescape();
        String abbreviation = node.getAbbreviation();
        String tag;
        
        if (useLinks) {
            html.attr("href", "#");
            tag = "a";
        } else {
            tag = "abbr";
        }

        html.attr("title", abbreviation);
        html.withAttr().tag(tag);
        html.text(text);
        html.tag("/" + tag);
    }
}
