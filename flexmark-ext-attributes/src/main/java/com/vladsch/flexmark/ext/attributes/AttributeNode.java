package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An Attribute node representing a single attribute name and value in attributes node
 */
public class AttributeNode extends Node implements DoNotDecorate {
    protected BasedSequence name = BasedSequence.NULL;
    protected BasedSequence attributeSeparator = BasedSequence.NULL;
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence value = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { name, attributeSeparator, openingMarker, value, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, name, "name");
        segmentSpanChars(out, attributeSeparator, "sep");
        delimitedSegmentSpanChars(out, openingMarker, value, closingMarker, "value");
        if (isImplicitName()) out.append(" isImplicit");
        if (isClass()) out.append(" isClass");
        if (isId()) out.append(" isId");
    }

    public AttributeNode() {
    }

    public AttributeNode(BasedSequence chars) {
        super(chars);
    }

    public AttributeNode(@Nullable BasedSequence name, @Nullable BasedSequence attributeSeparator, @Nullable BasedSequence openingMarker, @Nullable BasedSequence value, @Nullable BasedSequence closingMarker) {
        super(spanningChars(name, attributeSeparator, openingMarker, value, closingMarker));
        this.name = name != null ? name : BasedSequence.NULL;
        this.attributeSeparator = attributeSeparator != null ? attributeSeparator : BasedSequence.NULL;
        this.openingMarker = openingMarker != null ? openingMarker : BasedSequence.NULL;
        this.value = value != null ? value : BasedSequence.NULL;
        this.closingMarker = closingMarker != null ? closingMarker : BasedSequence.NULL;
    }

    public static boolean isImplicitName(CharSequence text) {
        return text.length() > 0 && (text.charAt(0) == '.' || text.charAt(0) == '#');
    }

    public boolean isImplicitName() {
        return (value.isNotNull() && attributeSeparator.isNull() && name.isNotNull());
    }

    public boolean isClass() {
        return (isImplicitName() && name.equals(".")) || (!isImplicitName() && name.equals(Attribute.CLASS_ATTR));
    }

    public boolean isId() {
        return (isImplicitName() && name.equals("#")) || (!isImplicitName() && name.equals(Attribute.ID_ATTR));
    }

    public BasedSequence getName() {
        return name;
    }

    public void setName(BasedSequence name) {
        this.name = name;
    }

    public BasedSequence getAttributeSeparator() {
        return attributeSeparator;
    }

    public void setAttributeSeparator(BasedSequence attributeSeparator) {
        this.attributeSeparator = attributeSeparator;
    }

    public BasedSequence getValue() {
        return value;
    }

    public void setValue(BasedSequence value) {
        this.value = value;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }
}
