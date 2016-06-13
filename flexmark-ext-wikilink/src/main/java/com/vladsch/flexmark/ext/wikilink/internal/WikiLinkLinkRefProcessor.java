package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.LinkRefProcessor;

public class WikiLinkLinkRefProcessor implements LinkRefProcessor {
    boolean isLinkFirst = false;

    @Override
    public boolean getWantExclamationPrefix() {
        return false;
    }

    @Override
    public int getNestingLevel() {
        return 1;
    }

    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        return nodeChars.length() >= 4 && nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '[' && nodeChars.endCharAt(1) == ']' && nodeChars.endCharAt(2) == ']';
    }

    @Override
    public void initializeDocument(Document document) {
        isLinkFirst = document.get(WikiLinkExtension.LINK_FIRST_SYNTAX);
    }

    @Override
    public void adjustInlineText(Node node) {
        // nothing to do, our prefixes are stripped out of a link ref
    }

    @Override
    public void finalize(Document document) {

    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        return new WikiLink(nodeChars, isLinkFirst);
    }
}
