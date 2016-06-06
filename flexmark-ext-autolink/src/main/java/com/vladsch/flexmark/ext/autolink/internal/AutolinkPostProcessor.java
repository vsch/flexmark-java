package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

public class AutolinkPostProcessor implements PostProcessor {

    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.EMAIL))
            .build();

    public Node process(Node node) {
        AutolinkVisitor autolinkVisitor = new AutolinkVisitor();
        node.accept(autolinkVisitor);
        return node;
    }

    private void linkify(Text text) {
        BasedSequence original = text.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper();
        BasedSequence literal = Escaping.unescapeSequence(original, textMapper);
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);

        Node lastNode = text;
        int lastEscaped = 0;
        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
            int index = textMapper.originalOffset(link.getBeginIndex());

            if (index != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, index);
                lastNode = insertNode(new Text(escapedChars), lastNode);
            }

            Text contentNode = new Text(linkText);
            LinkNode linkNode;

            if (link.getType() == LinkType.EMAIL) {
                linkNode = new MailLink();
                ((MailLink) linkNode).setText(linkText);
            } else {
                linkNode = new AutoLink();
                ((AutoLink) linkNode).setContent(linkText);
            }

            linkNode.setCharsFromContent();
            linkNode.appendChild(contentNode);
            lastNode = insertNode(linkNode, lastNode);

            lastEscaped = textMapper.originalOffset(link.getEndIndex());
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            insertNode(new Text(escapedChars), lastNode);
        }
        text.unlink();
    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class AutolinkVisitor extends AbstractVisitor {
        @Override
        public void visit(Text text) {
            if (!isVisiting(text, LinkNode.class)) {
                linkify(text);
            }
        }
    }
}
