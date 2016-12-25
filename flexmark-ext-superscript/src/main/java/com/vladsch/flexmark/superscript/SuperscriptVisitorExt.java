package com.vladsch.flexmark.superscript;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class SuperscriptVisitorExt {
    public static <V extends SuperscriptVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
// @formatter:off
                new VisitHandler<>(Superscript.class, new Visitor<Superscript>() { @Override public void visit(Superscript node) { visitor.visit(node); } }),
                // new VisitHandler<>(SuperscriptBlock.class, new Visitor<SuperscriptBlock>() { @Override public void visit(SuperscriptBlock node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
