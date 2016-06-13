package com.vladsch.flexmark.ext.wikilink.internal;

import com.vladsch.flexmark.ext.wikilink.WikiLink;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;

public class WikiLinkPostProcessor implements PostProcessor {
    boolean isLinkFirst = false;

    private void initializeNode(Node node) {
        Document document = node.getDocument();
        isLinkFirst = document.get(WikiLinkExtension.LINK_FIRST_SYNTAX);
    }

    public Node process(Node node) {
        initializeNode(node);

        WikiLinkVisitor visitor = new WikiLinkVisitor();
        node.accept(visitor);

        finalizeNode(node);
        return node;
    }

    private void finalizeNode(Node node) {
        isLinkFirst = false;
    }

    private void replaceNode(RefNode node) {
        BasedSequence nodeChars = node.getChars();

        if (node instanceof ImageRef) {
            // put the ! as part of text
            Node prev = node.getPrevious();
            if (prev instanceof Text && prev.getEndOffset() == node.getStartOffset()) {
                prev.getChars().baseSubSequence(prev.getStartOffset(), prev.getEndOffset() + 1);
            } else {
                // new text node
                Text text = new Text(node.getChars().subSequence(0, 1));
                node.insertBefore(text);
            }

            nodeChars = nodeChars.subSequence(1);
        }

        WikiLink wikilink = new WikiLink(nodeChars, isLinkFirst);
        node.insertBefore(wikilink);
        node.unlink();
    }

    private class WikiLinkVisitor extends AbstractVisitor {
        @Override
        public void visit(ImageRef node) {
            if (!node.isDummyReference() && node.isReferenceTextCombined() && node.getReferenceOpeningMarker().isContinuedBy(node.getReference()) &&
                    node.getReference().startsWith("[") && node.getReference().endsWith("]")) {
                replaceNode(node);
            }
        }

        @Override
        public void visit(LinkRef node) {
            if (!node.isDummyReference() && node.isReferenceTextCombined() && node.getReferenceOpeningMarker().isContinuedBy(node.getReference()) &&
                    node.getReferenceClosingMarker().isContinuationOf(node.getReference()) &&
                    node.getReference().startsWith("[") && node.getReference().endsWith("]")) {
                replaceNode(node);
            }
        }
    }
}
