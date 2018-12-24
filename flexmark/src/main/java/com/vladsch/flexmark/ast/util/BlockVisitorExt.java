package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class BlockVisitorExt {
    public static <V extends BlockVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<BlockQuote>(BlockQuote.class, new Visitor<BlockQuote>() {
                    @Override
                    public void visit(BlockQuote node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<BulletList>(BulletList.class, new Visitor<BulletList>() {
                    @Override
                    public void visit(BulletList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Document>(Document.class, new Visitor<Document>() {
                    @Override
                    public void visit(Document node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<FencedCodeBlock>(FencedCodeBlock.class, new Visitor<FencedCodeBlock>() {
                    @Override
                    public void visit(FencedCodeBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Heading>(Heading.class, new Visitor<Heading>() {
                    @Override
                    public void visit(Heading node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HtmlBlock>(HtmlBlock.class, new Visitor<HtmlBlock>() {
                    @Override
                    public void visit(HtmlBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HtmlCommentBlock>(HtmlCommentBlock.class, new Visitor<HtmlCommentBlock>() {
                    @Override
                    public void visit(HtmlCommentBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<IndentedCodeBlock>(IndentedCodeBlock.class, new Visitor<IndentedCodeBlock>() {
                    @Override
                    public void visit(IndentedCodeBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<BulletListItem>(BulletListItem.class, new Visitor<BulletListItem>() {
                    @Override
                    public void visit(BulletListItem node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<OrderedListItem>(OrderedListItem.class, new Visitor<OrderedListItem>() {
                    @Override
                    public void visit(OrderedListItem node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<OrderedList>(OrderedList.class, new Visitor<OrderedList>() {
                    @Override
                    public void visit(OrderedList node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Paragraph>(Paragraph.class, new Visitor<Paragraph>() {
                    @Override
                    public void visit(Paragraph node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<Reference>(Reference.class, new Visitor<Reference>() {
                    @Override
                    public void visit(Reference node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<ThematicBreak>(ThematicBreak.class, new Visitor<ThematicBreak>() {
                    @Override
                    public void visit(ThematicBreak node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
