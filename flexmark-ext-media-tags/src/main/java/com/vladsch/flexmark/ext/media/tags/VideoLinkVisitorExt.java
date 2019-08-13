package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class VideoLinkVisitorExt {
    public static <V extends VideoLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(VideoLink.class, new Visitor<VideoLink>() {
                    @Override
                    public void visit(VideoLink node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
