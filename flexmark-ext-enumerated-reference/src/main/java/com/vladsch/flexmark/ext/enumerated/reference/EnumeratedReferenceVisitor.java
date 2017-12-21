package com.vladsch.flexmark.ext.enumerated.reference;

public interface EnumeratedReferenceVisitor {
    void visit(final EnumeratedReferenceText node);
    void visit(final EnumeratedReferenceLink node);
    void visit(final EnumeratedReferenceBlock node);
}
