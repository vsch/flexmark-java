package org.commonmark.internal;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.SegmentedSequence;
import org.commonmark.internal.util.SubSequence;

import java.util.ArrayList;
import java.util.List;

public class BlockContent {
    private ArrayList<BasedSequence> lines = null;

    public BasedSequence getLine(int line) {
        return lines.get(line);
    }

    public BasedSequence getSpanningChars() {
        return lines != null ? new SubSequence(lines.get(0).getBase(), lines.get(0).getStartOffset(), lines.get(lines.size() - 1).getEndOffset()) : SubSequence.EMPTY;
    }

    public List<BasedSequence> getLines() {
        return lines;
    }

    public int getLineCount() {
        return lines == null ? 0 : lines.size();
    }

    public BlockContent() {
    }

    public BlockContent(BlockContent other, int startLine, int endLine) {
        // copy content from other
        if (other.lines != null) {
            lines = new ArrayList<>(endLine - startLine);

            for (int i = startLine; i < endLine; i++) {
                lines.add(other.lines.get(i));
            }
        }
    }

    public int getStartOffset() {
        return lines != null ? lines.get(0).getStartOffset() : -1;
    }

    public int getEndOffset() {
        return lines != null ? lines.get(lines.size() - 1).getEndOffset() : -1;
    }

    public int getSourceLength() {
        return lines != null ? lines.get(lines.size() - 1).getEndOffset() - lines.get(0).getStartOffset() : -1;
    }

    public void add(BasedSequence line) {
        lines.add(line);
    }

    public void addAll(List<BasedSequence> lines) {
        lines.addAll(lines);
    }

    public boolean hasSingleLine() {
        return lines != null && lines.size() == 1;
    }

    public BasedSequence getContents() {
        if (lines == null) return SubSequence.EMPTY;
        return getContents(0, lines.size());
    }

    public BasedSequence getContents(int startLine, int endLine) {
        if (lines == null) return SubSequence.EMPTY;

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

        if (endLine - startLine == 1) {
            return lines.get(startLine);
        } else {
            return new SegmentedSequence(lines.subList(startLine, endLine));
        }
    }

    public String getString() {
        if (lines == null) return "";

        StringBuilder sb = new StringBuilder();
        for (BasedSequence line : lines) sb.append(line);

        return sb.toString();
    }
}
