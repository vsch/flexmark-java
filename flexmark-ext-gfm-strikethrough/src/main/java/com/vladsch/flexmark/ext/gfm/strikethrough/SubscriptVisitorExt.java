package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class SubscriptVisitorExt {
    public static <V extends SubscriptVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Subscript.class, new Visitor<Subscript>() {
                    @Override
                    public void visit(Subscript node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
