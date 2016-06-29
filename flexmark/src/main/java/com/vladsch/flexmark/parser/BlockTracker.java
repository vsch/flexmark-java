package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.node.Block;

public interface BlockTracker {
    void added(Block node, boolean withChildren, boolean withDescendants);
    void removed(Block node, boolean withChildren, boolean withDescendants);
}
