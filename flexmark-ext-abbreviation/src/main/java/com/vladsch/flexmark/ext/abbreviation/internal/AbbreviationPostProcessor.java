package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.internal.AutolinkPostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.parser.block.DocumentPostProcessor;
import com.vladsch.flexmark.parser.block.DocumentPostProcessorFactory;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbbreviationPostProcessor extends DocumentPostProcessor {
    private Pattern abbreviations = null;
    private HashMap<String, String> abbreviationMap = null;
    private final NodeVisitor myVisitor;

    AbbreviationPostProcessor(Document document) {
        myVisitor = new NodeVisitor(
                new VisitHandler<>(Text.class, new Visitor<Text>() {
                    @Override
                    public void visit(Text text) {
                        AbbreviationPostProcessor.this.visit(text);
                    }
                })
        );

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
                    if (Character.isLetterOrDigit(abbr.charAt(abbr.length() - 1))) sb.append("\\b");
                }
            }

            if (sb.length() > 0) abbreviations = Pattern.compile(sb.toString());
        }
    }

    @Override
    public Document processDocument(Document document) {
        if (abbreviations != null) {
            myVisitor.visit(document);
        }
        return document;
    }

    private void visit(Text text) {
        if (!text.isOrDescendantOfType(DoNotDecorate.class)) {
            process(text);
        }
    }

    private Node process(Text node) {
        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        Matcher m = abbreviations.matcher(literal);
        Node lastNode = node;
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = null;

        while (m.find()) {
            //String found = m.group();
            if (abbreviationMap.containsKey(m.group(0))) {
                String abbreviation = abbreviationMap.get(m.group(0));

                int startOffset = textMapper.originalOffset(m.start(0));
                int endOffset = textMapper.originalOffset(m.end(0));

                if (wrapInTextBase) {
                    wrapInTextBase = false;
                    textBase = new TextBase(original);
                    node.insertBefore(textBase);
                    textBase.appendChild(node);
                }

                if (startOffset != lastEscaped) {
                    BasedSequence escapedChars = original.subSequence(lastEscaped, startOffset);
                    Node node1 = new Text(escapedChars);
                    lastNode.insertAfter(node1);
                    lastNode = node1;
                }

                BasedSequence origAbbrText = original.subSequence(startOffset, endOffset);
                Abbreviation abbrNode = new Abbreviation(origAbbrText, abbreviation);
                lastNode.insertAfter(abbrNode);
                lastNode = abbrNode;

                lastEscaped = endOffset;
            }
        }

        if (lastEscaped != original.length()) {
            BasedSequence escapedChars = original.subSequence(lastEscaped, original.length());
            Node node1 = new Text(escapedChars);
            lastNode.insertAfter(node1);
        }

        lastNode = node.getNext();
        node.unlink();
        return lastNode;
    }

    public static class Factory extends DocumentPostProcessorFactory {
        @Override
        public Set<Class<? extends PostProcessorFactory>> getAfterDependents() {
            HashSet<Class<? extends PostProcessorFactory>> set = new HashSet<>();
            set.add(AutolinkPostProcessor.Factory.class);
            return set;
        }

        @Override
        public DocumentPostProcessor create(Document document) {
            return new AbbreviationPostProcessor(document);
        }
    }
}
