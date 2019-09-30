package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class StrikethroughVisitorExt {
    public static <V extends StrikethroughVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Strikethrough.class, visitor::visit),
        };
    }
}
