package com.vladsch.flexmark.ext.jekyll.tag;

public interface JekyllTagVisitor {
    void visit(JekyllTag node);
    void visit(JekyllTagBlock node);
}
