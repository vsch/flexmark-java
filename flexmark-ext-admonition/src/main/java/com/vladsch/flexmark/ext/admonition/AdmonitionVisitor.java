package com.vladsch.flexmark.ext.admonition;

public interface AdmonitionVisitor {
    void visit(AdmonitionBlock node);
}
