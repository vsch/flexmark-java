package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.util.ast.VisitHandler;

public class EscapedCharacterVisitorExt {
    public static <V extends EscapedCharacterVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(EscapedCharacter.class, visitor::visit),
        };
    }
}
