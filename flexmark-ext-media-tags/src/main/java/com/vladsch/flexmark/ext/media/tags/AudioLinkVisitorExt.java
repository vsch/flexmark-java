package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class AudioLinkVisitorExt {
    public static <V extends AudioLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AudioLink.class, new Visitor<AudioLink>() {
                    @Override
                    public void visit(AudioLink node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
