package com.vladsch.flexmark.ext.anchorlink;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class AnchorLinkVisitorExt {
    public static <V extends AnchorLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<AnchorLink>(AnchorLink.class, new Visitor<AnchorLink>() {
                    @Override
                    public void visit(AnchorLink node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
