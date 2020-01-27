package com.vladsch.flexmark.ext.abbreviation.internal;

import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.ast.TextBase;
import com.vladsch.flexmark.ext.abbreviation.Abbreviation;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationBlock;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.autolink.internal.AutolinkNodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessor;
import com.vladsch.flexmark.parser.block.NodePostProcessorFactory;
import com.vladsch.flexmark.util.ast.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AbbreviationNodePostProcessor extends NodePostProcessor {
    private Pattern abbreviations = null;
    private HashMap<String, BasedSequence> abbreviationMap = null;

    private AbbreviationNodePostProcessor(Document document) {
        computeAbbreviations(document);
    }

    private void computeAbbreviations(Document document) {
        AbbreviationRepository abbrRepository = AbbreviationExtension.ABBREVIATIONS.get(document);

        if (!abbrRepository.isEmpty()) {
            abbreviationMap = new HashMap<>();
            StringBuilder sb = new StringBuilder();

            // sort reverse alphabetical order so longer ones match first. for sdk7
            ArrayList<String> abbreviations = new ArrayList<>(abbrRepository.keySet());
            abbreviations.sort(Comparator.reverseOrder());

            for (String abbr : abbreviations) {
                // Issue #198, test for empty abbr
                if (!abbr.isEmpty()) {
                    AbbreviationBlock abbreviationBlock = abbrRepository.get(abbr);
                    if (abbreviationBlock != null) {
                        BasedSequence abbreviation = abbreviationBlock.getAbbreviation();
                        if (!abbreviation.isEmpty()) {
                            abbreviationMap.put(abbr, abbreviation);

                            if (sb.length() > 0) sb.append("|");

                            if (Character.isLetterOrDigit(abbr.charAt(0))) sb.append("\\b");
                            sb.append("\\Q").append(abbr).append("\\E");
                            if (Character.isLetterOrDigit(abbr.charAt(abbr.length() - 1))) sb.append("\\b");
                        }
                    }
                }
            }

            if (sb.length() > 0) this.abbreviations = Pattern.compile(sb.toString());
        }
    }

    @Override
    public void process(@NotNull NodeTracker state, @NotNull Node node) {
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
            BasedSequence abbreviation = abbreviationMap.get(m.group(0));
            if (abbreviation != null) {

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
        @Nullable
        @Override
        public Set<Class<?>> getAfterDependents() {
            HashSet<Class<?>> set = new HashSet<>();
            set.add(AutolinkNodePostProcessor.Factory.class);
            return set;
        }

        public Factory() {
            super(false);
            addNodeWithExclusions(Text.class, DoNotDecorate.class, DoNotLinkDecorate.class);
        }

        @NotNull
        @Override
        public NodePostProcessor apply(@NotNull Document document) {
            return new AbbreviationNodePostProcessor(document);
        }
    }
}
