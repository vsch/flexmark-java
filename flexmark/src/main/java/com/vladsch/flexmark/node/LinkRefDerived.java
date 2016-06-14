package com.vladsch.flexmark.node;

/**
 * Nodes which are textually derived from LinkRef
 */
public interface LinkRefDerived {
    /**
     * @return true if this node can be reverted to its text equivalent without loss of HTML rendering results
     */
    boolean isTentative();
}
