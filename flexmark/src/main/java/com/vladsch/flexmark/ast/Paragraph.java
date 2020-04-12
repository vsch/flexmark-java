package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.BlockContent;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.TextContainer;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Paragraph extends Block implements TextContainer {
    final private static int[] EMPTY_INDENTS = new int[0];
    private int[] lineIndents = EMPTY_INDENTS;
    private boolean trailingBlankLine = false;
    private boolean hasTableSeparator;

    @NotNull
    @Override
    public BasedSequence[] getSegments() {
        return EMPTY_SEGMENTS;
    }

    @Override
    public void getAstExtra(@NotNull StringBuilder out) {
        super.getAstExtra(out);
        if (trailingBlankLine) out.append(" isTrailingBlankLine");
    }

    public Paragraph() {
    }

    public Paragraph(BasedSequence chars) {
        super(chars);
    }

    public Paragraph(BasedSequence chars, List<BasedSequence> lineSegments, List<Integer> lineIndents) {
        super(chars, lineSegments);
        if (lineSegments.size() != lineIndents.size())
            throw new IllegalArgumentException("line segments and line indents have to be of the same size");
        setLineIndents(lineIndents);
    }

    public Paragraph(BasedSequence chars, List<BasedSequence> lineSegments, int[] lineIndents) {
        super(chars, lineSegments);
        if (lineSegments.size() != lineIndents.length)
            throw new IllegalArgumentException("line segments and line indents have to be of the same size");
        this.lineIndents = lineIndents;
    }

    public Paragraph(BlockContent blockContent) {
        super(blockContent);
        setLineIndents(blockContent.getLineIndents());
    }

    protected void setLineIndents(List<Integer> lineIndents) {
        this.lineIndents = new int[lineIndents.size()];
        int i = 0;
        for (int indent : lineIndents) {
            this.lineIndents[i++] = indent;
        }
    }

    @Override
    // FIX: add indent tracking then deprecate. ContentNode does not have indents
//    @Deprecated
    public void setContent(@NotNull BasedSequence chars, @NotNull List<BasedSequence> lineSegments) {
        super.setContent(chars, lineSegments);
    }

    public void setContent(BasedSequence chars, List<BasedSequence> lineSegments, List<Integer> lineIndents) {
        super.setContent(chars, lineSegments);
        if (lineSegments.size() != lineIndents.size())
            throw new IllegalArgumentException("line segments and line indents have to be of the same size");
        setLineIndents(lineIndents);
    }

    @Override
    // FIX: add indent tracking then deprecate. ContentNode does not have indents
//    @Deprecated
    public void setContent(@NotNull List<BasedSequence> lineSegments) {
        super.setContent(lineSegments);
    }

    @Override
    public void setContent(@NotNull BlockContent blockContent) {
        super.setContent(blockContent);
        setLineIndents(blockContent.getLineIndents());
    }

    public void setContent(BlockContent blockContent, int startLine, int endLine) {
        super.setContent(blockContent.getLines().subList(startLine, endLine));
        setLineIndents(blockContent.getLineIndents().subList(startLine, endLine));
    }

    public void setContent(Paragraph other, int startLine, int endLine) {
        super.setContent(other.getContentLines(startLine, endLine));
        if (endLine > startLine) {
            int[] lineIndents = new int[endLine - startLine];
            System.arraycopy(other.lineIndents, startLine, lineIndents, 0, endLine - startLine);
            this.lineIndents = lineIndents;
        } else {
            this.lineIndents = EMPTY_INDENTS;
        }
    }

    public void setLineIndents(int[] lineIndents) {
        this.lineIndents = lineIndents;
    }

    public int getLineIndent(int line) {
        return lineIndents[line];
    }

    public int[] getLineIndents() {
        return lineIndents;
    }

    public boolean isTrailingBlankLine() {
        return trailingBlankLine;
    }

    public void setTrailingBlankLine(boolean trailingBlankLine) {
        this.trailingBlankLine = trailingBlankLine;
    }

    public void setHasTableSeparator(boolean hasTableSeparator) {
        this.hasTableSeparator = hasTableSeparator;
    }

    public boolean hasTableSeparator() {
        return hasTableSeparator;
    }

    @Override
    public boolean collectText(ISequenceBuilder<? extends ISequenceBuilder<?, BasedSequence>, BasedSequence> out, int flags, NodeVisitor nodeVisitor) {
        if (!out.isEmpty()) {
            out.add("\n\n");
        }
        return true;
    }
}
