package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.LinkRefProcessor;

public class FootnoteLinkRefProcessor implements LinkRefProcessor {
    private FootnoteRepository footnoteRepository = null;

    @Override
    public boolean getWantExclamationPrefix() {
        return false;
    }

    @Override
    public int getNestingLevel() {
        return 0;
    }

    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        return nodeChars.length() >= 3 && nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '^' && nodeChars.endCharAt(1) == ']';
    }

    @Override
    public void initializeDocument(Document document) {
        footnoteRepository = document.get(FootnoteExtension.FOOTNOTES);
    }

    @Override
    public void finalize(Document document) {
        footnoteRepository = null;
    }

    @Override
    public Node createNode(BasedSequence nodeChars) {
        BasedSequence footnoteId = nodeChars.midSequence(2, -1).trim();
        FootnoteBlock footnoteBlock = footnoteId.length() > 0 ? footnoteRepository.get(footnoteId.toString()) : null;

        Footnote footnote = new Footnote(nodeChars.subSequence(0, 2), footnoteId, nodeChars.endSequence(1));
        footnote.setFootnoteBlock(footnoteBlock);

        if (footnoteBlock != null) {
            footnoteRepository.addFootnoteReference(footnoteBlock, footnote);
        }
        return footnote;
    }

    @Override
    public void adjustInlineText(Node node) {
        Node firstChild = node.getFirstChild();
        if (firstChild != null) {
            if (firstChild.getChars().length() == 1) {
                firstChild.unlink();
            } else {
                firstChild.setChars(firstChild.getChars().subSequence(1));
            }
        }
    }
}
