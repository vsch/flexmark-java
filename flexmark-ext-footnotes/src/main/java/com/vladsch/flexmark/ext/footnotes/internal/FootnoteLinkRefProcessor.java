package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class FootnoteLinkRefProcessor implements LinkRefProcessor {
    static final boolean WANT_EXCLAMATION_PREFIX = false;
    static final int BRACKET_NESTING_LEVEL = 0;

    private final FootnoteRepository footnoteRepository;

    public FootnoteLinkRefProcessor(Document document) {
        this.footnoteRepository = FootnoteExtension.FOOTNOTES.get(document);
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
    public BasedSequence adjustInlineText(Document document, Node node) {
        assert node instanceof Footnote;
        return ((Footnote) node).getText();
    }

    @Override
    public boolean allowDelimiters(BasedSequence chars, Document document, Node node) {
        return true;
    }

    @Override
    public void updateNodeElements(Document document, Node node) {

    }

    public static class Factory implements LinkRefProcessorFactory {
        @Override
        public LinkRefProcessor apply(Document document) {
            return new FootnoteLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(DataHolder options) {
            return WANT_EXCLAMATION_PREFIX;
        }

        @Override
        public int getBracketNestingLevel(DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
