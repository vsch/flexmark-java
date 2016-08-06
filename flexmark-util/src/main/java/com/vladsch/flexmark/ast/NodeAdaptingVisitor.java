package com.vladsch.flexmark.ast;

/**
 * Intended to be extended with specific visit function(s) and parameters. see {@link Visitor}
 * @param <N> subclass of {@link Node} 
 */
public interface NodeAdaptingVisitor<N extends Node> {
    
}
