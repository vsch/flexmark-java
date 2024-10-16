package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

class AbstractYamlFrontMatterVisitor implements YamlFrontMatterVisitor {
  private final Map<String, List<String>> data;
  private final NodeVisitor myVisitor;

  AbstractYamlFrontMatterVisitor() {
    myVisitor = new NodeVisitor(YamlFrontMatterVisitorExt.visitHandlers(this));
    data = new LinkedHashMap<>();
  }

  void visit(Node node) {
    myVisitor.visit(node);
  }

  @Override
  public void visit(YamlFrontMatterNode node) {
    data.put(node.getKey(), node.getValues());
  }

  @Override
  public void visit(YamlFrontMatterBlock node) {
    myVisitor.visitChildren(node);
  }

  Map<String, List<String>> getData() {
    return data;
  }
}
