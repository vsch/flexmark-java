package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.sequence.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class LineCollectingVisitor {
    final private NodeVisitor myVisitor;
    private List<Range> myLines;
    private List<Integer> myEOLs;
    private int myStartOffset;
    private int myEndOffset;

    public LineCollectingVisitor() {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, this::visit),
                new VisitHandler<>(TextBase.class, this::visit),
                new VisitHandler<>(HtmlEntity.class, this::visit),
                new VisitHandler<>(HtmlInline.class, this::visit),
                new VisitHandler<>(SoftLineBreak.class, this::visit),
                new VisitHandler<>(HardLineBreak.class, this::visit)
        );

        myLines = Collections.EMPTY_LIST;
    }

    private void finalizeLines() {
        if (myStartOffset < myEndOffset) {
            Range range = Range.of(myStartOffset, myEndOffset);
            myLines.add(range);
            myEOLs.add(0);
            myStartOffset = myEndOffset;
        }
    }

    public List<Range> getLines() {
        finalizeLines();
        return myLines;
    }

    public List<Integer> getEOLs() {
        finalizeLines();
        return myEOLs;
    }

    public void collect(Node node) {
        myLines = new ArrayList<>();
        myEOLs = new ArrayList<>();
        myStartOffset = node.getStartOffset();
        myEndOffset = node.getEndOffset();
        myVisitor.visit(node);
    }

    public List<Range> collectAndGetRanges(Node node) {
        collect(node);
        return getLines();
    }

    private void visit(SoftLineBreak node) {
        Range range = Range.of(myStartOffset, node.getEndOffset());
        myLines.add(range);
        myEOLs.add(node.getTextLength());
        myStartOffset = node.getEndOffset();
    }

    private void visit(HardLineBreak node) {
        Range range = Range.of(myStartOffset, node.getEndOffset());
        myLines.add(range);
        myEOLs.add(node.getTextLength());
        myStartOffset = node.getEndOffset();
    }

    private void visit(HtmlEntity node) {
        myEndOffset = node.getEndOffset();
    }

    private void visit(HtmlInline node) {
        myEndOffset = node.getEndOffset();
    }

    private void visit(Text node) {
        myEndOffset = node.getEndOffset();
    }

    private void visit(TextBase node) {
        myEndOffset = node.getEndOffset();
    }
}
