package com.vladsch.flexmark.attributes;

public interface AttributesVisitor {
    void visit(final AttributesNode node);
    void visit(final AttributeNode node);
    // void visit(final AttributesBlock node);
}
