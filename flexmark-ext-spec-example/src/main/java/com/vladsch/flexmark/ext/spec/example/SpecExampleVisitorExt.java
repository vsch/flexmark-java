package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class SpecExampleVisitorExt {
    public static <V extends SpecExampleVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(SpecExampleAst.class, visitor::visit),
                new VisitHandler<>(SpecExampleBlock.class, visitor::visit),
                new VisitHandler<>(SpecExampleHtml.class, visitor::visit),
                new VisitHandler<>(SpecExampleOption.class, visitor::visit),
                new VisitHandler<>(SpecExampleOptionSeparator.class, visitor::visit),
                new VisitHandler<>(SpecExampleOptionsList.class, visitor::visit),
                new VisitHandler<>(SpecExampleSeparator.class, visitor::visit),
                new VisitHandler<>(SpecExampleSource.class, visitor::visit),
        };
    }
}
