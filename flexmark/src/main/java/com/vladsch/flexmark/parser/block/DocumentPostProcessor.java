package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.util.NodeTracker;

public abstract class DocumentPostProcessor implements PostProcessor {
    /**
     * @param state ast tracker used for optimizing ast processing
     * @param node  the ast to post-process
     */
    final public void process(NodeTracker state, Node node) {

    }
}
