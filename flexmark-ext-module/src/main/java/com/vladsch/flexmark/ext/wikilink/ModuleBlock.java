package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.SubSequence;
import com.vladsch.flexmark.node.CustomBlock;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.node.Visitor;

/**
 * A strikethrough node containing text and other inline nodes nodes as children.
 */
public class ModuleBlock extends CustomBlock {
    protected BasedSequence openingMarker = SubSequence.NULL;
    protected BasedSequence text = SubSequence.NULL;
    protected BasedSequence closingMarker = SubSequence.NULL;
    protected BasedSequence module = SubSequence.NULL;
    private int moduleOrdinal = 0;
    private int firstReferenceOffset = Integer.MAX_VALUE;

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

    public int getModuleOrdinal() {
        return moduleOrdinal;
    }

    public void setModuleOrdinal(int moduleOrdinal) {
        this.moduleOrdinal = moduleOrdinal;
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        out.append(" ordinal: " + moduleOrdinal + " ");
        segmentSpan(out, openingMarker, "open");
        segmentSpan(out, text, "text");
        segmentSpan(out, closingMarker, "close");
        segmentSpan(out, module, "module");
    }

    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, text, closingMarker, module };
    }

    public ModuleBlock() {
    }

    public ModuleBlock(BasedSequence chars) {
        super(chars);
    }

    public ModuleBlock(Node node) {
        super();
        appendChild(node);
        this.setCharsFromContent();
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

    public BasedSequence getModule() {
        return module;
    }

    public void setModule(BasedSequence module) {
        this.module = module;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }
}
