package com.vladsch.flexmark.ext.enumerated.reference;

import com.vladsch.flexmark.util.ast.VisitHandler;
import com.vladsch.flexmark.util.ast.Visitor;

public class EnumeratedReferenceVisitorExt {
    public static <V extends EnumeratedReferenceVisitor> VisitHandler<?>[] VISIT_HANDLERS(final V visitor) {
        return new VisitHandler<?>[] {
// @formatter:off
                new VisitHandler<EnumeratedReferenceText>(EnumeratedReferenceText.class, new Visitor<EnumeratedReferenceText>() { @Override public void visit(EnumeratedReferenceText node) { visitor.visit(node); } }),
                new VisitHandler<EnumeratedReferenceLink>(EnumeratedReferenceLink.class, new Visitor<EnumeratedReferenceLink>() { @Override public void visit(EnumeratedReferenceLink node) { visitor.visit(node); } }),
                new VisitHandler<EnumeratedReferenceBlock>(EnumeratedReferenceBlock.class, new Visitor<EnumeratedReferenceBlock>() { @Override public void visit(EnumeratedReferenceBlock node) { visitor.visit(node); } }),
 // @formatter:on
        };
    }
}
