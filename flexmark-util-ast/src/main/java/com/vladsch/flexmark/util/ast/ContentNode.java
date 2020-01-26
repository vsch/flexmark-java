package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public abstract class ContentNode extends Node implements Content {
    protected List<BasedSequence> lineSegments = BasedSequence.EMPTY_LIST;

    public ContentNode() {

    }

    public ContentNode(@NotNull BasedSequence chars) {
        super(chars);
    }

    public ContentNode(@NotNull BasedSequence chars, @NotNull List<BasedSequence> lineSegments) {
        super(chars);
        this.lineSegments = lineSegments;
    }

    public ContentNode(@NotNull List<BasedSequence> lineSegments) {
        this(getSpanningChars(lineSegments), lineSegments);
    }

    public ContentNode(@NotNull BlockContent blockContent) {
        this(blockContent.getSpanningChars(), blockContent.getLines());
    }

    public void setContent(@NotNull BasedSequence chars, @NotNull List<BasedSequence> lineSegments) {
        setChars(chars);
        this.lineSegments = lineSegments;
    }

    public void setContent(@NotNull List<BasedSequence> lineSegments) {
        this.lineSegments = lineSegments;
        setChars(getSpanningChars());
    }

    public void setContent(@NotNull BlockContent blockContent) {
        setChars(blockContent.getSpanningChars());
        this.lineSegments = blockContent.getLines();
    }

    @Override
    public @NotNull BasedSequence getSpanningChars() {
        return getSpanningChars(lineSegments);
    }

    private static @NotNull BasedSequence getSpanningChars(@NotNull List<BasedSequence> lineSegments) {
        return lineSegments.isEmpty() ? BasedSequence.NULL : lineSegments.get(0).baseSubSequence(lineSegments.get(0).getStartOffset(), lineSegments.get(lineSegments.size() - 1).getEndOffset());
    }

    @Override
    public int getLineCount() {
        return lineSegments.size();
    }

    @NotNull
    @Override
    public BasedSequence getLineChars(int index) {
        return lineSegments.get(index);
    }

    @NotNull
    @Override
    public List<BasedSequence> getContentLines() {
        return lineSegments;
    }

    @NotNull
    @Override
    public List<BasedSequence> getContentLines(int startLine, int endLine) {
        return lineSegments.subList(startLine, endLine);
    }

    @NotNull
    @Override
    public BasedSequence getContentChars() {
        return lineSegments.isEmpty() ? BasedSequence.NULL : SegmentedSequence.create(lineSegments.get(0), lineSegments);
    }

    @NotNull
    @Override
    public BasedSequence getContentChars(int startLine, int endLine) {
        return lineSegments.isEmpty() ? BasedSequence.NULL : SegmentedSequence.create(lineSegments.get(0), getContentLines(startLine, endLine));
    }

    public void setContentLines(@NotNull List<BasedSequence> contentLines) {
        this.lineSegments = contentLines;
    }

    public void setContentLine(int lineIndex, @NotNull BasedSequence contentLine) {
        ArrayList<BasedSequence> lines = new ArrayList<>(lineSegments);
        lines.set(lineIndex, contentLine);
        this.lineSegments = lines;
        setCharsFromContent();
    }
}
