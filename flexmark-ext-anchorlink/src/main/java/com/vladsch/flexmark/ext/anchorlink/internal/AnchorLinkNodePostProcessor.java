package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ast.BlockQuote;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeTracker;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;

import static com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension.ANCHORLINKS_NO_BLOCK_QUOTE;

public class AnchorLinkNodePostProcessor extends NodePostProcessor {
    final private AnchorLinkOptions options;

    public AnchorLinkNodePostProcessor(DataHolder options) {
        this.options = new AnchorLinkOptions(options);
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
        if (node instanceof Heading) {
            Heading heading = (Heading) node;
            if (heading.getText().isNotNull()) {
                Node anchor = new AnchorLink();

                if (!options.wrapText) {
                    if (heading.getFirstChild() == null) {
                        anchor.setChars(heading.getText().subSequence(0, 0));
                        heading.appendChild(anchor);
                    } else {
                        anchor.setChars(heading.getFirstChild().getChars().subSequence(0, 0));
                        heading.getFirstChild().insertBefore(anchor);
                    }
                } else {
                    anchor.takeChildren(heading);
                    heading.appendChild(anchor);
                }

                anchor.setCharsFromContent();
                state.nodeAdded(anchor);
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory(DataHolder options) {
            super(false);

            if (ANCHORLINKS_NO_BLOCK_QUOTE.get(options)) {
                addNodeWithExclusions(Heading.class, BlockQuote.class);
            } else {
                addNodes(Heading.class);
            }
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new AnchorLinkNodePostProcessor(document);
        }
    }
}
