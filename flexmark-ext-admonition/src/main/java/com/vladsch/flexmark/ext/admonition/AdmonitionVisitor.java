package com.vladsch.flexmark.ext.admonition;

public interface AdmonitionVisitor {
    // void visit(final Admonition node);
    void visit(final AdmonitionBlock node);
}
