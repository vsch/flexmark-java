package com.vladsch.flexmark.ext.youtube.embedded;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class YouTubeLinkVisitorExt {
    public static <V extends YouTubeLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<YouTubeLink>(YouTubeLink.class, new Visitor<YouTubeLink>() {
                    @Override
                    public void visit(YouTubeLink node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
