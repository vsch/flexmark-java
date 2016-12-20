package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class AnchorLinkVisitorExt {
    static <V extends AnchorLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AnchorLink.class, new Visitor<AnchorLink>() {
                    @Override
                    public void visit(AnchorLink node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
