package com.vladsch.flexmark.ext.autolink.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.*;
import org.jetbrains.annotations.NotNull;
import org.nibor.autolink.LinkExtractor;
import org.nibor.autolink.LinkSpan;
import org.nibor.autolink.LinkType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AutolinkNodePostProcessor extends NodePostProcessor {
    final static private Pattern URI_PREFIX = Pattern.compile("\\b([a-z][a-z0-9+.-]*://)(?:\\s|$)");

    final private Pattern ignoredLinks;
    final private boolean intellijDummyIdentifier;

    final private LinkExtractor linkExtractor = LinkExtractor.builder()
            .linkTypes(EnumSet.of(LinkType.URL, LinkType.WWW, LinkType.EMAIL))
            .build();

    public AutolinkNodePostProcessor(Document document) {
        String ignoreLinks = AutolinkExtension.IGNORE_LINKS.get(document);
        ignoredLinks = ignoreLinks.isEmpty() ? null : Pattern.compile(ignoreLinks);
        intellijDummyIdentifier = Parser.INTELLIJ_DUMMY_IDENTIFIER.get(document);
    }

    public boolean isIgnoredLinkPrefix(CharSequence url) {
        if (ignoredLinks != null) {
            Matcher matcher = ignoredLinks.matcher(url);
            return matcher.matches();
        }
        return false;
    }

    private static class DummyLinkSpan implements LinkSpan {
        final private LinkType linkType;
        final private int beginIndex;
        final private int endIndex;

        public DummyLinkSpan(LinkType linkType, int beginIndex, int endIndex) {
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
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
        // TODO: figure our why optimization does not work after AutoLink inserted by inline parser
        if (node.getAncestorOfType(DoNotDecorate.class, DoNotLinkDecorate.class) != null) return;

        BasedSequence combined = node.getChars();
        BasedSequence original = combined;
        Node firstNode = node;
        Node lastNode = node;
        ArrayList<Range> htmlEntities = new ArrayList<>();

        if (node.getNext() instanceof TypographicText || node.getNext() instanceof HtmlEntity) {
            // we absorb this, just in case it is part of the link
            if (node.getNext().getChars().isContinuationOf(combined)) {
                Node typoGraphic = node.getNext();
                ArrayList<BasedSequence> combinedSequences = new ArrayList<>();
                combinedSequences.add(combined);

                while (typoGraphic instanceof TypographicText || typoGraphic instanceof HtmlEntity || typoGraphic instanceof Text) {
                    if (!typoGraphic.getChars().isContinuationOf(combined) || typoGraphic.getChars().startsWith(" ") || combined.endsWith(" ")) break;
                    combined = typoGraphic.getChars();
                    if (typoGraphic instanceof HtmlEntity) {
                        htmlEntities.add(Range.of(combined.getStartOffset(), combined.getEndOffset()));
                    }
                    combinedSequences.add(combined);
                    lastNode = typoGraphic;
                    typoGraphic = typoGraphic.getNext();
                }

                original = SegmentedSequence.create(node.getChars(), combinedSequences);
            }
        }

        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);

        BasedSequence unescapedHtml = original;
        
        if (!htmlEntities.isEmpty()) {
            // need to replace all HTML entities in html entity regions
            int lastOffset = 0;
            
            for (Range range : htmlEntities) {
                if (lastOffset < range.getStart()) {
                    unescapedHtml = Escaping.unescapeHtml(original, range.getStart(), range.getEnd(), textMapper);
                } 
            }
        }
        
        BasedSequence literal = Escaping.unescape(unescapedHtml, textMapper);

        if (intellijDummyIdentifier) {
            literal = Escaping.removeAll(literal, "\u001f", textMapper);
        }

        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);
        ArrayList<LinkSpan> linksList = new ArrayList<>();

        for (LinkSpan link : links) {
            linksList.add(link);
        }

        Matcher matcher = URI_PREFIX.matcher(literal);
        while (matcher.find()) {
            int start = matcher.start(1);
            int end = matcher.end(1);

            if (linksList.isEmpty()) {
                linksList.add(new DummyLinkSpan(LinkType.URL, start, end));
            } else {
                int iMax = linksList.size();
                boolean skip = false;

                for (int i = 0; i < iMax; i++) {
                    LinkSpan link = linksList.get(i);
                    if (end < link.getBeginIndex()) {
                        // insert here
                        linksList.add(i, new DummyLinkSpan(LinkType.URL, start, end));
                        skip = true;
                        break;
                    } else if (start >= link.getBeginIndex() && end <= link.getEndIndex()) {
                        // overlap, skip
                        skip = true;
                        break;
                    }
                }

                if (!skip) {
                    linksList.add(new DummyLinkSpan(LinkType.URL, start, end));
                }
            }
        }

        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase || !(node.getParent() instanceof TextBase) ? null : (TextBase) node.getParent();
        boolean processedNode = false;

        for (LinkSpan link : linksList) {
            BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex()).trimEnd();
            if (isIgnoredLinkPrefix(linkText)) continue;

            int startOffset = textMapper.originalOffset(link.getBeginIndex());
            processedNode = true;

            if (lastEscaped == 0 && firstNode != lastNode) {
                // need to see if we need to abort because the first link is not in the first text node
                if (startOffset >= node.getChars().length()) {
                    // skip this, it will be processed by next Text node processor
                    return;
                }
            }

            if (wrapInTextBase) {
                wrapInTextBase = false;
                textBase = new TextBase(original);
                node.insertBefore(textBase);
                state.nodeAdded(textBase);
            }

            if (startOffset > lastEscaped) {
                BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                Node node1 = new Text(escapedChars);
                if (textBase != null) {
                    textBase.appendChild(node1);
                } else {
                    node.insertBefore(node1);
                }
                state.nodeAdded(node1);
            }

            BasedSequence linkChars = linkText.baseSubSequence(linkText.getStartOffset(), linkText.getEndOffset());
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
            if (firstNode != lastNode) {
                // remove all typographic nodes already processed and truncate sequence to exclude ones not processed
                Node removeNode = firstNode.getNext();
                int length = node.getChars().length();

                while (removeNode != null) {
                    if (length >= lastEscaped) {
                        // we are done, the rest should be excluded
                        original = original.subSequence(0, length);
                        break;
                    }

                    length += removeNode.getChars().length();
                    Node next = removeNode.getNext();
                    removeNode.unlink();
                    state.nodeRemoved(removeNode);

                    if (removeNode == lastNode) break;

                    removeNode = next;
                }
            }

            if (lastEscaped < original.length()) {
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

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new AutolinkNodePostProcessor(document);
        }
    }
}
