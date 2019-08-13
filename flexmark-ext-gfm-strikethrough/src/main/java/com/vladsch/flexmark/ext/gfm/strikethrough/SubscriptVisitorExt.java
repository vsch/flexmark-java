package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class SubscriptVisitorExt {
    public static <V extends SubscriptVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<Subscript>(Subscript.class, new Visitor<Subscript>() {
                    @Override
                    public void visit(Subscript node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
