package com.vladsch.flexmark.ext.gfm.users;

public interface GfmUsersVisitor {
    void visit(final GfmUser node);
    // void visit(final GitHubUsersBlock node);
}
