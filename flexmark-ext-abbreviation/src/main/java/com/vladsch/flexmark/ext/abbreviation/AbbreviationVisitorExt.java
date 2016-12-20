package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class AbbreviationVisitorExt {
    static <V extends AbbreviationVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AbbreviationBlock.class, new Visitor<AbbreviationBlock>() {
                    @Override
                    public void visit(AbbreviationBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(Abbreviation.class, new Visitor<Abbreviation>() {
                    @Override
                    public void visit(Abbreviation node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
