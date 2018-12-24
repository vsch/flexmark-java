package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;
import com.vladsch.flexmark.util.sequence.Range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class LineCollectingVisitor {
    private final NodeVisitor myVisitor;
    private List<Range> myLines;
    private List<Integer> myEOLs;
    private int myStartOffset;
    private int myEndOffset;

    public LineCollectingVisitor() {
        myVisitor = new NodeVisitor(
                new VisitHandler<Text>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text node) {
                        LineCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<TextBase>(TextBase.class, new Visitor<TextBase>() {
                    @Override
                    public void visit(TextBase node) {
                        LineCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HtmlEntity>(HtmlEntity.class, new Visitor<HtmlEntity>() {
                    @Override
                    public void visit(HtmlEntity node) {
                        LineCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HtmlInline>(HtmlInline.class, new Visitor<HtmlInline>() {
                    @Override
                    public void visit(HtmlInline node) {
                        LineCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<SoftLineBreak>(SoftLineBreak.class, new Visitor<SoftLineBreak>() {
                    @Override
                    public void visit(SoftLineBreak node) {
                        LineCollectingVisitor.this.visit(node);
                    }
                }),
                new VisitHandler<HardLineBreak>(HardLineBreak.class, new Visitor<HardLineBreak>() {
                    @Override
                    public void visit(HardLineBreak node) {
                        LineCollectingVisitor.this.visit(node);
                    }
                })
        );

        myLines = Collections.EMPTY_LIST;
    }

    private void finalizeLines() {
        if (myStartOffset < myEndOffset) {
            Range range = new Range(myStartOffset, myEndOffset);
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
        myLines = new ArrayList<Range>();
        myEOLs = new ArrayList<Integer>();
        myStartOffset = node.getStartOffset();
        myEndOffset = node.getEndOffset();
        myVisitor.visit(node);
    }

    public List<Range> collectAndGetRanges(Node node) {
        collect(node);
        return getLines();
    }

    private void visit(SoftLineBreak node) {
        Range range = new Range(myStartOffset, node.getEndOffset());
        myLines.add(range);
        myEOLs.add(node.getTextLength());
        myStartOffset = node.getEndOffset();
    }

    private void visit(HardLineBreak node) {
        Range range = new Range(myStartOffset, node.getEndOffset());
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
