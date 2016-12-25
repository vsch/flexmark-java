package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;

public class AutolinkPostProcessor extends DocumentPostProcessor {
    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.EMAIL))
            .build();

    private final NodeVisitor myVisitor;

    public AutolinkPostProcessor(Document document) {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text text) {
                        AutolinkPostProcessor.this.visit(text);
                    }
                })
        );
    }

    public Document processDocument(Document document) {
        myVisitor.visit(document);
        return document;
    }

    private void visit(Text text) {
        if (!text.isOrDescendantOfType(DoNotDecorate.class)) {
            processTextNode(text);
        }
    }

    private void processTextNode(Text node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);
        Node lastNode = node;
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = null;

        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
            int index = textMapper.originalOffset(link.getBeginIndex());

            if (wrapInTextBase) {
                wrapInTextBase = false;
                textBase = new TextBase(original);
                node.insertBefore(textBase);
                textBase.appendChild(node);
            }

            if (index != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, index);
                Node node1 = new Text(escapedChars);
                lastNode.insertAfter(node1);
                lastNode = node1;
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
            Node node1 = new Text(escapedChars);
            lastNode.insertAfter(node1);
        }
        node.unlink();
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public DocumentPostProcessor create(Document document) {
            return new AutolinkPostProcessor(document);
        }
    }
}
