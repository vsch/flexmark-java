package com.vladsch.flexmark.util.format;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.builder.ISequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface NodeContext<N, C extends NodeContext<N, C>> {
    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull C getSubContext();

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param options options to use for the context (only options which do not affect the context construction will be used)
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull C getSubContext(@Nullable DataHolder options);

    /**
     * Creates a child rendering context that can be used to collect rendered html text. The child context inherits
     * everything but the HtmlRenderer and doNotRenderLinksNesting from the parent.
     *
     * @param options options to use for the context (only options which do not affect the context construction will be used)
     * @param builder sequence builder to user for appended text for tracking original base offsets
     * @return a new rendering context with a given appendable for its output
     */
    @NotNull C getSubContext(@Nullable DataHolder options, @NotNull ISequenceBuilder<?, ?> builder);

    /**
     * @return the current node being rendered
     */
    @Nullable N getCurrentNode();

    /**
     * Get options for the context
     *
     * @return data holder
     */
    @NotNull DataHolder getOptions();
}
