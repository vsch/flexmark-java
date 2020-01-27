package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import org.jetbrains.annotations.NotNull;

public abstract class DocumentPostProcessor implements PostProcessor {
    /**
     * @param state node tracker used for optimizing node processing
     * @param node  the node to post-process
     */
    final public void process(@NotNull NodeTracker state, @NotNull Node node) {

    }
}
