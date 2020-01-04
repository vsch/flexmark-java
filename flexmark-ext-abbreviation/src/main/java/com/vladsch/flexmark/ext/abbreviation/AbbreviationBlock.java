package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ext.abbreviation.internal.AbbreviationRepository;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.ReferenceNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * A block node that contains the abbreviation definition
 */
public class AbbreviationBlock extends Block implements ReferenceNode<AbbreviationRepository, AbbreviationBlock, Abbreviation> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected BasedSequence abbreviation = BasedSequence.NULL;

    @Nullable
    @Override
    public Abbreviation getReferencingNode(@NotNull Node node) {
        return node instanceof Abbreviation ? (Abbreviation) node : null;
    }

    @Override
    public int compareTo(AbbreviationBlock other) {
        return SequenceUtils.compare(text, other.text, true);
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, abbreviation, "abbreviation");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker, abbreviation };
    }

    public AbbreviationBlock() {
    }

    public AbbreviationBlock(BasedSequence chars) {
        super(chars);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getText() {
        return text;
    }

    public void setText(BasedSequence text) {
        this.text = text;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getAbbreviation() {
        return abbreviation;
    }

    public void setAbbreviation(BasedSequence abbreviation) {
        this.abbreviation = abbreviation;
    }
}
