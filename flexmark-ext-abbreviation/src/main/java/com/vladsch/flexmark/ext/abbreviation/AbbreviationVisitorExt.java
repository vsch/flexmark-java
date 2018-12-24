package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class AbbreviationVisitorExt {
    public static <V extends AbbreviationVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<AbbreviationBlock>(AbbreviationBlock.class, new Visitor<AbbreviationBlock>() {
                    @Override
                    public void visit(AbbreviationBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Abbreviation>(Abbreviation.class, new Visitor<Abbreviation>() {
                    @Override
                    public void visit(Abbreviation node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
