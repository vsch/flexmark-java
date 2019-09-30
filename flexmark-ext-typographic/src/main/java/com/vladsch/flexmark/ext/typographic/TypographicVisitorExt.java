package com.vladsch.flexmark.ext.typographic;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class TypographicVisitorExt {
    public static <V extends TypographicVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(TypographicSmarts.class, visitor::visit),
                new VisitHandler<>(TypographicQuotes.class, visitor::visit),
        };
    }
}
