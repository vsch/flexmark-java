package com.vladsch.flexmark.ext.enumerated.reference.internal;

import com.vladsch.flexmark.ext.enumerated.reference.*;
import com.vladsch.flexmark.parser.LinkRefProcessor;
import com.vladsch.flexmark.parser.LinkRefProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class EnumeratedReferenceLinkRefProcessor implements LinkRefProcessor {
    static final boolean WANT_EXCLAMATION_PREFIX = false;
    static final int BRACKET_NESTING_LEVEL = 0;

    final private EnumeratedReferenceRepository enumeratedReferenceRepository;

    public EnumeratedReferenceLinkRefProcessor(Document document) {
        this.enumeratedReferenceRepository = EnumeratedReferenceExtension.ENUMERATED_REFERENCES.get(document);
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
    public boolean isMatch(@NotNull BasedSequence nodeChars) {
        return nodeChars.length() >= 3 && nodeChars.charAt(0) == '[' && (nodeChars.charAt(1) == '@' || nodeChars.charAt(1) == '#') && nodeChars.endCharAt(1) == ']' && (nodeChars.length() == 3 || !Character.isDigit(nodeChars.charAt(2)));
    }

    @NotNull
    @Override
    public Node createNode(@NotNull BasedSequence nodeChars) {
        BasedSequence enumeratedReferenceId = nodeChars.midSequence(2, -1).trim();
        EnumeratedReferenceBlock enumeratedReferenceBlock = enumeratedReferenceId.length() > 0 ? enumeratedReferenceRepository.get(enumeratedReferenceId.toString()) : null;

        if (nodeChars.charAt(1) == '@') {
            // reference link
            EnumeratedReferenceLink enumeratedReference = new EnumeratedReferenceLink(nodeChars.subSequence(0, 2), enumeratedReferenceId, nodeChars.endSequence(1));
            enumeratedReference.setEnumeratedReferenceBlock(enumeratedReferenceBlock);
            return enumeratedReference;
        } else {
            // reference text
            EnumeratedReferenceText enumeratedReferenceText = new EnumeratedReferenceText(nodeChars.subSequence(0, 2), enumeratedReferenceId, nodeChars.endSequence(1));
            enumeratedReferenceText.setEnumeratedReferenceBlock(enumeratedReferenceBlock);
            return enumeratedReferenceText;
        }
    }

    @NotNull
    @Override
    public BasedSequence adjustInlineText(@NotNull Document document, @NotNull Node node) {
        assert node instanceof EnumeratedReferenceBase;
        return ((EnumeratedReferenceBase) node).getText();
    }

    @Override
    public boolean allowDelimiters(@NotNull BasedSequence chars, @NotNull Document document, @NotNull Node node) {
        return true;
    }

    @Override
    public void updateNodeElements(@NotNull Document document, @NotNull Node node) {

    }

    public static class Factory implements LinkRefProcessorFactory {
        @NotNull
        @Override
        public LinkRefProcessor apply(@NotNull Document document) {
            return new EnumeratedReferenceLinkRefProcessor(document);
        }

        @Override
        public boolean getWantExclamationPrefix(@NotNull DataHolder options) {
            return WANT_EXCLAMATION_PREFIX;
        }

        @Override
        public int getBracketNestingLevel(@NotNull DataHolder options) {
            return BRACKET_NESTING_LEVEL;
        }
    }
}
