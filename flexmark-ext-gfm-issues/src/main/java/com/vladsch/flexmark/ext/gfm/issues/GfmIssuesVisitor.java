package com.vladsch.flexmark.ext.gfm.issues;

public interface GfmIssuesVisitor {
    void visit(final GfmIssue node);
    // void visit(final GitHubIssuesBlock node);
}
