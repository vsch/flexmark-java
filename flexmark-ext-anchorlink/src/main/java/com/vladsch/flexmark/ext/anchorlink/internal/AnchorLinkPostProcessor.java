package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;

public class AnchorLinkPostProcessor extends AbstractVisitor implements PostProcessor {
    final private GitHubHeaderIdGenerator generator;
    final private AnchorLinkOptions options;

    public AnchorLinkPostProcessor(Document document) {
        this.generator = new GitHubHeaderIdGenerator();
        this.options = new AnchorLinkOptions(document);
    }

    public Node process(Node Node) {
        Node.accept(this);
        return Node;
    }

    @Override
    public void visit(Heading node) {
        if (!isVisiting(node, DoNotLinkify.class)) {
            processNode(node);
        }
    }

    private void processNode(Heading node) {
        if (node.getText().isNotNull()) {
            String headerId = generator.generate(node.getText());
            Node anchor = new AnchorLink(headerId);

            if (options.noWrap) {
                if (node.getFirstChild() == null) {
                    node.appendChild(anchor);
                } else {
                    node.getFirstChild().insertBefore(anchor);
                }
            } else {
                anchor.takeChildren(node);
                node.appendChild(anchor);
            }
        }
    }

    public static class Factory implements PostProcessorFactory {
        @Override
        public PostProcessor create(Document document) {
            return new AnchorLinkPostProcessor(document);
        }
    }
}
