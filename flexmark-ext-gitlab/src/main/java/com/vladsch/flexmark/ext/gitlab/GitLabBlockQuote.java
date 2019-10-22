package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.ParagraphContainer;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * A GitLab block node
 */
public class GitLabBlockQuote extends Block implements ParagraphContainer {
    private BasedSequence openingMarker = BasedSequence.NULL;
    private BasedSequence openingTrailing = BasedSequence.NULL;
    private BasedSequence closingMarker = BasedSequence.NULL;
    private BasedSequence closingTrailing = BasedSequence.NULL;

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        segmentSpanChars(out, openingMarker, "open");
        segmentSpanChars(out, openingTrailing, "openTrail");
        segmentSpanChars(out, closingMarker, "close");
        segmentSpanChars(out, closingTrailing, "closeTrail");
    }

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return new BasedSequence[] { openingMarker, openingTrailing, closingMarker, closingTrailing };
    }

    @Override
    public boolean isParagraphEndWrappingDisabled(Paragraph node) {
        return node == getLastChild() || node.getNext() instanceof GitLabBlockQuote;
    }

    @Override
    public boolean isParagraphStartWrappingDisabled(Paragraph node) {
        return node == getFirstChild() || node.getPrevious() instanceof GitLabBlockQuote;
    }

    public GitLabBlockQuote() {
    }

    public GitLabBlockQuote(BasedSequence chars) {
        super(chars);
    }

    public GitLabBlockQuote(BasedSequence chars, List<BasedSequence> segments) {
        super(chars, segments);
    }

    public GitLabBlockQuote(BlockContent blockContent) {
        super(blockContent);
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

    public BasedSequence getOpeningTrailing() {
        return openingTrailing;
    }

    public void setOpeningTrailing(BasedSequence openingTrailing) {
        this.openingTrailing = openingTrailing;
    }

    public BasedSequence getClosingTrailing() {
        return closingTrailing;
    }

    public void setClosingTrailing(BasedSequence closingTrailing) {
        this.closingTrailing = closingTrailing;
    }
}
