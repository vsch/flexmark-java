package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.BulletList;
import com.vladsch.flexmark.ast.BulletListItem;
import com.vladsch.flexmark.ast.FencedCodeBlock;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.HtmlBlock;
import com.vladsch.flexmark.ast.HtmlCommentBlock;
import com.vladsch.flexmark.ast.IndentedCodeBlock;
import com.vladsch.flexmark.ast.OrderedList;
import com.vladsch.flexmark.ast.OrderedListItem;
import com.vladsch.flexmark.ast.Paragraph;
import com.vladsch.flexmark.ast.Reference;
import com.vladsch.flexmark.ast.ThematicBreak;
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
