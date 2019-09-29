package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class EmbedLinkVisitorExt {
    public static <V extends EmbedLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(EmbedLink.class, visitor::visit)
        };
    }
}
