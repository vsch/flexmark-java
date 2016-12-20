package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class SpecExampleVisitorExt {
    static <V extends SpecExampleVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(SpecExampleAst.class, new Visitor<SpecExampleAst>() {
                    @Override
                    public void visit(SpecExampleAst node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleBlock.class, new Visitor<SpecExampleBlock>() {
                    @Override
                    public void visit(SpecExampleBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleHtml.class, new Visitor<SpecExampleHtml>() {
                    @Override
                    public void visit(SpecExampleHtml node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleOption.class, new Visitor<SpecExampleOption>() {
                    @Override
                    public void visit(SpecExampleOption node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleOptionSeparator.class, new Visitor<SpecExampleOptionSeparator>() {
                    @Override
                    public void visit(SpecExampleOptionSeparator node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleOptionsList.class, new Visitor<SpecExampleOptionsList>() {
                    @Override
                    public void visit(SpecExampleOptionsList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleSeparator.class, new Visitor<SpecExampleSeparator>() {
                    @Override
                    public void visit(SpecExampleSeparator node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<>(SpecExampleSource.class, new Visitor<SpecExampleSource>() {
                    @Override
                    public void visit(SpecExampleSource node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
