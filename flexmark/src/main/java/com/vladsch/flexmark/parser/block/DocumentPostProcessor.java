package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.node.AbstractVisitor;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.NodeTracker;
import com.vladsch.flexmark.parser.PostProcessor;

public abstract class DocumentPostProcessor extends AbstractVisitor implements PostProcessor {
    /**
     * @param state node tracker used for optimizing node processing
     * @param node  the node to post-process
     */
    final public void process(NodeTracker state, Node node) {

    }
}
