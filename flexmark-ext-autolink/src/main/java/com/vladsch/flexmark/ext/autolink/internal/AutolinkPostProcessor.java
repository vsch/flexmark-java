package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

public class AutolinkPostProcessor extends AbstractVisitor implements PostProcessor {

    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.EMAIL))
            .build();

    public AutolinkPostProcessor(Document document) {
        
    }

    public Node process(Node Node) {
        Node.accept(this);
        return Node;
    }

    @Override
    public void visit(Text text) {
        if (!isVisiting(text, DoNotLinkify.class)) {
            processTextNode(text);
        }
    }
    
    private void processTextNode(Text text) {
        BasedSequence original = text.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);

        Node lastNode = text;
        int lastEscaped = 0;
        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
            int index = textMapper.originalOffset(link.getBeginIndex());

            if (index != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, index);
                Node node = new Text(escapedChars);
                lastNode.insertAfter(node);
                lastNode = node;
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

            lastEscaped = textMapper.originalOffset(link.getEndIndex());
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            Node node = new Text(escapedChars);
            lastNode.insertAfter(node);
        }
        text.unlink();
    }

    public static class Factory implements PostProcessorFactory {
        @Override
        public PostProcessor create(Document document) {
            return new AutolinkPostProcessor(document);
        }
    }
}
