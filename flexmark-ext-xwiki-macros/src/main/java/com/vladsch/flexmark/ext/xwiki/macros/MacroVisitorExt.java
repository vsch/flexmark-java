package com.vladsch.flexmark.ext.xwiki.macros;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class MacroVisitorExt {
    public static <V extends MacroVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<Macro>(Macro.class, visitor::visit),
                new VisitHandler<MacroClose>(MacroClose.class, visitor::visit),
                new VisitHandler<MacroBlock>(MacroBlock.class, visitor::visit),
        };
    }
}
