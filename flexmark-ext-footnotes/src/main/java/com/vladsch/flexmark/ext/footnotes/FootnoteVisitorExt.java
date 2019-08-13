package com.vladsch.flexmark.ext.footnotes;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class FootnoteVisitorExt {
    public static <V extends FootnoteVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<FootnoteBlock>(FootnoteBlock.class, new Visitor<FootnoteBlock>() {
                    @Override
                    public void visit(FootnoteBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Footnote>(Footnote.class, new Visitor<Footnote>() {
                    @Override
                    public void visit(Footnote node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
