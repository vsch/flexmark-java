package com.vladsch.flexmark.ext.yaml.front.matter;

interface YamlFrontMatterVisitor {
  void visit(YamlFrontMatterNode node);

  void visit(YamlFrontMatterBlock node);
}
