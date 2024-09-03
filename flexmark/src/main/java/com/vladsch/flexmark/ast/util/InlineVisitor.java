package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;

public interface InlineVisitor {
  void visit(AutoLink node);

  void visit(Code node);

  void visit(Emphasis node);

  void visit(HardLineBreak node);

  void visit(HtmlEntity node);

  void visit(HtmlInline node);

  void visit(HtmlInlineComment node);

  void visit(Image node);

  void visit(ImageRef node);

  void visit(Link node);

  void visit(LinkRef node);

  void visit(MailLink node);

  void visit(SoftLineBreak node);

  void visit(StrongEmphasis node);

  void visit(Text node);
}
