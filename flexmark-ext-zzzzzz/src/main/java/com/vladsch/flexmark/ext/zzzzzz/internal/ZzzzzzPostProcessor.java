package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;

public class ZzzzzzPostProcessor extends AbstractVisitor implements PostProcessor {
    public ZzzzzzPostProcessor(Document document) {
    }

    @Override
    public Node process(Node Node) {
        Node.accept(this);
        return Node;
    }

    @Override
    public void visit(Text text) {
        if (!isVisiting(text, DoNotLinkify.class)) {
            process(text);
        }
    }

    private Node processText(Text node) {
        return node;
    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    public static class Factory implements PostProcessorFactory {
        @Override
        public PostProcessor create(Document document) {
            return new ZzzzzzPostProcessor(document);
        }
    }
}
