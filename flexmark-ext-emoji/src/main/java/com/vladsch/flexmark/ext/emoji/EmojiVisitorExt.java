package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class EmojiVisitorExt {
    public static <V extends EmojiVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Emoji.class, new Visitor<Emoji>() {
                    @Override
                    public void visit(Emoji node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
