package com.vladsch.flexmark.ext.youtube.embedded;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class YouTubeLinkVisitorExt {
    public static <V extends YouTubeLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(YouTubeLink.class, visitor::visit)
        };
    }
}
