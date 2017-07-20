package com.vladsch.flexmark.ext.gfm.issues;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class GfmIssuesVisitorExt {
    public static <V extends GfmIssuesVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                // @formatter:off
                new VisitHandler<GfmIssue>(GfmIssue.class, new Visitor<GfmIssue>() {
                    @Override
                    public void visit(GfmIssue node) { visitor.visit(node); }
                }),
                // new VisitHandler<GitHubIssuesBlock>(GitHubIssuesBlock.class, new Visitor<GitHubIssuesBlock>() { @Override public void visit(GitHubIssuesBlock node) { visitor.visit(node); } }),
                // @formatter:on
        };
    }
}
