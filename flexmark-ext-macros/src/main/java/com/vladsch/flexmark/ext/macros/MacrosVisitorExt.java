package com.vladsch.flexmark.ext.macros;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class MacrosVisitorExt {
    public static <V extends MacrosVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                // @formatter:off
                new VisitHandler<MacroReference>(MacroReference.class, new Visitor<MacroReference>() { @Override public void visit(MacroReference node) { visitor.visit(node); } }),
                new VisitHandler<MacroDefinitionBlock>(MacroDefinitionBlock.class, new Visitor<MacroDefinitionBlock>() { @Override public void visit(MacroDefinitionBlock node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
