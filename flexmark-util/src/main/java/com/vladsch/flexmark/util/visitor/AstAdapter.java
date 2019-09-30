package com.vladsch.flexmark.util.visitor;

public interface AstAdapter<B, N extends B> {
    N getFirstChild(B node);
    N getNext(B node);
}
