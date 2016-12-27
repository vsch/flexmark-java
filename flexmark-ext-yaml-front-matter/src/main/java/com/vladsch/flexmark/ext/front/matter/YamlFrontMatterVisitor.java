package com.vladsch.flexmark.ext.front.matter;

public interface YamlFrontMatterVisitor {
    void visit(final YamlFrontMatterNode node);
    void visit(final YamlFrontMatterBlock node);
}
