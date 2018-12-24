package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.HtmlInnerBlock;
import com.vladsch.flexmark.ast.HtmlInnerBlockComment;
import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class HtmlInnerVisitorExt {
    public static <V extends HtmlInnerVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<HtmlInnerBlock>(HtmlInnerBlock.class, new Visitor<HtmlInnerBlock>() {
                    @Override
                    public void visit(HtmlInnerBlock node) {
                        visitor.visit(node);
                    }
                }),
                new VisitHandler<HtmlInnerBlockComment>(HtmlInnerBlockComment.class, new Visitor<HtmlInnerBlockComment>() {
                    @Override
                    public void visit(HtmlInnerBlockComment node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
