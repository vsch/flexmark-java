package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
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
