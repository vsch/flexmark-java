package com.vladsch.flexmark.ast;

/**
 * Nodes which are textually derived from LinkRef
 */
public interface LinkRefDerived {
    /**
     * @return true if this node will be rendered as text because it depends on a reference which is not defined.
     */
    boolean isTentative();
}
