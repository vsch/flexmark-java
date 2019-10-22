package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.ext.wikilink.WikiImage;
import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.ext.wikilink.WikiNode;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class WikiLinkLinkRefProcessor implements LinkRefProcessor {
    static final int BRACKET_NESTING_LEVEL = 1;

    private final WikiLinkOptions options;

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
    public boolean isMatch(BasedSequence nodeChars) {
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

    @Override
    public BasedSequence adjustInlineText(Document document, Node node) {
        // here we remove the page ref from child text and only leave the text part
        assert (node instanceof WikiNode);
        WikiNode wikiNode = (WikiNode) node;
        return wikiNode.getText().ifNull(wikiNode.getLink());
    }

    @Override
    public boolean allowDelimiters(BasedSequence chars, Document document, Node node) {
        assert (node instanceof WikiNode);
        WikiNode wikiNode = (WikiNode) node;
        return node instanceof WikiLink && WikiLinkExtension.ALLOW_INLINES.get(document) && wikiNode.getText().ifNull(wikiNode.getLink()).containsAllOf(chars);
    }

    @Override
    public void updateNodeElements(Document document, Node node) {
        assert (node instanceof WikiNode);
        WikiNode wikiNode = (WikiNode) node;
        if (node instanceof WikiLink && WikiLinkExtension.ALLOW_INLINES.get(document)) {
            // need to update link and pageRef with plain text versions
            if (wikiNode.getText().isNull()) {
                BasedSequence link = new TextCollectingVisitor().collectAndGetSequence(node);
                wikiNode.setLink(link, WikiLinkExtension.ALLOW_ANCHORS.get(document), WikiLinkExtension.ALLOW_ANCHOR_ESCAPE.get(document));
            }
        }
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        return nodeChars.firstChar() == '!' ? new WikiImage(nodeChars, options.linkFirstSyntax, options.allowPipeEscape)
                : new WikiLink(nodeChars, options.linkFirstSyntax, options.allowAnchors, options.allowPipeEscape, options.allowAnchorEscape);
    }

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor apply(Document document) {
            return new WikiLinkLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(DataHolder options) {
            return WikiLinkExtension.IMAGE_LINKS.get(options);
        }

        @Override
        public int getBracketNestingLevel(DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
