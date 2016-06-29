package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.NodeTracker;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ZzzzzzNodePostProcessor extends NodePostProcessor {
    public ZzzzzzNodePostProcessor(Document document) {
    }

    @Override
    public void process(NodeTracker state, Node node) {
        
    }

    private void processText(NodeTracker state, Text node) {
    }

    public static class Factory extends NodePostProcessorFactory {
        @Override
        public Set<Class<? extends Node>> getNodeTypes() {
            return new HashSet<Class<? extends Node>>(Arrays.asList(
                    HtmlBlock.class,
                    HtmlCommentBlock.class
            ));
        }

        @Override
        public Set<Class<? extends Node>> getExcludeDescendantsOfTypes() {
            return null;
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new ZzzzzzNodePostProcessor(document);
        }
    }
}
