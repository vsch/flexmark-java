package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import org.jetbrains.annotations.NotNull;

public interface PostProcessor {
    /**
     * @param document the node to post-process
     * @return the result of post-processing, may be a modified {@code document} argument
     */
    @NotNull Document processDocument(@NotNull Document document);

    /**
     * @param state node tracker used for optimizing node processing
     * @param node  the node to post-process
     */
    void process(@NotNull NodeTracker state, @NotNull Node node);
}
