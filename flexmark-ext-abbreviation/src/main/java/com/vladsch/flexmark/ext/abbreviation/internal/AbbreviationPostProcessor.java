package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;

import java.util.HashMap;
import java.util.regex.Pattern;

public class AbbreviationPostProcessor implements PostProcessor {
    private Pattern abbreviations = null;
    private boolean initialized = false;

    private void initAbbreviations(Node node) {
        initialized = true;
        Document document = node.getDocument();
        HashMap<String, AbbreviationBlock> abbrMap = document.getValueOrDefault(AbbreviationBlockParser.ABBREVIATION_MAP_KEY);
        
        if (!abbrMap.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String abbr : abbrMap.keySet()) {
                AbbreviationBlock abbreviationBlock = abbrMap.get(abbr);
                BasedSequence abbreviation = abbreviationBlock.getAbbreviation();
                if (!abbreviation.isEmpty()) {
                    if (sb.length() > 0) sb.append("|");
                    sb.append("\\b\\Q").append(abbr).append("\\E\\b");
                }
            }
        }
    }

    public Node process(Node node) {
        if (!initialized) {
            initAbbreviations(node);
        }

        if (abbreviations != null) {
            AbbreviationVisitor visitor = new AbbreviationVisitor();
            node.accept(visitor);
        }
        return node;
    }

    private void linkify(Text text) {
        BasedSequence original = text.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper();
        BasedSequence literal = Escaping.unescapeSequence(original, textMapper);
        
        Iterable<LinkSpan> links = linkExtractor.extractLinks(literal);
        //
        //Node lastNode = text;
        //int lastEscaped = 0;
        //for (LinkSpan link : links) {
        //    BasedSequence linkText = literal.subSequence(link.getBeginIndex(), link.getEndIndex());
        //    int index = textMapper.originalOffset(link.getBeginIndex());
        //
        //    if (index != lastEscaped) {
        //        BasedSequence escapedChars = original.subSequence(lastEscaped, index);
        //        lastNode = insertNode(new Text(escapedChars), lastNode);
        //    }
        //
        //    Text contentNode = new Text(linkText);
        //    LinkNode linkNode;
        //
        //    if (link.getType() == LinkType.EMAIL) {
        //        linkNode = new MailLink();
        //        ((MailLink) linkNode).setText(linkText);
        //    } else {
        //        linkNode = new AutoLink();
        //        ((AutoLink) linkNode).setContent(linkText);
        //    }
        //
        //    linkNode.setCharsFromContent();
        //    linkNode.appendChild(contentNode);
        //    lastNode = insertNode(linkNode, lastNode);
        //
        //    lastEscaped = textMapper.originalOffset(link.getEndIndex());
        //}
        //
        //if (lastEscaped != original.length()) {
        //    BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
        //    insertNode(new Text(escapedChars), lastNode);
        //}
        //text.unlink();
    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class AbbreviationVisitor extends AbstractVisitor {
        @Override
        public void visit(Text text) {
            if (!isVisiting(text, LinkNode.class)) {
                linkify(text);
            }
        }
    }
}
