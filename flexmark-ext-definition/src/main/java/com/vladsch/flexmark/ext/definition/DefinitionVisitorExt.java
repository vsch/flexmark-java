package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class DefinitionVisitorExt {
    public static <V extends DefinitionVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(DefinitionItem.class, visitor::visit),
                new VisitHandler<>(DefinitionList.class, visitor::visit),
                new VisitHandler<>(DefinitionTerm.class, visitor::visit),
                new VisitHandler<>(DefinitionItem.class, visitor::visit),
        };
    }
}
