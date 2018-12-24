package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;

import java.util.ArrayList;
import java.util.List;

public abstract class ContentNode extends Node implements Content {
    protected List<BasedSequence> lineSegments = BasedSequence.EMPTY_LIST;

    public ContentNode() {

    }

    public ContentNode(BasedSequence chars) {
        super(chars);
    }

    public ContentNode(BasedSequence chars, List<BasedSequence> lineSegments) {
        super(chars);
        this.lineSegments = lineSegments;
    }

    public ContentNode(List<BasedSequence> lineSegments) {
        this(getSpanningChars(lineSegments), lineSegments);
    }

    public ContentNode(BlockContent blockContent) {
        this(blockContent.getSpanningChars(), blockContent.getLines());
    }

    public void setContent(BasedSequence chars, List<BasedSequence> lineSegments) {
        setChars(chars);
        this.lineSegments = lineSegments;
    }

    public void setContent(List<BasedSequence> lineSegments) {
        this.lineSegments = lineSegments;
        setChars(getSpanningChars());
    }

    public void setContent(BlockContent blockContent) {
        setChars(blockContent.getSpanningChars());
        this.lineSegments = blockContent.getLines();
    }

    @Override
    public BasedSequence getSpanningChars() {
        return getSpanningChars(lineSegments);
    }

    private static BasedSequence getSpanningChars(List<BasedSequence> lineSegments) {
        return lineSegments.isEmpty() ? BasedSequence.NULL : lineSegments.get(0).baseSubSequence(lineSegments.get(0).getStartOffset(), lineSegments.get(lineSegments.size() - 1).getEndOffset());
    }

    @Override
    public int getLineCount() {
        return lineSegments.size();
    }

    @Override
    public BasedSequence getLineChars(int index) {
        return lineSegments.get(index);
    }

    @Override
    public List<BasedSequence> getContentLines() {
        return lineSegments;
    }

    @Override
    public List<BasedSequence> getContentLines(int startLine, int endLine) {
        return lineSegments.subList(startLine, endLine);
    }

    @Override
    public BasedSequence getContentChars() {
        return SegmentedSequence.of(lineSegments);
    }

    @Override
    public BasedSequence getContentChars(int startLine, int endLine) {
        return SegmentedSequence.of(getContentLines(startLine, endLine));
    }

    public void setContentLines(List<BasedSequence> contentLines) {
        this.lineSegments = contentLines;
    }

    public void setContentLine(int lineIndex, BasedSequence contentLine) {
        ArrayList<BasedSequence> lines = new ArrayList<BasedSequence>(lineSegments);
        lines.set(lineIndex, contentLine);
        this.lineSegments = lines;
        setCharsFromContent();
    }
}
