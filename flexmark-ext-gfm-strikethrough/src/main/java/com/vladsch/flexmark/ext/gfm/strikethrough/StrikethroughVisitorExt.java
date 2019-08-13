package com.vladsch.flexmark.ext.gfm.strikethrough;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class StrikethroughVisitorExt {
    public static <V extends StrikethroughVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<Strikethrough>(Strikethrough.class, new Visitor<Strikethrough>() {
                    @Override
                    public void visit(Strikethrough node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
