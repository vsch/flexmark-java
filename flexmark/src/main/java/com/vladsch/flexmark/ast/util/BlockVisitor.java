package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Document;

public interface BlockVisitor {
  void visit(BlockQuote node);

  void visit(BulletList node);

  void visit(Document node);

  void visit(FencedCodeBlock node);

  void visit(Heading node);

  void visit(HtmlBlock node);

  void visit(HtmlCommentBlock node);

  void visit(IndentedCodeBlock node);

  void visit(BulletListItem node);

  void visit(OrderedListItem node);

  void visit(OrderedList node);

  void visit(Paragraph node);

  void visit(Reference node);

  void visit(ThematicBreak node);
}
