package com.vladsch.flexmark.ext.ins;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class InsVisitorExt {
    public static <V extends InsVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Ins.class, visitor::visit),
        };
    }
}
