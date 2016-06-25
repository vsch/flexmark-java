package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ext.zzzzzz.Zzzzzz;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.LinkRefProcessor;

public class ZzzzzzLinkRefProcessor implements LinkRefProcessor {
    final private ZzzzzzOptions options;
    private ZzzzzzOptions documentOptions;

    public ZzzzzzLinkRefProcessor(DataHolder options) {
        // getting options from builder
        this.options = new ZzzzzzOptions(options);
    }

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
        // getting options from document
        documentOptions = new ZzzzzzOptions(document);
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
        return new Zzzzzz(nodeChars, options.zzzzzzOption2);
    }
}
