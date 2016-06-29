package com.vladsch.flexmark.internal.util;

import com.vladsch.flexmark.node.Block;

public interface BlockTracker {
    void blockAdded(Block node);
    void blockAddedWithChildren(Block node);
    void blockAddedWithDescendants(Block node);
    
    void blockRemoved(Block node);
    void blockRemovedWithChildren(Block node);
    void blockRemovedWithDescendants(Block node);
}
