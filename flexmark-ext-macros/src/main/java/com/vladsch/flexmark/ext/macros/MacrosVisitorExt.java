package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class MacrosVisitorExt {
    public static <V extends MacrosVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<MacroReference>(MacroReference.class, visitor::visit),
                new VisitHandler<MacroDefinitionBlock>(MacroDefinitionBlock.class, visitor::visit),
        };
    }
}
