package com.vladsch.flexmark.ext.zzzzzz.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class ZzzzzzNodePostProcessor extends NodePostProcessor {
    public ZzzzzzNodePostProcessor(Document document) {
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {

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

            addNodeWithExclusions(Text.class, DoNotDecorate.class/*, Heading.class*/);
            //addNodes(HtmlBlock.class, HtmlCommentBlock.class);
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new ZzzzzzNodePostProcessor(document);
        }
    }
}
