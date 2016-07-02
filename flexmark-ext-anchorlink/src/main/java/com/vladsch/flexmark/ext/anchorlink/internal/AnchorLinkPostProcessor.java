package com.vladsch.flexmark.ext.anchorlink.internal;

import com.vladsch.flexmark.ext.anchorlink.AnchorLink;
import com.vladsch.flexmark.html.renderer.GitHubHeaderIdGenerator;
import com.vladsch.flexmark.node.DoNotLinkify;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.node.Heading;
import com.vladsch.flexmark.node.Node;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;

public class AnchorLinkPostProcessor extends DocumentPostProcessor {
    final private GitHubHeaderIdGenerator generator;
    final private AnchorLinkOptions options;

    public AnchorLinkPostProcessor(Document document) {
        this.generator = new GitHubHeaderIdGenerator();
        this.options = new AnchorLinkOptions(document);
    }

    @Override
    public Document processDocument(Document document) {
        document.accept(this);
        return document;
    }

    @Override
    public void visit(Heading node) {
        if (!isVisiting(node, DoNotLinkify.class)) {
            processNode(node);
        }
    }

    private void processNode(Heading node) {
        if (node.getText().isNotNull()) {
            Node anchor = new AnchorLink();

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

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new AnchorLinkPostProcessor(document);
        }
    }
}
