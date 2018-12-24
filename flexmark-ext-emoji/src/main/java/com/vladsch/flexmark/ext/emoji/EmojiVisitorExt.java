package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class EmojiVisitorExt {
    public static <V extends EmojiVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<Emoji>(Emoji.class, new Visitor<Emoji>() {
                    @Override
                    public void visit(Emoji node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
