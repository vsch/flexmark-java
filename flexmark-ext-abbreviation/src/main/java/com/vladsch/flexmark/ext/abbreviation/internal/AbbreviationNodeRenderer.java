package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Node;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AbbreviationNodeRenderer implements NodeRenderer {
    private final AbbreviationOptions options;

    public AbbreviationNodeRenderer(DataHolder options) {
        this.options = new AbbreviationOptions(options);
    }

    @Override
    public Set<Class<? extends Node>> getNodeTypes() {
        return new HashSet<Class<? extends Node>>(Arrays.asList(
                Abbreviation.class,
                AbbreviationBlock.class
        ));
    }

    @Override
    public void render(NodeRendererContext context, HtmlWriter html, Node node) {
        if (node instanceof Abbreviation) {
            renderAbbreviation(context, html, (Abbreviation) node);
        } else if (node instanceof AbbreviationBlock) {
            renderAbbreviationBlock(context, html, (AbbreviationBlock) node);
        }

    }

    private void renderAbbreviationBlock(NodeRendererContext context, HtmlWriter html, AbbreviationBlock node) {

    }

    private void renderAbbreviation(NodeRendererContext context, HtmlWriter html, Abbreviation node) {
        String text = node.getChars().unescape();
        String abbreviation = node.getAbbreviation();
        String tag;

        if (options.useLinks) {
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
