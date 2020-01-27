package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.wikilink.WikiNode;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.TextCollectingVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class WikiLinkLinkRefProcessor implements LinkRefProcessor {
    static final int BRACKET_NESTING_LEVEL = 1;

    final private WikiLinkOptions options;

    public WikiLinkLinkRefProcessor(Document document) {
        this.options = new WikiLinkOptions(document);
    }

    @Override
    public boolean getWantExclamationPrefix() {
        return options.imageLinks;
    }

    @Override
    public int getBracketNestingLevel() {
        return BRACKET_NESTING_LEVEL;
    }

    @Override
    public boolean isMatch(@NotNull BasedSequence nodeChars) {
        int length = nodeChars.length();
        if (options.imageLinks) {
            if (length >= 5 && nodeChars.charAt(0) == '!') {
                return nodeChars.charAt(1) == '[' && nodeChars.charAt(2) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
            } else if (length >= 4) {
                return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
            }
        } else if (length >= 4) {
            return nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
        }
        return false;
    }

    @NotNull
    @Override
    public BasedSequence adjustInlineText(@NotNull Document document, @NotNull Node node) {
        // here we remove the page ref from child text and only leave the text part
        assert (node instanceof WikiNode);
        WikiNode wikiNode = (WikiNode) node;
        return wikiNode.getText().ifNull(wikiNode.getLink());
    }

    @Override
    public boolean allowDelimiters(@NotNull BasedSequence chars, @NotNull Document document, @NotNull Node node) {
        assert (node instanceof WikiNode);
        WikiNode wikiNode = (WikiNode) node;
        return node instanceof WikiLink && WikiLinkExtension.ALLOW_INLINES.get(document) && wikiNode.getText().ifNull(wikiNode.getLink()).containsAllOf(chars);
    }

    @Override
    public void updateNodeElements(@NotNull Document document, @NotNull Node node) {
        assert (node instanceof WikiNode);
        WikiNode wikiNode = (WikiNode) node;
        if (node instanceof WikiLink && WikiLinkExtension.ALLOW_INLINES.get(document)) {
            // need to update link and pageRef with plain text versions
            if (wikiNode.getText().isNull()) {
                BasedSequence link = new TextCollectingVisitor().collectAndGetSequence(node, TextContainer.F_NODE_TEXT);
                wikiNode.setLink(link, WikiLinkExtension.ALLOW_ANCHORS.get(document), WikiLinkExtension.ALLOW_ANCHOR_ESCAPE.get(document));
            }
        }
    }

    @NotNull
    @Override
    public Node createNode(@NotNull BasedSequence nodeChars) {
        return nodeChars.firstChar() == '!' ? new WikiImage(nodeChars, options.linkFirstSyntax, options.allowPipeEscape)
                : new WikiLink(nodeChars, options.linkFirstSyntax, options.allowAnchors, options.allowPipeEscape, options.allowAnchorEscape);
    }

    public static class Factory implements LinkRefProcessorFactory {
        @NotNull
        @Override
        public LinkRefProcessor apply(@NotNull Document document) {
            return new WikiLinkLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(@NotNull DataHolder options) {
            return WikiLinkExtension.IMAGE_LINKS.get(options);
        }

        @Override
        public int getBracketNestingLevel(@NotNull DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
