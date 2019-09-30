package com.vladsch.flexmark.ext.gfm.users;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class GfmUsersVisitorExt {
    public static <V extends GfmUsersVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(GfmUser.class, visitor::visit),
        };
    }
}
