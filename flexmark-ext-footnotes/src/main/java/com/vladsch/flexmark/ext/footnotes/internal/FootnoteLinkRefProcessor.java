package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class FootnoteLinkRefProcessor implements LinkRefProcessor {
    static final boolean WANT_EXCLAMATION_PREFIX = false;
    static final int BRACKET_NESTING_LEVEL = 0;

    private final FootnoteRepository footnoteRepository;

    public FootnoteLinkRefProcessor(Document document) {
        this.footnoteRepository = document.get(FootnoteExtension.FOOTNOTES);
    }

    @Override
    public boolean getWantExclamationPrefix() {
        return WANT_EXCLAMATION_PREFIX;
    }

    @Override
    public int getBracketNestingLevel() {
        return BRACKET_NESTING_LEVEL;
    }


    @Override
    public boolean isMatch(BasedSequence nodeChars) {
        return nodeChars.length() >= 3 && nodeChars.charAt(0) == '[' && nodeChars.charAt(1) == '^' && nodeChars.endCharAt(1) == ']';
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

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor create(Document document) {
            return new FootnoteLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix() {
            return WANT_EXCLAMATION_PREFIX;
        }

        @Override
        public int getBracketNestingLevel() {
            return BRACKET_NESTING_LEVEL;
        }
    }

}
