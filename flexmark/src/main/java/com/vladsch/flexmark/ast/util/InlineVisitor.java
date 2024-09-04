package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.AutoLink;
import com.vladsch.flexmark.ast.Code;
import com.vladsch.flexmark.ast.Emphasis;
import com.vladsch.flexmark.ast.HardLineBreak;
import com.vladsch.flexmark.ast.HtmlEntity;
import com.vladsch.flexmark.ast.HtmlInline;
import com.vladsch.flexmark.ast.HtmlInlineComment;
import com.vladsch.flexmark.ast.Image;
import com.vladsch.flexmark.ast.ImageRef;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.LinkRef;
import com.vladsch.flexmark.ast.MailLink;
import com.vladsch.flexmark.ast.SoftLineBreak;
import com.vladsch.flexmark.ast.StrongEmphasis;
import com.vladsch.flexmark.ast.Text;

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
