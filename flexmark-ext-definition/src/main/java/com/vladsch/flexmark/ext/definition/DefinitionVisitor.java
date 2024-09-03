package com.vladsch.flexmark.ext.definition;

public interface DefinitionVisitor {
  void visit(DefinitionList node);

  void visit(DefinitionTerm node);

  void visit(DefinitionItem node);
}
