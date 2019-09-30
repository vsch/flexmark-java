package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.VisitHandler;

public class BlockVisitorExt {
    public static <V extends BlockVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(BlockQuote.class, visitor::visit),
                new VisitHandler<>(BulletList.class, visitor::visit),
                new VisitHandler<>(Document.class, visitor::visit),
                new VisitHandler<>(FencedCodeBlock.class, visitor::visit),
                new VisitHandler<>(Heading.class, visitor::visit),
                new VisitHandler<>(HtmlBlock.class, visitor::visit),
                new VisitHandler<>(HtmlCommentBlock.class, visitor::visit),
                new VisitHandler<>(IndentedCodeBlock.class, visitor::visit),
                new VisitHandler<>(BulletListItem.class, visitor::visit),
                new VisitHandler<>(OrderedListItem.class, visitor::visit),
                new VisitHandler<>(OrderedList.class, visitor::visit),
                new VisitHandler<>(Paragraph.class, visitor::visit),
                new VisitHandler<>(Reference.class, visitor::visit),
                new VisitHandler<>(ThematicBreak.class, visitor::visit)
        };
    }
}
