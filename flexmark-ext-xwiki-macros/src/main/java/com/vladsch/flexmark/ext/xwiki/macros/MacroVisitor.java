package com.vladsch.flexmark.ext.xwiki.macros;

public interface MacroVisitor {
    void visit(Macro node);
    void visit(MacroClose node);
    void visit(MacroBlock node);
}
