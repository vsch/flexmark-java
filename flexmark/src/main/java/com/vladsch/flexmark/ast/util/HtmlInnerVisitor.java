package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.HtmlInnerBlock;
import com.vladsch.flexmark.ast.HtmlInnerBlockComment;
import com.vladsch.flexmark.ast.VisitHandler;

public interface HtmlInnerVisitor {
    static <V extends HtmlInnerVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(HtmlInnerBlock.class, visitor::visit),
                new VisitHandler<>(HtmlInnerBlockComment.class, visitor::visit),
        };
    }

    void visit(HtmlInnerBlock node);
    void visit(HtmlInnerBlockComment node);
}
