package com.vladsch.flexmark.ext.tables;

public interface TableVisitor {
  void visit(TableBlock node);

  void visit(TableHead node);

  void visit(TableSeparator node);

  void visit(TableBody node);

  void visit(TableRow node);

  void visit(TableCell node);

  void visit(TableCaption node);
}
