package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.node.AbstractVisitor;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;

public class AnchorLinkPostProcessor implements PostProcessor {
    final private GitHubHeaderIdGenerator generator;
    final private AnchorLinkOptions options;

    public AnchorLinkPostProcessor(Document document) {
        this.generator = new GitHubHeaderIdGenerator();
        this.options = new AnchorLinkOptions(document);
    }

    public Node process(Node node) {
        AnchorLinksVisitor visitor = new AnchorLinksVisitor();
        node.accept(visitor);
        return node;
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

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class AnchorLinksVisitor extends AbstractVisitor {
        @Override
        public void visit(Heading text) {
            processNode(text);
        }
    }

    public static class Factory implements PostProcessorFactory {
        @Override
        public PostProcessor create(Document document) {
            return new AnchorLinkPostProcessor(document);
        }
    }
}
