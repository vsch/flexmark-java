package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;

public interface NodeContext<N, C extends NodeContext<N, C>> {
  /**
   * Creates a child rendering context that can be used to collect rendered html text. The child
   * context inherits everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
   *
   * @return a new rendering context with a given appendable for its output
   */
  C getSubContext();

  /**
   * Creates a child rendering context that can be used to collect rendered html text. The child
   * context inherits everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
   *
   * @param options options to use for the context (only options which do not affect the context
   *     construction will be used)
   * @return a new rendering context with a given appendable for its output
   */
  C getSubContext(DataHolder options);

  /**
   * Creates a child rendering context that can be used to collect rendered html text. The child
   * context inherits everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
   *
   * @param options options to use for the context (only options which do not affect the context
   *     construction will be used)
   * @param builder sequence builder to user for appended text for tracking original base offsets
   * @return a new rendering context with a given appendable for its output
   */
  C getSubContext(DataHolder options, ISequenceBuilder<?, ?> builder);

  /**
   * @return the current node being rendered
   */
  N getCurrentNode();

  /**
   * Get options for the context
   *
   * @return data holder
   */
  DataHolder getOptions();
}
