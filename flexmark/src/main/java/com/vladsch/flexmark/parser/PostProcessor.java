package com.vladsch.flexmark.parser;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.NodeTracker;

public interface PostProcessor {
    /**
     * @param document the ast to post-process
     * @return the result of post-processing, may be a modified {@code document} argument
     */
    Document processDocument(Document document);
    
    /**
     * @param state ast tracker used for optimizing ast processing
     * @param node the ast to post-process
     */
    void process(NodeTracker state, Node node);
}
