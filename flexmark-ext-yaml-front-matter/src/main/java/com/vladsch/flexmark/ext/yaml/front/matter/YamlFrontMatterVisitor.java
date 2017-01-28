package com.vladsch.flexmark.ext.yaml.front.matter;

public interface YamlFrontMatterVisitor {
    void visit(final YamlFrontMatterNode node);
    void visit(final YamlFrontMatterBlock node);
}
