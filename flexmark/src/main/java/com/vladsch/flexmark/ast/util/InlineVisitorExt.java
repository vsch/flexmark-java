package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.VisitHandler;

public class InlineVisitorExt {
    public static <V extends InlineVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<AutoLink>(AutoLink.class, visitor::visit),
                new VisitHandler<Code>(Code.class, visitor::visit),
                new VisitHandler<Emphasis>(Emphasis.class, visitor::visit),
                new VisitHandler<HardLineBreak>(HardLineBreak.class, visitor::visit),
                new VisitHandler<HtmlEntity>(HtmlEntity.class, visitor::visit),
                new VisitHandler<HtmlInline>(HtmlInline.class, visitor::visit),
                new VisitHandler<HtmlInlineComment>(HtmlInlineComment.class, visitor::visit),
                new VisitHandler<Image>(Image.class, visitor::visit),
                new VisitHandler<ImageRef>(ImageRef.class, visitor::visit),
                new VisitHandler<Link>(Link.class, visitor::visit),
                new VisitHandler<LinkRef>(LinkRef.class, visitor::visit),
                new VisitHandler<MailLink>(MailLink.class, visitor::visit),
                new VisitHandler<SoftLineBreak>(SoftLineBreak.class, visitor::visit),
                new VisitHandler<StrongEmphasis>(StrongEmphasis.class, visitor::visit),
                new VisitHandler<Text>(Text.class, visitor::visit),
        };
    }
}
