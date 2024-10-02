package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.data.DataHolder;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

/** Factory for instantiating new node renderers when rendering is done. */
public interface NodeRendererFactory extends Function<DataHolder, NodeRenderer> {
  /**
   * Create a new node renderer for the specified rendering context.
   *
   * @param options the context for rendering (normally passed on to the node renderer)
   * @return a node renderer
   */
  @Override
  @NotNull
  NodeRenderer apply(@NotNull DataHolder options);
}
