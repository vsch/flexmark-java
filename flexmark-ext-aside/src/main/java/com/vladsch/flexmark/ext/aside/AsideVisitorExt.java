package com.vladsch.flexmark.ext.aside;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class AsideVisitorExt {
    public static <V extends AsideVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AsideBlock.class, visitor::visit),
        };
    }
}
