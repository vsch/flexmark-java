package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.ParagraphContainer;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * An Admonition block node
 */
public class AdmonitionBlock extends Block implements ParagraphContainer {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence info = BasedSequence.NULL;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] {
                openingMarker,
                info
        };
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, info, "info");
    }

    public AdmonitionBlock() {
    }

    public AdmonitionBlock(BasedSequence chars) {
        super(chars);
    }

    public AdmonitionBlock(BasedSequence chars, BasedSequence openingMarker, BasedSequence info, List<BasedSequence> segments) {
        super(chars, segments);
        this.openingMarker = openingMarker;
        this.info = info;
    }

    public BasedSequence getOpeningMarker() {
        return openingMarker;
    }

    public void setOpeningMarker(BasedSequence openingMarker) {
        this.openingMarker = openingMarker;
    }

    public void setInfo(BasedSequence info) {
        this.info = info;
    }

    public BasedSequence getInfo() {
        return info;
    }

    @Nullable
    public AdmonitionTitle getTitleNode() {
        Node child = getFirstChild();
        if (child instanceof AdmonitionTitle)
            return (AdmonitionTitle) child;
        return null;
    }

    public BasedSequence getTitle() {
        AdmonitionTitle title = getTitleNode();
        return title == null ? BasedSequence.NULL : title.getText();
    }

    public BasedSequence getTitleOpeningMarker() {
        AdmonitionTitle title = getTitleNode();
        return title == null ? BasedSequence.NULL : title.getOpeningMarker();
    }

    public BasedSequence getTitleClosingMarker() {
        AdmonitionTitle title = getTitleNode();
        return title == null ? BasedSequence.NULL : title.getClosingMarker();
    }

    public BasedSequence getTitleChars() {
        AdmonitionTitle title = getTitleNode();
        return title == null ? BasedSequence.NULL : title.getChars();
    }

    @Override
    public boolean isParagraphEndWrappingDisabled(Paragraph node) {
        return false;
    }

    @Override
    public boolean isParagraphStartWrappingDisabled(Paragraph node) {
        if (node == getFirstChild()) {
            // need to see if there is a blank line between it and our start
            int ourEOL = getChars().getBaseSequence().endOfLine(getChars().getStartOffset());
            int childStartEOL = node.getStartOfLine();
            return ourEOL + 1 == childStartEOL;
        }
        return false;
    }
}
