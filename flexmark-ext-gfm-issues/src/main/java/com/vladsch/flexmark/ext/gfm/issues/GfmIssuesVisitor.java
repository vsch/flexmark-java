package com.vladsch.flexmark.ext.gfm.issues;

public interface GfmIssuesVisitor {
    void visit(GfmIssue node);
    // void visit(GitHubIssuesBlock node);
}
