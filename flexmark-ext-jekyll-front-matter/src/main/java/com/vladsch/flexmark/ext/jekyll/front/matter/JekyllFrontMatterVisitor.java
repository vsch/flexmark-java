
package com.vladsch.flexmark.ext.jekyll.front.matter;

import com.vladsch.flexmark.internal.util.ast.VisitHandler;

public interface JekyllFrontMatterVisitor {
    static <V extends JekyllFrontMatterVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(JekyllFrontMatterBlock.class, visitor::visit),
        };
    }

    void visit(JekyllFrontMatterBlock node);
}
