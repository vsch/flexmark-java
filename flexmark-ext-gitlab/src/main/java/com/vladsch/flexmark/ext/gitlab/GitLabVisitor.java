package com.vladsch.flexmark.ext.gitlab;

public interface GitLabVisitor {
    void visit(GitLabIns node);
    void visit(GitLabDel node);
    void visit(GitLabInlineMath node);
    void visit(GitLabBlockQuote node);
}
