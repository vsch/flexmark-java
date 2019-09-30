package com.vladsch.flexmark.util.ast;

/**
 * Intended to be extended with specific visit function(s) and parameters. see {@link Visitor}
 *
 * @param <N> subclass of {@link Node}
 * @deprecated no longer needed for new implementation see: {@link NodeAdaptedVisitor}
 */
@Deprecated
public interface NodeAdaptingVisitor<N extends Node> {

}
