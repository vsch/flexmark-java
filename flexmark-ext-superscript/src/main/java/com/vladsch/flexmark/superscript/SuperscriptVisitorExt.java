package com.vladsch.flexmark.superscript;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class SuperscriptVisitorExt {
    public static <V extends SuperscriptVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<Superscript>(Superscript.class, visitor::visit),
        };
    }
}
