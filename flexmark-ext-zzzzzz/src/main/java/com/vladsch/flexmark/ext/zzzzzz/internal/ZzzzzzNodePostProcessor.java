package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;

public class ZzzzzzNodePostProcessor extends NodePostProcessor {
    public ZzzzzzNodePostProcessor(Document document) {
    }

    @Override
    public void process(NodeTracker state, Node node) {

    }

    private void processText(NodeTracker state, Text node) {
        BasedSequence original = node.getChars();
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = null;

        while (wrapInTextBase) {
            if (wrapInTextBase) {
                wrapInTextBase = false;
                textBase = new TextBase(original);
                node.insertBefore(textBase);
                textBase.appendChild(node);
                state.nodeAdded(textBase);
            }
        }

    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            
            addNodeWithExclusions(Text.class, DoNotLinkify.class, Heading.class);
            addNodes(HtmlBlock.class, HtmlCommentBlock.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new ZzzzzzNodePostProcessor(document);
        }
    }
}
