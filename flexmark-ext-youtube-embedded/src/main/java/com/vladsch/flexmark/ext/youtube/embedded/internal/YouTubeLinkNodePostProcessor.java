package com.vladsch.flexmark.ext.youtube.embedded.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLink;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

public class YouTubeLinkNodePostProcessor extends NodePostProcessor {
    public YouTubeLinkNodePostProcessor(DataHolder options) {
    }

    @Override
    public void process(NodeTracker state, Node node) {
        if (node instanceof Link) {
            Node previous = node.getPrevious();

            if (previous instanceof Text) {
                final BasedSequence chars = previous.getChars();
                if (chars.endsWith("@") && chars.isContinuedBy(node.getChars())) {
                    // trim previous chars to remove '@'
                    previous.setChars(chars.subSequence(0, chars.length() - 1));

                    YouTubeLink youTubeLink = new YouTubeLink((Link) node);
                    youTubeLink.takeChildren(node);
                    node.unlink();
                    previous.insertAfter(youTubeLink);
                    state.nodeRemoved(node);
                    state.nodeAddedWithChildren(youTubeLink);
                }
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory(DataHolder options) {
            super(false);

            addNodes(Link.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new YouTubeLinkNodePostProcessor(document);
        }
    }
}
