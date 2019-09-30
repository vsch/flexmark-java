package com.vladsch.flexmark.ext.emoji;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class EmojiVisitorExt {
    public static <V extends EmojiVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(Emoji.class, visitor::visit),
        };
    }
}
