package com.vladsch.flexmark.ext.gfm.issues;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class GfmIssuesVisitorExt {
    public static <V extends GfmIssuesVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(GfmIssue.class, visitor::visit),
        };
    }
}
