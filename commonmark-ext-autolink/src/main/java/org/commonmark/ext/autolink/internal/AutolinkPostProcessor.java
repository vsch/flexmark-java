package org.commonmark.ext.autolink.internal;

import org.commonmark.internal.util.BasedSequence;
import org.commonmark.internal.util.PrefixedSubSequence;
import org.commonmark.node.*;
import org.commonmark.parser.PostProcessor;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

public class AutolinkPostProcessor implements PostProcessor {

    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.EMAIL))
            .build();

    @Override
    public Node process(Node node) {
        AutolinkVisitor autolinkVisitor = new AutolinkVisitor();
        node.accept(autolinkVisitor);
        return node;
    }

    private void linkify(Text text) {
        BasedSequence literal = text.getChars();
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);

        Node lastNode = text;
        int last = 0;
        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
            if (link.getBeginIndex() != last) {
                lastNode = insertNode(new Text(literal.subSequence(last, link.getBeginIndex())), lastNode);
            }
            Text contentNode = new Text(linkText);
            BasedSequence destination = getDestination(link, linkText);
            AutoLink linkNode = new AutoLink(destination);
            linkNode.appendChild(contentNode);
            lastNode = insertNode(linkNode, lastNode);
            last = link.getEndIndex();
        }
        if (last != literal.length()) {
            insertNode(new Text(literal.subSequence(last, literal.length())), lastNode);
        }
        text.unlink();
    }

    private static BasedSequence getDestination(LinkSpan linkSpan, BasedSequence linkText) {
        if (linkSpan.getType() == LinkType.EMAIL) {
            return new PrefixedSubSequence("mailto:", linkText);
        } else {
            return linkText;
        }
    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class AutolinkVisitor extends AbstractVisitor {
        int inLink = 0;

        @Override
        public void visit(Link link) {
            inLink++;
            super.visit(link);
            inLink--;
        }

        @Override
        public void visit(Text text) {
            if (inLink == 0) {
                linkify(text);
            }
        }
    }
}
