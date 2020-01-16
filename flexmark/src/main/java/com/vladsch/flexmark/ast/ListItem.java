package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.util.ast.BlankLineContainer;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class ListItem extends Block implements ParagraphItemContainer, BlankLineContainer, ParagraphContainer {
    protected BasedSequence openingMarker = BasedSequence.NULL;
    protected BasedSequence markerSuffix = BasedSequence.NULL;
    private boolean tight = true;
    private boolean hadBlankAfterItemParagraph = false;
    private boolean containsBlankLine = false;
    private int priority = Integer.MIN_VALUE;

    public ListItem() {
    }

    public ListItem(ListItem other) {
        this.openingMarker = other.openingMarker;
        this.markerSuffix = other.markerSuffix;
        this.tight = other.tight;
        this.hadBlankAfterItemParagraph = other.hadBlankAfterItemParagraph;
        this.containsBlankLine = other.containsBlankLine;
        this.priority = other.priority;

        takeChildren(other);
        setCharsFromContent();
    }

    public ListItem(BasedSequence chars) {
        super(chars);
    }

    public ListItem(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public ListItem(BlockContent blockContent) {
        super(blockContent);
    }

    public boolean isOrderedItem() {
        return false;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, markerSuffix, "openSuffix");
        if (isTight()) out.append(" isTight");
        else out.append(" isLoose");
        if (isHadBlankAfterItemParagraph()) out.append(" hadBlankLineAfter");
        else if (isContainsBlankLine()) out.append(" hadBlankLine");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, markerSuffix };
    }

    public boolean canChangeMarker() {
        return true;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public BasedSequence getMarkerSuffix() {
        return markerSuffix;
    }

    public void setMarkerSuffix(BasedSequence markerSuffix) {
        assert markerSuffix.isNull() || openingMarker.getBase() == markerSuffix.getBase();

        this.markerSuffix = markerSuffix;
    }

    public void setTight(boolean tight) {
        this.tight = tight;
    }

    public void setLoose(boolean loose) {
        this.tight = !loose;
    }

    public boolean isTight() {
        return tight && isInTightList();
    }

    public boolean isOwnTight() {
        return tight;
    }

    public boolean isLoose() {
        return !isTight();
    }

    @Override
    public boolean isParagraphEndWrappingDisabled(Paragraph node) {
        return getFirstChild() != node && getLastChild() == node || getFirstChild() == node && (getParent() == null || getParent().getLastChildAny(ListItem.class) == this);
    }

    @Override
    public boolean isParagraphStartWrappingDisabled(Paragraph node) {
        return isItemParagraph(node);
    }

    @Override
    public boolean isParagraphInTightListItem(Paragraph node) {
        if (!isTight()) return false;

        // see if this is the first paragraph child item
        return isItemParagraph(node);
    }

    public boolean isItemParagraph(Paragraph node) {
        // see if this is the first paragraph child item
        Node child = getFirstChild();
        while (child != null && !(child instanceof Paragraph)) child = child.getNext();
        return child == node;
    }

    @Override
    public boolean isParagraphWrappingDisabled(Paragraph node, ListOptions listOptions, DataHolder options) {
        assert node.getParent() == this;
        return listOptions.isInTightListItem(node);
    }

    public boolean isInTightList() {
        return !(getParent() instanceof ListBlock) || ((ListBlock) getParent()).isTight();
    }

    public boolean isHadBlankAfterItemParagraph() {
        return hadBlankAfterItemParagraph;
    }

    public boolean isContainsBlankLine() {
        return containsBlankLine;
    }

    public void setContainsBlankLine(boolean containsBlankLine) {
        this.containsBlankLine = containsBlankLine;
    }

    @SuppressWarnings("SameParameterValue")
    public void setHadBlankAfterItemParagraph(boolean hadBlankAfterItemParagraph) {
        this.hadBlankAfterItemParagraph = hadBlankAfterItemParagraph;
    }

    @Override
    public Node getLastBlankLineChild() {
        return getLastChild();
    }
}
