package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class TypographicVisitorExt {
    public static <V extends TypographicVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<TypographicSmarts>(TypographicSmarts.class, new Visitor<TypographicSmarts>() {
                    @Override
                    public void visit(TypographicSmarts node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<TypographicQuotes>(TypographicQuotes.class, new Visitor<TypographicQuotes>() {
                    @Override
                    public void visit(TypographicQuotes node1) {
                        visitor.visit(node1);
                    }
                }),
        };
    }
}
