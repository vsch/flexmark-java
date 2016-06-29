package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.node.Node;

public interface NodeTracker {
    void added(Node node, boolean withChildren, boolean withDescendants);
    void removed(Node node, boolean withChildren, boolean withDescendants);
}
