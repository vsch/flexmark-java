package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;

import java.util.ArrayList;
import java.util.List;

public class BlockContent {
    // list of line text
    private final ArrayList<BasedSequence> lines = new ArrayList<BasedSequence>();
    private final ArrayList<Integer> lineIndents = new ArrayList<Integer>();

    public BasedSequence getLine(int line) {
        return lines.get(line);
    }

    public BasedSequence getSpanningChars() {
        return lines.size() > 0 ? lines.get(0).baseSubSequence(lines.get(0).getStartOffset(), lines.get(lines.size() - 1).getEndOffset()) : BasedSequence.NULL;
    }

    public List<BasedSequence> getLines() {
        return lines;
    }

    public List<Integer> getLineIndents() {
        return lineIndents;
    }

    public int getLineCount() {
        return lines.size();
    }

    public BlockContent() {
    }

    public BlockContent(BlockContent other, int startLine, int lineIndent) {
        // copy content from other
        assert lines.size() == lineIndents.size() : "lines and eols should be of the same size";
        assert other.lines.size() == other.lineIndents.size() : "lines and eols should be of the same size";

        if (other.lines.size() > 0 && startLine < lineIndent) {
            lines.addAll(other.lines.subList(startLine, lineIndent));
            lineIndents.addAll(other.lineIndents.subList(startLine, lineIndent));
        }
    }

    public int getStartOffset() {
        return lines.size() > 0 ? lines.get(0).getStartOffset() : -1;
    }

    public int getEndOffset() {
        return lines.size() > 0 ? lines.get(lines.size() - 1).getEndOffset() : -1;
    }

    public int getLineIndent() {
        return lines.size() > 0 ? lineIndents.get(0) : 0;
    }

    public int getSourceLength() {
        return lines.size() > 0 ? lines.get(lines.size() - 1).getEndOffset() - lines.get(0).getStartOffset() : -1;
    }

    public void add(BasedSequence lineWithEOL, int lineIndent) {
        lines.add(lineWithEOL);
        lineIndents.add(lineIndent);
    }

    public void addAll(List<BasedSequence> lines, List<Integer> lineIndents) {
        assert lines.size() == lineIndents.size() : "lines and lineIndents should be of the same size";
        this.lines.addAll(lines);
        this.lineIndents.addAll(lineIndents);
    }

    public boolean hasSingleLine() {
        return lines.size() > 0 && lines.size() == 1;
    }

    public BasedSequence getContents() {
        if (lines.size() == 0) return BasedSequence.NULL;
        return getContents(0, lines.size());
    }

    public BlockContent subContents(int startLine, int endLine) {
        return new BlockContent(this, startLine, endLine);
    }

    public BasedSequence getContents(int startLine, int endLine) {
        if (lines.size() == 0) return BasedSequence.NULL;

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
            throw new IndexOutOfBoundsException("endLine must not be greater than line cardinality");
        }

        return SegmentedSequence.of(lines.subList(startLine, endLine));
    }

    public String getString() {
        if (lines.size() == 0) return "";

        StringBuilder sb = new StringBuilder();

        for (BasedSequence line : lines) {
            sb.append(line.trimEOL());
            sb.append('\n');
        }

        return sb.toString();
    }
}
