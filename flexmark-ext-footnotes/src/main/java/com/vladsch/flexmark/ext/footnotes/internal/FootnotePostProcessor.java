package com.vladsch.flexmark.ext.footnotes.internal;

import com.vladsch.flexmark.ext.footnotes.Footnote;
import com.vladsch.flexmark.ext.footnotes.FootnoteBlock;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;

public class FootnotePostProcessor implements PostProcessor {
    private FootnoteRepository footnoteRepository = null;

    private void initializeNode(Node node) {
        Document document = node.getDocument();
        footnoteRepository = document.get(FootnoteExtension.FOOTNOTES);
    }

    public Node process(Node node) {
        initializeNode(node);

        FootnoteVisitor visitor = new FootnoteVisitor();
        node.accept(visitor);

        finalizeNode(node);
        return node;
    }

    private void finalizeNode(Node node) {
        footnoteRepository = null;
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
        BasedSequence footnoteId = nodeChars.subSequence(2, nodeChars.length() - 1).trim();
        FootnoteBlock footnoteBlock = footnoteId.length() > 0 ? footnoteRepository.get(footnoteId.toString()) : null;

        Footnote footnote = new Footnote(nodeChars.subSequence(0, 2), footnoteId, nodeChars.subSequence(nodeChars.length() - 1));
        footnote.setFootnoteBlock(footnoteBlock);

        if (footnoteBlock != null) {
            footnoteRepository.addFootnoteReference(footnoteBlock);
        } else {
            // we have to move children from the ref node to us, since the footnote is not defined and therefore it will be rendered as text
            // except the first character of the first child is part of our openingMarker
            if (node.getFirstChild() != null) {
                node.getFirstChild().setChars(node.getFirstChild().getChars().subSequence(1));
            }
            footnote.takeChildren(node);
        }

        node.insertBefore(footnote);
        node.unlink();
    }

    private class FootnoteVisitor extends AbstractVisitor {
        @Override
        public void visit(ImageRef node) {
            if (!node.isDummyReference() && node.isReferenceTextCombined() && node.getReference().startsWith("^")) {
                replaceNode(node);
            }
        }

        @Override
        public void visit(LinkRef node) {
            if (!node.isDummyReference() && node.isReferenceTextCombined() && node.getReference().startsWith("^")) {
                replaceNode(node);
            }
        }
    }
}
