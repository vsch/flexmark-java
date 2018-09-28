package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutolinkNodePostProcessor extends NodePostProcessor {

    private final Pattern ignoredLinks;
    private final boolean intellijDummyIdentifier;

    private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
            .build();

    public AutolinkNodePostProcessor(Document document) {
        String ignoreLinks = AutolinkExtension.IGNORE_LINKS.getFrom(document);
        ignoredLinks = ignoreLinks.isEmpty() ? null : Pattern.compile(ignoreLinks);
        intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.getFrom(document);
    }

    public boolean isIgnoredLinkPrefix(final CharSequence url) {
        if (ignoredLinks != null) {
            Matcher matcher = ignoredLinks.matcher(url);
            return matcher.matches();
        }
        return false;
    }

    @Override
    public void process(NodeTracker state, Node node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);
        if (intellijDummyIdentifier) {
            literal = Escaping.removeAll(literal, "\u001f", textMapper);
        }

        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

        for (LinkSpan link : links) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex()).trimEnd();
            if (isIgnoredLinkPrefix(linkText)) continue;

            int startOffset = textMapper.originalOffset(link.getBeginIndex());

            if (wrapInTextBase) {
                wrapInTextBase = false;
                textBase = new TextBase(original);
                node.insertBefore(textBase);
                state.nodeAdded(textBase);
            }

            if (startOffset != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                Node node1 = new Text(escapedChars);
                textBase.appendChild(node1);
                state.nodeAdded(node1);
            }

            final BasedSequence linkChars = linkText.baseSubSequence(linkText.getStartOffset(), linkText.getEndOffset());
            Text contentNode = new Text(linkChars);
            LinkNode linkNode;

            if (link.getType() == LinkType.EMAIL) {
                linkNode = new MailLink();
                ((MailLink) linkNode).setText(linkChars);
            } else {
                linkNode = new AutoLink();
                ((AutoLink) linkNode).setText(linkChars);
                ((AutoLink) linkNode).setUrlChars(linkChars);
            }

            linkNode.setCharsFromContent();
            linkNode.appendChild(contentNode);
            textBase.appendChild(linkNode);
            state.nodeAddedWithChildren(linkNode);

            lastEscaped = textMapper.originalOffset(link.getBeginIndex() + linkText.length());
        }

        if (lastEscaped > 0) {
            if (lastEscaped != original.length()) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
                Node node1 = new Text(escapedChars);
                textBase.appendChild(node1);
                state.nodeAdded(node1);
            }

            node.unlink();
            state.nodeRemoved(node);
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            addNodeWithExclusions(Text.class, DoNotDecorate.class, DoNotLinkDecorate.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new AutolinkNodePostProcessor(document);
        }
    }
}
