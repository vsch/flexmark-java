package com.vladsch.flexmark.attributes;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class AttributesVisitorExt {
    public static <V extends AttributesVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
// @formatter:off
                new VisitHandler<AttributesNode>(AttributesNode.class, new Visitor<AttributesNode>() { @Override public void visit(AttributesNode node) { visitor.visit(node); } }),
                new VisitHandler<AttributeNode>(AttributeNode.class, new Visitor<AttributeNode>() { @Override public void visit(AttributeNode node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
