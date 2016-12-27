package com.vladsch.flexmark.ext.spec.example;

public interface SpecExampleVisitor {
    void visit(final SpecExampleAst node);
    void visit(final SpecExampleBlock node);
    void visit(final SpecExampleHtml node);
    void visit(final SpecExampleOption node);
    void visit(final SpecExampleOptionSeparator node);
    void visit(final SpecExampleOptionsList node);
    void visit(final SpecExampleSeparator node);
    void visit(final SpecExampleSource node);
}
