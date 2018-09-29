package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.BlockContent;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.ReferenceNode;
import com.vladsch.flexmark.ext.macros.internal.MacroDefinitionRepository;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

/**
 * A MacroReference block node
 */
public class MacroDefinitionBlock extends Block implements ReferenceNode<MacroDefinitionRepository, MacroDefinitionBlock, MacroReference> {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence name = BasedSequence.NULL;
    private BasedSequence openingTrailing = BasedSequence.NULL;
    private BasedSequence closingMarker = BasedSequence.NULL;
    private BasedSequence closingTrailing = BasedSequence.NULL;
    private int ordinal = 0;
    private int firstReferenceOffset = Integer.MAX_VALUE;
    private int footnoteReferences = 0;
    private boolean inExpansion = false;

    public int getFootnoteReferences() {
        return footnoteReferences;
    }

    public void setFootnoteReferences(final int footnoteReferences) {
        this.footnoteReferences = footnoteReferences;
    }

    public int getFirstReferenceOffset() {
        return firstReferenceOffset;
    }

    public void setFirstReferenceOffset(int firstReferenceOffset) {
        this.firstReferenceOffset = firstReferenceOffset;
    }

    public void addFirstReferenceOffset(int firstReferenceOffset) {
        if (this.firstReferenceOffset < firstReferenceOffset) this.firstReferenceOffset = firstReferenceOffset;
    }

    public boolean isReferenced() {
        return this.firstReferenceOffset < Integer.MAX_VALUE;
    }

    public int getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(int ordinal) {
        this.ordinal = ordinal;
    }

    public boolean isInExpansion() {
        return inExpansion;
    }

    public void setInExpansion(final boolean inExpansion) {
        this.inExpansion = inExpansion;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, name, "name");
        segmentSpanChars(out, openingTrailing, "openTrail");
        segmentSpanChars(out, closingMarker, "close");
        segmentSpanChars(out, closingTrailing, "closeTrail");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, name, openingTrailing, closingMarker, closingTrailing };
    }

    @Override
    public MacroReference getReferencingNode(final Node node) {
        return node instanceof MacroReference ? (MacroReference) node : null;
    }

    @Override
    public int compareTo(final MacroDefinitionBlock o) {
        return name.compareTo(o.name);
    }

    public MacroDefinitionBlock() {
    }

    public MacroDefinitionBlock(BasedSequence chars) {
        super(chars);
    }

    public MacroDefinitionBlock(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public MacroDefinitionBlock(BlockContent blockContent) {
        super(blockContent);
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getName() {
        return name;
    }

    public void setName(final BasedSequence name) {
        this.name = name;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(final BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getOpeningTrailing() {
        return openingTrailing;
    }

    public void setOpeningTrailing(final BasedSequence openingTrailing) {
        this.openingTrailing = openingTrailing;
    }

    public BasedSequence getClosingTrailing() {
        return closingTrailing;
    }

    public void setClosingTrailing(final BasedSequence closingTrailing) {
        this.closingTrailing = closingTrailing;
    }
}
