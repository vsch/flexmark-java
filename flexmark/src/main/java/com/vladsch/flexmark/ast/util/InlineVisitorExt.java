package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class InlineVisitorExt {
    public static <V extends InlineVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<AutoLink>(AutoLink.class, new Visitor<AutoLink>() {
                    @Override
                    public void visit(AutoLink node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Code>(Code.class, new Visitor<Code>() {
                    @Override
                    public void visit(Code node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Emphasis>(Emphasis.class, new Visitor<Emphasis>() {
                    @Override
                    public void visit(Emphasis node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HardLineBreak>(HardLineBreak.class, new Visitor<HardLineBreak>() {
                    @Override
                    public void visit(HardLineBreak node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HtmlEntity>(HtmlEntity.class, new Visitor<HtmlEntity>() {
                    @Override
                    public void visit(HtmlEntity node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HtmlInline>(HtmlInline.class, new Visitor<HtmlInline>() {
                    @Override
                    public void visit(HtmlInline node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HtmlInlineComment>(HtmlInlineComment.class, new Visitor<HtmlInlineComment>() {
                    @Override
                    public void visit(HtmlInlineComment node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Image>(Image.class, new Visitor<Image>() {
                    @Override
                    public void visit(Image node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<ImageRef>(ImageRef.class, new Visitor<ImageRef>() {
                    @Override
                    public void visit(ImageRef node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Link>(Link.class, new Visitor<Link>() {
                    @Override
                    public void visit(Link node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<LinkRef>(LinkRef.class, new Visitor<LinkRef>() {
                    @Override
                    public void visit(LinkRef node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<MailLink>(MailLink.class, new Visitor<MailLink>() {
                    @Override
                    public void visit(MailLink node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<SoftLineBreak>(SoftLineBreak.class, new Visitor<SoftLineBreak>() {
                    @Override
                    public void visit(SoftLineBreak node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<StrongEmphasis>(StrongEmphasis.class, new Visitor<StrongEmphasis>() {
                    @Override
                    public void visit(StrongEmphasis node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Text>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
