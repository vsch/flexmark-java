package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class AudioLinkVisitorExt {
    public static <V extends AudioLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AudioLink.class, visitor::visit)
        };
    }
}
