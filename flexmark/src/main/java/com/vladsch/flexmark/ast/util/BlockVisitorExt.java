package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.VisitHandler;

public class BlockVisitorExt {
    public static <V extends BlockVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<BlockQuote>(BlockQuote.class, visitor::visit),
                new VisitHandler<BulletList>(BulletList.class, visitor::visit),
                new VisitHandler<Document>(Document.class, visitor::visit),
                new VisitHandler<FencedCodeBlock>(FencedCodeBlock.class, visitor::visit),
                new VisitHandler<Heading>(Heading.class, visitor::visit),
                new VisitHandler<HtmlBlock>(HtmlBlock.class, visitor::visit),
                new VisitHandler<HtmlCommentBlock>(HtmlCommentBlock.class, visitor::visit),
                new VisitHandler<IndentedCodeBlock>(IndentedCodeBlock.class, visitor::visit),
                new VisitHandler<BulletListItem>(BulletListItem.class, visitor::visit),
                new VisitHandler<OrderedListItem>(OrderedListItem.class, visitor::visit),
                new VisitHandler<OrderedList>(OrderedList.class, visitor::visit),
                new VisitHandler<Paragraph>(Paragraph.class, visitor::visit),
                new VisitHandler<Reference>(Reference.class, visitor::visit),
                new VisitHandler<ThematicBreak>(ThematicBreak.class, visitor::visit)
        };
    }
}
