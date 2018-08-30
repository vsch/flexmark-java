package com.vladsch.flexmark.ext.gitlab;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class GitLabVisitorExt {
    public static <V extends GitLabVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
// @formatter:off
                new VisitHandler<GitLabIns>(GitLabIns.class, new Visitor<GitLabIns>() { @Override public void visit(GitLabIns node) { visitor.visit(node); } }),
                new VisitHandler<GitLabDel>(GitLabDel.class, new Visitor<GitLabDel>() { @Override public void visit(GitLabDel node) { visitor.visit(node); } }),
                new VisitHandler<GitLabInlineMath>(GitLabInlineMath.class, new Visitor<GitLabInlineMath>() { @Override public void visit(GitLabInlineMath node) { visitor.visit(node); } }),
                new VisitHandler<GitLabBlockQuote>(GitLabBlockQuote.class, new Visitor<GitLabBlockQuote>() { @Override public void visit(GitLabBlockQuote node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
