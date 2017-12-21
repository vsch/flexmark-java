package com.vladsch.flexmark.attributes;

import com.vladsch.flexmark.ast.CustomNode;
import com.vladsch.flexmark.ast.DoNotDecorate;
import com.vladsch.flexmark.util.sequence.BasedSequence;

/**
 * An Attribute node representing a single attribute name and value in attributes node
 */
public class AttributeNode extends CustomNode implements DoNotDecorate {
    protected BasedSequence name = BasedSequence.NULL;
    protected BasedSequence attributeSeparator = BasedSequence.NULL;
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence value = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { name, attributeSeparator, openingMarker, value, closingMarker };
    }

    @Override
    public void getAstExtra(StringBuilder out) {
        if (name.isNotNull()) out.append(" name: ").append(name);
        if (attributeSeparator.isNotNull()) out.append(attributeSeparator);
        delimitedSegmentSpanChars(out, openingMarker, value, closingMarker, "value");
        if (isClass()) out.append(" isClass");
        if (isId()) out.append(" isId");
    }

    public AttributeNode() {
    }

    public AttributeNode(BasedSequence chars) {
        super(chars);
    }

    public AttributeNode(BasedSequence name, BasedSequence attributeSeparator, BasedSequence openingMarker, BasedSequence value, BasedSequence closingMarker) {
        super(spanningChars(name, attributeSeparator, openingMarker, value, closingMarker));
        this.name = name != null ? name : BasedSequence.NULL;
        this.attributeSeparator = attributeSeparator != null ? attributeSeparator : BasedSequence.NULL;
        this.openingMarker = openingMarker != null ? openingMarker : BasedSequence.NULL;
        this.value = value != null ? value : BasedSequence.NULL;
        this.closingMarker = closingMarker != null ? closingMarker : BasedSequence.NULL;
    }

    public static boolean isImplicitName(CharSequence name) {
        return name.length() > 0 && (name.charAt(0) == '.' || name.charAt(0) == '#');
    }


    public boolean isImplicitName() {
        return (value.isNotNull() && name.isNull() && isImplicitName(value));
    }

    public boolean isClass() {
        return (value.isNotNull() && name.isNull() && value.startsWith("."));

    }

    public boolean isId() {
        return (value.isNotNull() && name.isNull() && value.startsWith("#"));
    }

    public BasedSequence getImplicitNameValue() {
        return isImplicitName() ? value.subSequence(1) : value;
    }

    public BasedSequence getName() {
        return name;
    }

    public void setName(final BasedSequence name) {
        this.name = name;
    }

    public BasedSequence getAttributeSeparator() {
        return attributeSeparator;
    }

    public void setAttributeSeparator(final BasedSequence attributeSeparator) {
        this.attributeSeparator = attributeSeparator;
    }

    public BasedSequence getValue() {
        return value;
    }

    public void setValue(final BasedSequence value) {
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
