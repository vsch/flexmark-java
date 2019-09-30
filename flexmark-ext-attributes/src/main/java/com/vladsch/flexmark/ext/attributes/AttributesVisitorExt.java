package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class AttributesVisitorExt {
    public static <V extends AttributesVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(AttributesNode.class, visitor::visit),
                new VisitHandler<>(AttributeNode.class, visitor::visit),
        };
    }
}
