package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class MacroVisitorExt {
    public static <V extends MacroVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Macro.class, visitor::visit),
                new VisitHandler<>(MacroClose.class, visitor::visit),
                new VisitHandler<>(MacroBlock.class, visitor::visit),
        };
    }
}
