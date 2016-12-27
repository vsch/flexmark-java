package com.vladsch.flexmark.ext.typographic;

public interface TypographicVisitor {
    void visit(final TypographicSmarts node);
    void visit(final TypographicQuotes node);
}
