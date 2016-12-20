package com.vladsch.flexmark.ext.aside;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class AsideVisitorExt {
    static <V extends AsideVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                // new VisitHandler<>(ExtAside.class, visitor::visit),
                new VisitHandler<>(AsideBlock.class, new Visitor<AsideBlock>() {
                    @Override
                    public void visit(AsideBlock node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
