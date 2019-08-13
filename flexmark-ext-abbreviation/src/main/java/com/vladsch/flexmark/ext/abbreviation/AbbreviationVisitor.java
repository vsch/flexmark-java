package com.vladsch.flexmark.ext.abbreviation;

public interface AbbreviationVisitor {
    void visit(AbbreviationBlock node);
    void visit(Abbreviation node);
}
