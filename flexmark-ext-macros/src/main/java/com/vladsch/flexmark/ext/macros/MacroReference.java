package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.ext.macros.internal.MacroDefinitionRepository;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A MacroReference node
 */
public class MacroReference extends Node implements DelimitedNode, DoNotDecorate, ReferencingNode<MacroDefinitionRepository, MacroDefinitionBlock> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected MacroDefinitionBlock myMacroDefinitionBlock;

    @Override
    public boolean isDefined() {
        return myMacroDefinitionBlock != null;
    }

    @NotNull
    @Override
    public BasedSequence getReference() {
        return text;
    }

    @Override
    public MacroDefinitionBlock getReferenceNode(Document document) {
        if (myMacroDefinitionBlock != null || text.isEmpty()) return myMacroDefinitionBlock;
        myMacroDefinitionBlock = getMacroDefinitionBlock(MacrosExtension.MACRO_DEFINITIONS.get(document));
        return myMacroDefinitionBlock;
    }

    @Override
    public MacroDefinitionBlock getReferenceNode(MacroDefinitionRepository repository) {
        if (myMacroDefinitionBlock != null || text.isEmpty()) return myMacroDefinitionBlock;
        myMacroDefinitionBlock = getMacroDefinitionBlock(repository);
        return myMacroDefinitionBlock;
    }

    public MacroDefinitionBlock getMacroDefinitionBlock(MacroDefinitionRepository repository) {
        return text.isEmpty() ? null : repository.get(text.toString());
    }

    public MacroDefinitionBlock getMacroDefinitionBlock() {
        return myMacroDefinitionBlock;
    }

    public void setMacroDefinitionBlock(MacroDefinitionBlock macroDefinitionBlock) {
        this.myMacroDefinitionBlock = macroDefinitionBlock;
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        delimitedSegmentSpanChars(out, openingMarker, text, closingMarker, "text");
    }

    public MacroReference() {
    }

    public MacroReference(BasedSequence chars) {
        super(chars);
    }

    public MacroReference(BasedSequence openingMarker, BasedSequence text, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.text = text;
        this.closingMarker = closingMarker;
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
}
