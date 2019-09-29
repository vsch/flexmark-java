package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class DefinitionVisitorExt {
    public static <V extends DefinitionVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<DefinitionItem>(DefinitionItem.class, visitor::visit),
                new VisitHandler<DefinitionList>(DefinitionList.class, visitor::visit),
                new VisitHandler<DefinitionTerm>(DefinitionTerm.class, visitor::visit),
                new VisitHandler<DefinitionItem>(DefinitionItem.class, visitor::visit),
        };
    }
}
