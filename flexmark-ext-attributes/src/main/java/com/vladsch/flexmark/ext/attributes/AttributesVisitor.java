package com.vladsch.flexmark.ext.attributes;

public interface AttributesVisitor {
    void visit(AttributesNode node);
    void visit(AttributeNode node);
}
