package com.vladsch.flexmark.ext.definition;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class DefinitionVisitorExt {
    static <V extends DefinitionVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(DefinitionItem.class, new Visitor<DefinitionItem>() {
                    @Override
                    public void visit(DefinitionItem node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(DefinitionList.class, new Visitor<DefinitionList>() {
                    @Override
                    public void visit(DefinitionList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(DefinitionTerm.class, new Visitor<DefinitionTerm>() {
                    @Override
                    public void visit(DefinitionTerm node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(DefinitionItem.class, new Visitor<DefinitionItem>() {
                    @Override
                    public void visit(DefinitionItem node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
