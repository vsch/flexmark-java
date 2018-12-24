package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.Node;

public interface NodeTracker {
    void nodeAdded(Node node);
    void nodeAddedWithChildren(Node node);
    void nodeAddedWithDescendants(Node node);
    
    void nodeRemoved(Node node);
    void nodeRemovedWithChildren(Node node);
    void nodeRemovedWithDescendants(Node node);
}
