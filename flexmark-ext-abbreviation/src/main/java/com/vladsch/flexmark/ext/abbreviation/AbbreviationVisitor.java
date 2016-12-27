package com.vladsch.flexmark.ext.abbreviation;

public interface AbbreviationVisitor {
    void visit(final AbbreviationBlock node);
    void visit(final Abbreviation node);
}
