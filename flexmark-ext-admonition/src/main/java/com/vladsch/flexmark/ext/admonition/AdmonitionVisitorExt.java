package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class AdmonitionVisitorExt {
    public static <V extends AdmonitionVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AdmonitionBlock.class, visitor::visit),
        };
    }
}
