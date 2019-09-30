package com.vladsch.flexmark.ext.jekyll.tag;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class JekyllTagVisitorExt {
    public static <V extends JekyllTagVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(JekyllTag.class, visitor::visit),
                new VisitHandler<>(JekyllTagBlock.class, visitor::visit),
        };
    }
}
