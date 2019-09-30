package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class AnchorLinkVisitorExt {
    public static <V extends AnchorLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AnchorLink.class, visitor::visit),
        };
    }
}
