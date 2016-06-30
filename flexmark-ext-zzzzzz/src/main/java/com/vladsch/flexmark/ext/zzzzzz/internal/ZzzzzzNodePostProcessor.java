package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.internal.util.NodeTracker;
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
