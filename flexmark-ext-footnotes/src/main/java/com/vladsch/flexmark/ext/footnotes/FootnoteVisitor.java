package com.vladsch.flexmark.ext.footnotes;

public interface FootnoteVisitor {
    void visit(final FootnoteBlock node);
    void visit(final Footnote node);
}
