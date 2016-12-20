package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.ast.VisitHandler;
import com.vladsch.flexmark.ast.Visitor;

public class EscapedCharacterVisitorExt {
    public static <V extends EscapedCharacterVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(EscapedCharacter.class, new Visitor<EscapedCharacter>() {
                    @Override
                    public void visit(EscapedCharacter node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
