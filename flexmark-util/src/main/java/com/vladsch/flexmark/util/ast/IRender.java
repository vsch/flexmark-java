package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Render interface for rendering implementation for RenderingTestCase
 */
public interface IRender {
    public void render(Node node, @NotNull Appendable output);

    /**
     * Render the tree of nodes to HTML.
     *
     * @param document the root node
     * @return the rendered HTML
     */
    @NotNull
    default String render(Node document) {
        StringBuilder sb = new StringBuilder();
        render(document, sb);
        return sb.toString();
    }

    @NotNull IRender withOptions(@Nullable DataHolder options);

    /**
     * Get Options for parsing
     *
     * @return DataHolder for options
     */
    @Nullable DataHolder getOptions();
}
