package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.internal.util.BasedSequence;
import com.vladsch.flexmark.internal.util.Escaping;
import com.vladsch.flexmark.internal.util.ReplacedTextMapper;
import com.vladsch.flexmark.node.*;
import com.vladsch.flexmark.parser.PostProcessor;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbbreviationPostProcessor implements PostProcessor {
    private Pattern abbreviations = null;
    private HashMap<String, String> abbreviationMap = null;

    private void initializeNode(Node node) {
        Document document = node.getDocument();
        AbbreviationRepository abbrRepository = document.get(AbbreviationExtension.ABBREVIATIONS);

        if (!abbrRepository.isEmpty()) {
            abbreviationMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();
            for (String abbr : abbrRepository.keySet()) {
                AbbreviationBlock abbreviationBlock = abbrRepository.get(abbr);
                BasedSequence abbreviation = abbreviationBlock.getAbbreviation();
                if (!abbreviation.isEmpty()) {
                    abbreviationMap.put(abbr, abbreviation.toString());

                    if (sb.length() > 0) sb.append("|");

                    if (Character.isLetterOrDigit(abbr.charAt(0))) sb.append("\\b");
                    sb.append("\\Q").append(abbr).append("\\E");
                    if (Character.isLetterOrDigit(abbr.charAt(abbr.length()-1))) sb.append("\\b");
                }
            }

            if (sb.length() > 0) abbreviations = Pattern.compile(sb.toString());
        }
    }

    public Node process(Node node) {
        initializeNode(node);

        if (abbreviations != null) {
            AbbreviationVisitor visitor = new AbbreviationVisitor();
            node.accept(visitor);
        }

        finalizeNode(node);
        return node;
    }

    private void finalizeNode(Node node) {
        abbreviations = null;
        abbreviationMap = null;
    }

    private void linkify(Text node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        Matcher m = abbreviations.matcher(literal);
        Node lastNode = node;
        int lastEscaped = 0;

        while (m.find()) {
            //String found = m.group();
            if (abbreviationMap.containsKey(m.group(0))) {
                String abbreviation = abbreviationMap.get(m.group(0));

                BasedSequence abbrText = literal.subSequence(m.start(0), m.end(0));
                int startOffset = textMapper.originalOffset(m.start(0));
                int endOffset = textMapper.originalOffset(m.end(0));

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    lastNode = insertNode(new Text(escapedChars), lastNode);
                }

                BasedSequence origAbbrText = original.subSequence(startOffset, endOffset);
                Abbreviation abbrNode = new Abbreviation(origAbbrText, abbreviation);
                lastNode = insertNode(abbrNode, lastNode);

                lastEscaped = endOffset;
            }
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            insertNode(new Text(escapedChars), lastNode);
        }
        node.unlink();
    }

    private static Node insertNode(Node node, Node insertAfterNode) {
        insertAfterNode.insertAfter(node);
        return node;
    }

    private class AbbreviationVisitor extends AbstractVisitor {
        @Override
        public void visit(Text text) {
            if (!isVisiting(text, DoNotLinkify.class)) {
                linkify(text);
            }
        }
    }
}
