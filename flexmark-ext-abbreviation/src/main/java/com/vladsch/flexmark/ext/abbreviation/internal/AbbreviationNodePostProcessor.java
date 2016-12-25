package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ast.*;
import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.internal.AutolinkNodePostProcessor;
import com.vladsch.flexmark.parser.PostProcessorFactory;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.html.Escaping;
import com.vladsch.flexmark.util.NodeTracker;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbbreviationNodePostProcessor extends NodePostProcessor {
    private Pattern abbreviations = null;
    private HashMap<String, String> abbreviationMap = null;

    private AbbreviationNodePostProcessor(Document document) {
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
    public void process(NodeTracker state, Node node) {
        if (abbreviations == null) return;

        BasedSequence original = node.getChars();
        ReplacedTextMapper textMapper = new ReplacedTextMapper(original);
        BasedSequence literal = Escaping.unescape(original, textMapper);

        Matcher m = abbreviations.matcher(literal);
        int lastEscaped = 0;
        boolean wrapInTextBase = !(node.getParent() instanceof TextBase);
        TextBase textBase = wrapInTextBase ? null : (TextBase) node.getParent();

        while (m.find()) {
            //String found = m.group();
            if (abbreviationMap.containsKey(m.group(0))) {
                String abbreviation = abbreviationMap.get(m.group(0));

                BasedSequence toDecorateText = literal.subSequence(m.start(0), m.end(0));
                int startOffset = textMapper.originalOffset(m.start(0));
                int endOffset = textMapper.originalOffset(m.end(0));

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

                BasedSequence origToDecorateText = original.subSequence(startOffset, endOffset);
                Abbreviation decorationNode = new Abbreviation(origToDecorateText, abbreviation);
                textBase.appendChild(decorationNode);
                //Text undecoratedTextNode = new Text(origToDecorateText);
                //decorationNode.appendChild(undecoratedTextNode);
                //state.nodeAddedWithChildren(decorationNode);
                state.nodeAdded(decorationNode);

                lastEscaped = endOffset;
            }
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
        @Override
        public Set<Class<? extends PostProcessorFactory>> getAfterDependents() {
            HashSet<Class<? extends PostProcessorFactory>> set = new HashSet<>();
            set.add(AutolinkNodePostProcessor.Factory.class);
            return set;
        }

        public Factory() {
            super(false);

            addNodeWithExclusions(Text.class, DoNotDecorate.class);
        }

        @Override
        public NodePostProcessor create(Document document) {
            return new AbbreviationNodePostProcessor(document);
        }
    }
}
