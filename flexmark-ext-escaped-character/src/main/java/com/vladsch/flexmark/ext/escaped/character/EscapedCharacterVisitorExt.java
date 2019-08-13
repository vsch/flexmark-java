package com.vladsch.flexmark.ext.escaped.character;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class EscapedCharacterVisitorExt {
    public static <V extends EscapedCharacterVisitor> VisitHandler<?>[] VISIT_HANDLERS(V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<EscapedCharacter>(EscapedCharacter.class, new Visitor<EscapedCharacter>() {
                    @Override
                    public void visit(EscapedCharacter node) {
                        visitor.visit(node);
                    }
                }),
        };
    }
}
