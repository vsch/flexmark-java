package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.macros.internal.MacroDefinitionRepository;
import com.vladsch.flexmark.util.ast.CustomNode;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.ReferencingNode;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * A MacroReference node
 */
public class MacroReference extends CustomNode implements DelimitedNode, DoNotDecorate, ReferencingNode<MacroDefinitionRepository, MacroDefinitionBlock> {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence text = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;
    protected MacroDefinitionBlock myMacroDefinitionBlock;

    @Override
    public boolean isDefined() {
        return myMacroDefinitionBlock != null;
    }

    @Override
    public BasedSequence getReference() {
        return text;
    }

    @Override
    public MacroDefinitionBlock getReferenceNode(final Document document) {
        if (myMacroDefinitionBlock != null || text.isEmpty()) return myMacroDefinitionBlock;
        myMacroDefinitionBlock = getMacroDefinitionBlock(document.get(MacrosExtension.MACRO_DEFINITIONS));
        return myMacroDefinitionBlock;
    }

    @Override
    public MacroDefinitionBlock getReferenceNode(final MacroDefinitionRepository repository) {
        if (myMacroDefinitionBlock != null || text.isEmpty()) return myMacroDefinitionBlock;
        myMacroDefinitionBlock = getMacroDefinitionBlock(repository);
        return myMacroDefinitionBlock;
    }

    public MacroDefinitionBlock getMacroDefinitionBlock(final MacroDefinitionRepository repository) {
        return text.isEmpty() ? null : repository.get(text.toString());
    }

    public MacroDefinitionBlock getMacroDefinitionBlock() {
        return myMacroDefinitionBlock;
    }

    public void setMacroDefinitionBlock(MacroDefinitionBlock macroDefinitionBlock) {
        this.myMacroDefinitionBlock = macroDefinitionBlock;
    }

    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, text, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
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
