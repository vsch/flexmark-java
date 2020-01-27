package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AbbreviationNodeRenderer implements NodeRenderer {
    final private AbbreviationOptions options;

    public AbbreviationNodeRenderer(DataHolder options) {
        this.options = new AbbreviationOptions(options);
    }

    @Override
    public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
        return new HashSet<>(Arrays.asList(
                new NodeRenderingHandler<>(Abbreviation.class, this::render),
                new NodeRenderingHandler<>(AbbreviationBlock.class, this::render)
        ));
    }

    private void render(AbbreviationBlock node, NodeRendererContext context, HtmlWriter html) {

    }

    private void render(Abbreviation node, NodeRendererContext context, HtmlWriter html) {
        String text = node.getChars().unescape();
        BasedSequence abbreviation = node.getAbbreviation();
        String tag;

        if (options.useLinks) {
            html.attr("href", "#");
            tag = "a";
        } else {
            tag = "abbr";
        }

        html.attr("title", abbreviation);
        html.srcPos(node.getChars()).withAttr().tag(tag);
        html.text(text);
        html.closeTag(tag);
    }

    public static class Factory implements NodeRendererFactory {
        @NotNull
        @Override
        public NodeRenderer apply(@NotNull DataHolder options) {
            return new AbbreviationNodeRenderer(options);
        }
    }
}
