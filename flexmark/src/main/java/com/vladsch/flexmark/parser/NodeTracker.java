package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.node.Node;

public interface NodeTracker {
    void nodeAdded(Node node);
    void nodeAddedWithChildren(Node node);
    void nodeAddedWithDescendants(Node node);
    
    void nodeRemoved(Node node);
    void nodeRemovedWithChildren(Node node);
    void nodeRemovedWithDescendants(Node node);
}
