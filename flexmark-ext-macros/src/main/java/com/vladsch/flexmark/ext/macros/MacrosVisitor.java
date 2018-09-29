package com.vladsch.flexmark.ext.macros;

public interface MacrosVisitor {
    void visit(final MacroReference node);
    void visit(final MacroDefinitionBlock node);
}
