package com.vladsch.flexmark.ext.abbreviation;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class AbbreviationVisitorExt {
    public static <V extends AbbreviationVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AbbreviationBlock.class, visitor::visit),
                new VisitHandler<>(Abbreviation.class, visitor::visit),
        };
    }
}
