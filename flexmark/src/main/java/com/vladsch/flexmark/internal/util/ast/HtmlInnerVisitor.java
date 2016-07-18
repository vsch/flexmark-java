package com.vladsch.flexmark.internal.util.ast;

import com.vladsch.flexmark.node.HtmlInnerBlock;
import com.vladsch.flexmark.node.HtmlInnerBlockComment;

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
