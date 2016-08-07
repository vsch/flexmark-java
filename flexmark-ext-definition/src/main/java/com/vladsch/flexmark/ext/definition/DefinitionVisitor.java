
package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.VisitHandler;

public interface DefinitionVisitor {
    static <V extends DefinitionVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(DefinitionList.class, visitor::visit),
                new VisitHandler<>(DefinitionTerm.class, visitor::visit),
                new VisitHandler<>(DefinitionItem.class, visitor::visit),
        };
    }

    // void visit(Definition node);
    void visit(DefinitionList node);
    void visit(DefinitionTerm node);
    void visit(DefinitionItem node);
}
