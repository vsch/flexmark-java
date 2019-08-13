package com.vladsch.flexmark.ext.spec.example;

public interface SpecExampleVisitor {
    void visit(SpecExampleAst node);
    void visit(SpecExampleBlock node);
    void visit(SpecExampleHtml node);
    void visit(SpecExampleOption node);
    void visit(SpecExampleOptionSeparator node);
    void visit(SpecExampleOptionsList node);
    void visit(SpecExampleSeparator node);
    void visit(SpecExampleSource node);
}
