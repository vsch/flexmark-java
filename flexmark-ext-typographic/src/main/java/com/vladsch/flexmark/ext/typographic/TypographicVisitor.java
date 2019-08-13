package com.vladsch.flexmark.ext.typographic;

public interface TypographicVisitor {
    void visit(TypographicSmarts node);
    void visit(TypographicQuotes node);
}
