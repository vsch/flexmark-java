package com.vladsch.flexmark.ext.gitlab;

public interface GitLabVisitor {
    void visit(final GitLabIns node);
    void visit(final GitLabDel node);
    void visit(final GitLabInlineMath node);
    void visit(final GitLabBlockQuote node);
}
