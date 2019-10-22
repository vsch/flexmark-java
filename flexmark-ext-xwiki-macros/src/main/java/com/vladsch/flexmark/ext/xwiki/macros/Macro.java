package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * A macros node
 */
@SuppressWarnings("WeakerAccess")
public class Macro extends Node {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence name = BasedSequence.NULL;
    protected BasedSequence attributeText = BasedSequence.NULL;
    protected BasedSequence closingMarker = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        //return EMPTY_SEGMENTS;
        return new BasedSequence[] { openingMarker, name, attributeText, closingMarker };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, name, "name");
        segmentSpanChars(out, attributeText, "attributes");
        segmentSpanChars(out, closingMarker, "close");
        if (isClosedTag()) out.append(" isClosed");
        if (isBlockMacro()) out.append(" isBlockMacro");
        segmentSpanChars(out, getMacroContentChars(), "macroContent");
    }

    public Macro() {
    }

    public Macro(BasedSequence chars) {
        super(chars);
    }

    public Macro(BasedSequence openingMarker, BasedSequence name, BasedSequence closingMarker) {
        super(openingMarker.baseSubSequence(openingMarker.getStartOffset(), closingMarker.getEndOffset()));
        this.openingMarker = openingMarker;
        this.name = name;
        this.closingMarker = closingMarker;
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

    public void setName(BasedSequence name) {
        this.name = name;
    }

    public BasedSequence getClosingMarker() {
        return closingMarker;
    }

    public void setClosingMarker(BasedSequence closingMarker) {
        this.closingMarker = closingMarker;
    }

    public BasedSequence getAttributeText() {
        return attributeText;
    }

    public void setAttributeText(BasedSequence attributeText) {
        this.attributeText = attributeText;
    }

    public boolean isBlockMacro() {
        Node parent = getParent();
        return parent instanceof MacroBlock && parent.getFirstChild() == this;
    }

    public Map<String, String> getAttributes() {
        Map<String, String> attributes = new LinkedHashMap<>();
        Node child = getFirstChild();
        while (child != null) {
            if (child instanceof MacroAttribute) {
                MacroAttribute attribute = (MacroAttribute) child;
                attributes.put(attribute.getAttribute().toString(), attribute.getValue().toString());
            }
            child = child.getNext();
        }
        return attributes;
    }

    public BasedSequence getMacroContentChars() {
        Node lastChild = getLastChild();
        int startOffset = getClosingMarker().getEndOffset();
        int endOffset = lastChild == null || lastChild instanceof MacroAttribute ? getEndOffset() : lastChild.getStartOffset();
        return isClosedTag() ? BasedSequence.NULL : getChars().baseSubSequence(startOffset, endOffset);
    }

    public boolean isClosedTag() {
        return getClosingMarker().length() > 2;
    }
}
