package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class GitLabVisitorExt {
    public static <V extends GitLabVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<GitLabIns>(GitLabIns.class, visitor::visit),
                new VisitHandler<GitLabDel>(GitLabDel.class, visitor::visit),
                new VisitHandler<GitLabInlineMath>(GitLabInlineMath.class, visitor::visit),
                new VisitHandler<GitLabBlockQuote>(GitLabBlockQuote.class, visitor::visit),
        };
    }
}
