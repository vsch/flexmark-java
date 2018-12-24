package com.vladsch.flexmark.util;

import com.vladsch.flexmark.util.ast.Block;

public interface BlockTracker {
    void blockAdded(Block node);
    void blockAddedWithChildren(Block node);
    void blockAddedWithDescendants(Block node);
    
    void blockRemoved(Block node);
    void blockRemovedWithChildren(Block node);
    void blockRemovedWithDescendants(Block node);
}
