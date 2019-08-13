package com.vladsch.flexmark.ext.gfm.users;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class GfmUsersVisitorExt {
    public static <V extends GfmUsersVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                // @formatter:off
                new VisitHandler<GfmUser>(GfmUser.class, new Visitor<GfmUser>() { @Override public void visit(GfmUser node) { visitor.visit(node); } }),
                // new VisitHandler<GitHubUsersBlock>(GitHubUsersBlock.class, new Visitor<GitHubUsersBlock>() { @Override public void visit(GitHubUsersBlock node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
