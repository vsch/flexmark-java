package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.ast.DoNotDecorate;
import com.vladsch.flexmark.util.ast.DoNotLinkDecorate;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutolinkNodePostProcessor extends NodePostProcessor {
    final static private Pattern URI_SUFFIX = Pattern.compile("\\b([a-z][a-z0-9+.-]*://\\s*)$");

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

    private static class DummyLinkSpan implements LinkSpan {
        private final LinkType linkType;
        private final int beginIndex;
        private final int endIndex;

        public DummyLinkSpan(final LinkType linkType, final int beginIndex, final int endIndex) {
            this.linkType = linkType;
            this.beginIndex = beginIndex;
            this.endIndex = endIndex;
        }

        @Override
        public LinkType getType() {
            return linkType;
        }

        @Override
        public int getBeginIndex() {
            return beginIndex;
        }

        @Override
        public int getEndIndex() {
            return endIndex;
        }
    }

    @Override
    public void process(NodeTracker state, Node node) {
        // TODO: figure our why optimization does not work after AutoLink inserted by inline parser
        if (node.getAncestorOfType(DoNotDecorate.class, DoNotLinkDecorate.class) != null) return;

        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        if (intellijDummyIdentifier) {
            literal = Escaping.removeAll(literal, "\u001f", textMapper);
        }

        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);
        ArrayList<LinkSpan> linksList = null;

        final Matcher matcher = URI_SUFFIX.matcher(literal);
        if (matcher.find()) {
            linksList = new ArrayList<LinkSpan>();

            linksList.add(new DummyLinkSpan(LinkType.URL, matcher.start(1), matcher.end(1)));
        }

        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase || !(node.getParent() instanceof TextBase) ? null : (TextBase) node.getParent();
        boolean processedNode = false;

        int iMax = linksList == null ? 0 : linksList.size();
        int i = 0;

        for (Iterator<LinkSpan> iterator = links.iterator(); ; ) {
            LinkSpan link;

            if (iterator.hasNext()) {
                link = iterator.next();
            } else if (i < iMax) {
                link = linksList.get(i++);
            } else {
                break;
            }

            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex()).trimEnd();
            if (isIgnoredLinkPrefix(linkText)) continue;

            int startOffset = textMapper.originalOffset(link.getBeginIndex());
            processedNode = true;

            if (wrapInTextBase) {
                wrapInTextBase = false;
                textBase = new TextBase(original);
                node.insertBefore(textBase);
                state.nodeAdded(textBase);
            }

            if (startOffset != lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                Node node1 = new Text(escapedChars);
                if (textBase != null) {
                    textBase.appendChild(node1);
                } else {
                    node.insertBefore(node1);
                }
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
            if (textBase != null) {
                textBase.appendChild(linkNode);
            } else {
                node.insertBefore(linkNode);
            }
            state.nodeAddedWithChildren(linkNode);

            lastEscaped = textMapper.originalOffset(link.getBeginIndex() + linkText.length());
        }

        if (lastEscaped > 0) {
            if (lastEscaped != original.length()) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
                Node node1 = new Text(escapedChars);
                if (textBase != null) {
                    textBase.appendChild(node1);
                } else {
                    node.insertBefore(node1);
                }
                state.nodeAdded(node1);
            }
        }

        if (processedNode) {
            node.unlink();
            state.nodeRemoved(node);
        }
    }

    public static class Factory extends NodePostProcessorFactory {
        public Factory() {
            super(false);
            // TODO: figure our why optimization does not work after AutoLink inserted by inline parser
            //addNodeWithExclusions(Text.class, DoNotDecorate.class, DoNotLinkDecorate.class);
            addNodes(Text.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new AutolinkNodePostProcessor(document);
        }
    }
}
