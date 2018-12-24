package com.vladsch.flexmark.ext.wikilink;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class WikiImageVisitorExt {
    public static <V extends WikiImageVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<WikiImage>(WikiImage.class, new Visitor<WikiImage>() {
                    @Override
                    public void visit(WikiImage node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
