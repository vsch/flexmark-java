package com.vladsch.flexmark.superscript;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class SuperscriptVisitorExt {
    public static <V extends SuperscriptVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                // @formatter:off
                new VisitHandler<Superscript>(Superscript.class, new Visitor<Superscript>() { @Override public void visit(Superscript node) { visitor.visit(node); } }),
                // new VisitHandler<>(SuperscriptBlock.class, new Visitor<SuperscriptBlock>() { @Override public void visit(SuperscriptBlock node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
