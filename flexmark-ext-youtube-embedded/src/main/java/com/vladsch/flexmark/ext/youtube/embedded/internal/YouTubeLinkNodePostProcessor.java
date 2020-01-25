package com.vladsch.flexmark.ext.youtube.embedded.internal;

import com.vladsch.flexmark.ast.Link;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ext.youtube.embedded.YouTubeLink;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import org.jetbrains.annotations.NotNull;

public class YouTubeLinkNodePostProcessor extends NodePostProcessor {

    public YouTubeLinkNodePostProcessor(DataHolder options) {
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
        if (node instanceof Link) {
            Node previous = node.getPrevious();

            if (previous instanceof Text) {
                BasedSequence chars = previous.getChars();
                if (chars.endsWith("@") && chars.isContinuedBy(node.getChars())) {
                    int prevBackslash = chars.subSequence(0, chars.length() - 1).countTrailing(CharPredicate.BACKSLASH);
                    if ((prevBackslash & 1) == 0) {
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
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory(DataHolder options) {
            super(false);

            addNodes(Link.class);
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new YouTubeLinkNodePostProcessor(document);
        }
    }
}
