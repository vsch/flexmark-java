package com.vladsch.flexmark.ext.macros;

public interface MacrosVisitor {
    void visit(MacroReference node);
    void visit(MacroDefinitionBlock node);
}
