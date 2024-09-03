package com.vladsch.flexmark.html.renderer;

import java.util.Set;
import org.jetbrains.annotations.Nullable;

/** A renderer for a set of node types. */
public interface NodeRenderer {
  /**
   * @return the mapping of nodes this renderer handles to rendering function
   */
  @Nullable
  Set<NodeRenderingHandler<?>> getNodeRenderingHandlers();
}
