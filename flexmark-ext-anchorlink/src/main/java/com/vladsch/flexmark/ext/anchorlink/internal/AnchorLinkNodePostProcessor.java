package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.collection.DataHolder;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;

public class AnchorLinkNodePostProcessor extends NodePostProcessor {
    final private GitHubHeaderIdGenerator generator;
    final private AnchorLinkOptions options;

    public AnchorLinkNodePostProcessor(DataHolder options) {
        this.generator = new GitHubHeaderIdGenerator();
        this.options = new AnchorLinkOptions(options);
    }

    @Override
    public void process(NodeTracker state, Node node) {
        if (node instanceof Heading) {
            Heading heading = (Heading) node;
            if (heading.getText().isNotNull()) {
                String headerId = generator.generate(heading.getText());
                Node anchor = new AnchorLink(headerId);

                if (options.noWrap) {
                    if (heading.getFirstChild() == null) {
                        heading.appendChild(anchor);
                    } else {
                        heading.getFirstChild().insertBefore(anchor);
                    }
                } else {
                    anchor.takeChildren(heading);
                    heading.appendChild(anchor);
                }

                state.nodeAdded(anchor);
            }
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            addNodes(Heading.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new AnchorLinkNodePostProcessor(document);
        }
    }
}
