package com.vladsch.flexmark.ext.media.tags;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class PictureLinkVisitorExt {
    public static <V extends PictureLinkVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
                new VisitHandler<>(PictureLink.class, new Visitor<PictureLink>() {
                    @Override
                    public void visit(PictureLink node) {
                        visitor.visit(node);
                    }
                })
        };
    }
}
