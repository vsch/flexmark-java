package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class StrikethroughVisitorExt {
    static <V extends StrikethroughVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Strikethrough.class, new Visitor<Strikethrough>() {
                    @Override
                    public void visit(Strikethrough node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
