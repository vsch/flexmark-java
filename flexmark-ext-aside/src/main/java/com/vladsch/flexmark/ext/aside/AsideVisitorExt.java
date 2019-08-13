package com.vladsch.flexmark.ext.aside;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class AsideVisitorExt {
    public static <V extends AsideVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                // new VisitHandler<>(ExtAside.class, visitor::visit),
                new VisitHandler<AsideBlock>(AsideBlock.class, new Visitor<AsideBlock>() {
                    @Override
                    public void visit(AsideBlock node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
