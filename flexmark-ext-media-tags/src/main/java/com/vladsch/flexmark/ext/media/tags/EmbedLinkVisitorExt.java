package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class EmbedLinkVisitorExt {
    public static <V extends EmbedLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(EmbedLink.class, new Visitor<EmbedLink>() {
                    @Override
                    public void visit(EmbedLink node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
