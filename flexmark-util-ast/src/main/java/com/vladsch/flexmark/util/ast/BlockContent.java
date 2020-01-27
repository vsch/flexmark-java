package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BlockContent {
    // list of line text
    final private ArrayList<BasedSequence> lines = new ArrayList<>();
    final private ArrayList<Integer> lineIndents = new ArrayList<>();

    public @NotNull BasedSequence getLine(int line) {
        return lines.get(line);
    }

    public @NotNull BasedSequence getSpanningChars() {
        return lines.size() > 0 ? lines.get(0).baseSubSequence(lines.get(0).getStartOffset(), lines.get(lines.size() - 1).getEndOffset()) : BasedSequence.NULL;
    }

    public @NotNull List<BasedSequence> getLines() {
        return lines;
    }

    public @NotNull List<Integer> getLineIndents() {
        return lineIndents;
    }

    public int getLineCount() {
        return lines.size();
    }

    public BlockContent() {
    }

    public BlockContent(@NotNull BlockContent other, int startLine, int lineIndent) {
        // copy content from other
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

    public void add(@NotNull BasedSequence lineWithEOL, int lineIndent) {
        lines.add(lineWithEOL);
        lineIndents.add(lineIndent);
    }

    public void addAll(@NotNull List<BasedSequence> lines, List<Integer> lineIndents) {
        assert lines.size() == lineIndents.size() : "lines and lineIndents should be of the same size";
        this.lines.addAll(lines);
        this.lineIndents.addAll(lineIndents);
    }

    public boolean hasSingleLine() {
        return lines.size() == 1;
    }

    public @NotNull BasedSequence getContents() {
        if (lines.size() == 0) return BasedSequence.NULL;
        return getContents(0, lines.size());
    }

    public @NotNull BlockContent subContents(int startLine, int endLine) {
        return new BlockContent(this, startLine, endLine);
    }

    public @NotNull BasedSequence getContents(int startLine, int endLine) {
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

        return SegmentedSequence.create(lines.get(0), lines.subList(startLine, endLine));
    }

    public @NotNull String getString() {
        if (lines.size() == 0) return "";

        StringBuilder sb = new StringBuilder();

        for (BasedSequence line : lines) {
            sb.append(line.trimEOL());
            sb.append('\n');
        }

        return sb.toString();
    }
}
