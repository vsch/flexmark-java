package com.vladsch.flexmark.ext.admonition;

public interface AdmonitionVisitor {
    // void visit(Admonition node);
    void visit(AdmonitionBlock node);
}
