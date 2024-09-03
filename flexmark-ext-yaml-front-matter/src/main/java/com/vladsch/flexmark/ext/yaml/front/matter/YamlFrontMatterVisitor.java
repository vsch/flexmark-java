package com.vladsch.flexmark.ext.yaml.front.matter;

public interface YamlFrontMatterVisitor {
  void visit(YamlFrontMatterNode node);

  void visit(YamlFrontMatterBlock node);
}
