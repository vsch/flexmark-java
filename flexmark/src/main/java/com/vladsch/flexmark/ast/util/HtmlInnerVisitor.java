package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.HtmlInnerBlock;
import com.vladsch.flexmark.ast.HtmlInnerBlockComment;

public interface HtmlInnerVisitor {
  void visit(HtmlInnerBlock node);

  void visit(HtmlInnerBlockComment node);
}
