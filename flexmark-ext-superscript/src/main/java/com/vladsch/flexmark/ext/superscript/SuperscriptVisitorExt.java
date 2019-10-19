package com.vladsch.flexmark.ext.superscript;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class SuperscriptVisitorExt {
    public static <V extends SuperscriptVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Superscript.class, visitor::visit),
        };
    }
}
