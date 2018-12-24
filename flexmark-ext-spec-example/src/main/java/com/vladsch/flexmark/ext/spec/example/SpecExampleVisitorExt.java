package com.vladsch.flexmark.ext.spec.example;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class SpecExampleVisitorExt {
    public static <V extends SpecExampleVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<SpecExampleAst>(SpecExampleAst.class, new Visitor<SpecExampleAst>() {
                    @Override
                    public void visit(SpecExampleAst node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleBlock>(SpecExampleBlock.class, new Visitor<SpecExampleBlock>() {
                    @Override
                    public void visit(SpecExampleBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleHtml>(SpecExampleHtml.class, new Visitor<SpecExampleHtml>() {
                    @Override
                    public void visit(SpecExampleHtml node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleOption>(SpecExampleOption.class, new Visitor<SpecExampleOption>() {
                    @Override
                    public void visit(SpecExampleOption node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleOptionSeparator>(SpecExampleOptionSeparator.class, new Visitor<SpecExampleOptionSeparator>() {
                    @Override
                    public void visit(SpecExampleOptionSeparator node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleOptionsList>(SpecExampleOptionsList.class, new Visitor<SpecExampleOptionsList>() {
                    @Override
                    public void visit(SpecExampleOptionsList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleSeparator>(SpecExampleSeparator.class, new Visitor<SpecExampleSeparator>() {
                    @Override
                    public void visit(SpecExampleSeparator node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SpecExampleSource>(SpecExampleSource.class, new Visitor<SpecExampleSource>() {
                    @Override
                    public void visit(SpecExampleSource node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
