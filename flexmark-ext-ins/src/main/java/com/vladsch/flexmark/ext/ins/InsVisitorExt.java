package com.vladsch.flexmark.ext.ins;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class InsVisitorExt {
    public static <V extends InsVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                // @formatter:off
                new VisitHandler<Ins>(Ins.class, new Visitor<Ins>() { @Override public void visit(Ins node) { visitor.visit(node); } }),
                // @formatter:on
        };
    }
}
