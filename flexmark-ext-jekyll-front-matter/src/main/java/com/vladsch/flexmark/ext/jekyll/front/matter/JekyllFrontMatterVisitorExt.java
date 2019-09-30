package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class JekyllFrontMatterVisitorExt {
    public static <V extends JekyllFrontMatterVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(JekyllFrontMatterBlock.class, visitor::visit),
        };
    }
}
