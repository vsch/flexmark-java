package org.commonmark.internal;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SegmentedSequence;
import org.commonmark.internal.util.SubSequence;

import java.util.ArrayList;
import java.util.List;

public class BlockContent {
    public static final List<Integer> EMPTY_EOL_OFFSETS = new ArrayList<>();

    // list of line text
    final private ArrayList<BasedSequence> lines = new ArrayList<>();
    final private ArrayList<Integer> eolLengths = new ArrayList<>();

    public BasedSequence getLine(int line) {
        return lines.get(line);
    }

    public BasedSequence getSpanningChars() {
        return lines.size() > 0 ? new SubSequence(lines.get(0).getBase(), lines.get(0).getStartOffset(), lines.get(lines.size() - 1).getEndOffset()) : SubSequence.EMPTY;
    }

    public List<BasedSequence> getLines() {
        return lines;
    }

    public List<Integer> getEolLengths() {
        return eolLengths;
    }

    public int getLineCount() {
        return lines.size() == 0 ? 0 : lines.size();
    }

    public BlockContent() {
    }

    public BlockContent(BlockContent other, int startLine, int endLine) {
        // copy content from other
        assert lines.size() == eolLengths.size() : "lines and eols should be of the same size";
        assert other.lines.size() == other.eolLengths.size() : "lines and eols should be of the same size";

        if (other.lines.size() > 0 && startLine < endLine) {
            lines.addAll(other.lines.subList(startLine, endLine));
            eolLengths.addAll(other.eolLengths.subList(startLine, endLine));
        }
    }

    public int getStartOffset() {
        return lines.size() > 0 ? lines.get(0).getStartOffset() : -1;
    }

    public int getEndOffset() {
        return lines.size() > 0 ? lines.get(lines.size() - 1).getEndOffset() : -1;
    }

    public int getEolLength() {
        return lines.size() > 0 ? eolLengths.get(lines.size() - 1) : 0;
    }

    public int getSourceLength() {
        return lines.size() > 0 ? lines.get(lines.size() - 1).getEndOffset() - lines.get(0).getStartOffset() : -1;
    }

    public void add(BasedSequence line, int eolOffset) {
        lines.add(line);
        eolLengths.add(eolOffset);
    }

    public void addAll(List<BasedSequence> lines, List<Integer> eolOffsets) {
        assert lines.size() == eolOffsets.size() : "lines and eolOffsets should be of the same size";
        this.lines.addAll(lines);
        this.eolLengths.addAll(eolOffsets);
    }

    public boolean hasSingleLine() {
        return lines.size() > 0 && lines.size() == 1;
    }

    public BasedSequence getContents() {
        if (lines.size() == 0) return SubSequence.EMPTY;
        return getContents(0, lines.size());
    }

    public BasedSequence getContents(int startLine, int endLine) {
        if (lines.size() == 0) return SubSequence.EMPTY;

        if (startLine < 0) {
            throw new IndexOutOfBoundsException("startLine must be at least 0");
        }
        if (endLine < 0) {
            throw new IndexOutOfBoundsException("endLine must be at least 0");
        }
        if (endLine < startLine) {
            throw new IndexOutOfBoundsException("endLine must not be less than startLine");
        }
        if (endLine > lines.size()) {
            throw new IndexOutOfBoundsException("endLine must not be greater than line count");
        }

        return SegmentedSequence.of(lines.subList(startLine, endLine), SubSequence.EMPTY);
    }

    public String getString() {
        if (lines.size() == 0) return "";

        StringBuilder sb = new StringBuilder();

        for (BasedSequence line : lines) {
            sb.append(line);
            sb.append('\n');
        }

        return sb.toString();
    }
}
