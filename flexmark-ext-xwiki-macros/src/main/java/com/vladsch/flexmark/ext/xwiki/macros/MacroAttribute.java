package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

/**
 * A macros node
 */
public class MacroAttribute extends Node implements DoNotDecorate {
    protected BasedSequence attribute = BasedSequence.NULL;
    protected BasedSequence separator = BasedSequence.NULL;
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence value = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { attribute, separator, openingMarker, value, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, attribute, "attribute");
        segmentSpanChars(out, separator, "separator");
        delimitedSegmentSpanChars(out, openingMarker, value, closingMarker, "value");
    }

    public MacroAttribute() {
    }

    public MacroAttribute(BasedSequence chars) {
        super(chars);
    }

    public MacroAttribute(BasedSequence attribute, BasedSequence separator, BasedSequence openingMarker, BasedSequence value, BasedSequence closingMarker) {
        super(spanningChars(attribute, separator, openingMarker, value, closingMarker));
        this.attribute = attribute;
        this.separator = separator;
        this.openingMarker = openingMarker;
        this.value = value;
        this.closingMarker = closingMarker;
    }

    public BasedSequence getAttribute() {
        return attribute;
    }

    public void setAttribute(BasedSequence attribute) {
        this.attribute = attribute;
    }

    public BasedSequence getSeparator() {
        return separator;
    }

    public void setSeparator(BasedSequence separator) {
        this.separator = separator;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getValue() {
        return value;
    }

    public void setValue(BasedSequence value) {
        this.value = value;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
