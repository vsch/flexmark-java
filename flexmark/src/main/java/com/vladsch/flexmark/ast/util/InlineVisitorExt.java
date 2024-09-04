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
import com.vladsch.flexmark.util.ast.VisitHandler;

public class InlineVisitorExt {
  public static <V extends InlineVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
    return new VisitHandler<?>[] {
      new VisitHandler<>(AutoLink.class, visitor::visit),
      new VisitHandler<>(Code.class, visitor::visit),
      new VisitHandler<>(Emphasis.class, visitor::visit),
      new VisitHandler<>(HardLineBreak.class, visitor::visit),
      new VisitHandler<>(HtmlEntity.class, visitor::visit),
      new VisitHandler<>(HtmlInline.class, visitor::visit),
      new VisitHandler<>(HtmlInlineComment.class, visitor::visit),
      new VisitHandler<>(Image.class, visitor::visit),
      new VisitHandler<>(ImageRef.class, visitor::visit),
      new VisitHandler<>(Link.class, visitor::visit),
      new VisitHandler<>(LinkRef.class, visitor::visit),
      new VisitHandler<>(MailLink.class, visitor::visit),
      new VisitHandler<>(SoftLineBreak.class, visitor::visit),
      new VisitHandler<>(StrongEmphasis.class, visitor::visit),
      new VisitHandler<>(Text.class, visitor::visit),
    };
  }
}
