package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class VideoLinkVisitorExt {
    public static <V extends VideoLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(VideoLink.class, visitor::visit)
        };
    }
}
