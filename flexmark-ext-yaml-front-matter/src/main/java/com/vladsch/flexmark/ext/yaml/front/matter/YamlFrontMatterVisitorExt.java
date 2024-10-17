package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.VisitHandler;

class YamlFrontMatterVisitorExt {
  static <V extends YamlFrontMatterVisitor> VisitHandler<?>[] visitHandlers(V visitor) {
    return new VisitHandler<?>[] {
      new VisitHandler<>(YamlFrontMatterNode.class, visitor::visit),
      new VisitHandler<>(YamlFrontMatterBlock.class, visitor::visit),
    };
  }

  private YamlFrontMatterVisitorExt() {
    throw new IllegalStateException();
  }
}
