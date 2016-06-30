package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.NodeTracker;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

public class AutolinkNodePostProcessor extends NodePostProcessor {

    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.EMAIL))
            .build();

    public AutolinkNodePostProcessor(Document document) {

    }

    @Override
    public void process(NodeTracker state, Node node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);

        Node lastNode = node;
        int lastEscaped = 0;
        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
            int index = textMapper.originalOffset(link.getBeginIndex());

            if (index != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, index);
                Node node1 = new Text(escapedChars);
                lastNode.insertAfter(node1);
                lastNode = node1;
                state.nodeAdded(lastNode);
            }

            Text contentNode = new Text(linkText);
            LinkNode linkNode;

            if (link.getType() == LinkType.EMAIL) {
                linkNode = new MailLink();
                ((MailLink) linkNode).setText(linkText);
            } else {
                linkNode = new AutoLink();
                ((AutoLink) linkNode).setText(linkText);
            }

            linkNode.setCharsFromContent();
            linkNode.appendChild(contentNode);
            lastNode.insertAfter(linkNode);
            lastNode = linkNode;
            state.nodeAddedWithChildren(lastNode);

            lastEscaped = textMapper.originalOffset(link.getEndIndex());
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            Node node1 = new Text(escapedChars);
            lastNode.insertAfter(node1);
            lastNode = node1;
            state.nodeAdded(lastNode);
        }
        node.unlink();
        state.nodeRemoved(node);
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            addNodeWithExclusions(Text.class, DoNotLinkify.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new AutolinkNodePostProcessor(document);
        }
    }
}
