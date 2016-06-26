package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;

public class ZzzzzzPostProcessor implements PostProcessor {
    public ZzzzzzPostProcessor(Document document) {
    }

    public Node process(Node node) {
        ZzzzzzVisitor visitor = new ZzzzzzVisitor();
        node.accept(visitor);
        return node;
    }

    private void processNode(Text node) {

    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class ZzzzzzVisitor extends AbstractVisitor {
        @Override
        public void visit(Text text) {
            if (!isVisiting(text, DoNotLinkify.class)) {
                processNode(text);
            }
        }
    }

    public static class Factory implements PostProcessorFactory {
        @Override
        public PostProcessor create(Document document) {
            return new ZzzzzzPostProcessor(document);
        }
    }
}
