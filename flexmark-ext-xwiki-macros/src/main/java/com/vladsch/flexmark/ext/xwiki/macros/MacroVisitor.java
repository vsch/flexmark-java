package com.vladsch.flexmark.ext.xwiki.macros;

public interface MacroVisitor {
    void visit(final Macro node);
    void visit(final MacroBlock node);
}
