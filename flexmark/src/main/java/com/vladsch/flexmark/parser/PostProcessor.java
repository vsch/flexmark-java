package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Node;

public interface PostProcessor {
    /**
     * @param document the node to post-process
     * @return the result of post-processing, may be a modified {@code document} argument
     */
    Document processDocument(Document document);
    
    /**
     * @param state node tracker used for optimizing node processing
     * @param node the node to post-process
     */
    void process(NodeTracker state, Node node);
}
