package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.util.ast.Document;
import org.jetbrains.annotations.NotNull;

public abstract class NodePostProcessor implements PostProcessor {
    /**
     * @param document the node to post-process
     * @return the result of post-processing, may be a modified {@code document} argument
     */
    @NotNull
    @Override
    final public Document processDocument(@NotNull Document document) {
        return document;
    }
}
