package com.vladsch.flexmark.ext.enumerated.reference;

public interface EnumeratedReferenceVisitor {
    void visit(EnumeratedReferenceText node);
    void visit(EnumeratedReferenceLink node);
    void visit(EnumeratedReferenceBlock node);
}
