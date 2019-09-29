package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class SpecExampleVisitorExt {
    public static <V extends SpecExampleVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<SpecExampleAst>(SpecExampleAst.class, visitor::visit),
                new VisitHandler<SpecExampleBlock>(SpecExampleBlock.class, visitor::visit),
                new VisitHandler<SpecExampleHtml>(SpecExampleHtml.class, visitor::visit),
                new VisitHandler<SpecExampleOption>(SpecExampleOption.class, visitor::visit),
                new VisitHandler<SpecExampleOptionSeparator>(SpecExampleOptionSeparator.class, visitor::visit),
                new VisitHandler<SpecExampleOptionsList>(SpecExampleOptionsList.class, visitor::visit),
                new VisitHandler<SpecExampleSeparator>(SpecExampleSeparator.class, visitor::visit),
                new VisitHandler<SpecExampleSource>(SpecExampleSource.class, visitor::visit),
        };
    }
}
