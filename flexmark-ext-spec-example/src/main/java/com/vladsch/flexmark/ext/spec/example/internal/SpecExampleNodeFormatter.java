package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.spec.example.SpecExampleBlock;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.LineAppendable;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpecExampleNodeFormatter implements NodeFormatter {
    final private SpecExampleOptions formatOptions;
    final private String[] mySections = new String[7];// 0 is not used and signals no section when indexed by lastSectionLevel
    private String mySection = "";
    private int myFlexMarkExampleCount = 0;

    public SpecExampleNodeFormatter(DataHolder options) {
        formatOptions = new SpecExampleOptions(options);
    }

    @Nullable
    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    // only registered if assignTextAttributes is enabled
    @Nullable
    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        HashSet<NodeFormattingHandler<?>> set = new HashSet<>();
        set.add(new NodeFormattingHandler<>(SpecExampleBlock.class, SpecExampleNodeFormatter.this::render));
        set.add(new NodeFormattingHandler<>(Heading.class, SpecExampleNodeFormatter.this::render));
        return set;
    }

    void render(Heading node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.isTransformingText() && node.isAtxHeading()) {
            Pair<String, Integer> pair = TestUtils.addSpecSection(node.getChars().toString(), node.getText().toString(), mySections);
            mySection = pair.getFirst();
            myFlexMarkExampleCount = 0;
        }

        context.delegateRender();
    }

    void render(SpecExampleBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.isTransformingText()) {
            markdown.blankLine();

            BasedSequence optionsSet = node.getOptions();
            @NotNull List<BasedSequence> optionsList = optionsSet.splitList(",", 0, BasedSequence.SPLIT_INCLUDE_DELIM_PARTS | BasedSequence.SPLIT_TRIM_SKIP_EMPTY, CharPredicate.SPACE_TAB);
            SequenceBuilder builder = node.getChars().getBuilder();
            if (optionsList.isEmpty()) optionsSet = BasedSequence.NULL;
            else {
                for (BasedSequence option : optionsList) {
                    builder.append(option);
                    if (option.equals(",")) builder.append(' ');
                }
                optionsSet = builder.toSequence();
            }

            MarkdownWriter out = new MarkdownWriter(builder.getBuilder(), markdown.getOptions());
            out.openPreFormatted(false).removeOptions(LineAppendable.F_WHITESPACE_REMOVAL);

            String exampleNumber = Integer.toString(++myFlexMarkExampleCount);
            TestUtils.addSpecExample(
                    node.getOpeningMarker().equals(formatOptions.exampleBreak) ? node.getOpeningMarker() : formatOptions.exampleBreak,
                    node.getHtmlSeparator().equals(formatOptions.sectionBreak + "\n") ? node.getHtmlSeparator() : formatOptions.sectionBreak,
                    node.getAstSeparator().equals(formatOptions.sectionBreak + "\n") ? node.getAstSeparator() : formatOptions.sectionBreak,
                    node.getClosingMarker().equals(formatOptions.exampleBreak) ? node.getClosingMarker() : formatOptions.exampleBreak,
                    true,
                    false,
                    out,
                    node.getSource(),
                    node.getHtml(),
                    node.getAst(),
                    optionsSet,
                    true,
                    node.getSection().equals(mySection) ? node.getSection() : builder.getBuilder().append(node.getSection().getEmptyPrefix()).append(mySection).append(node.getSection().getEmptySuffix()).toSequence(),
                    node.getNumber().equals(exampleNumber) ? node.getNumber() : builder.getBuilder().append(node.getNumber().getEmptyPrefix()).append(exampleNumber).append(node.getNumber().getEmptySuffix()).toSequence(),
                    node.getExampleKeyword(),
                    node.getOptionsKeyword().equals(SpecReader.OPTIONS_KEYWORD) ? node.getOptionsKeyword() : SpecReader.OPTIONS_KEYWORD);

            out.line().closePreFormatted();

            BasedSequence result = BasedSequence.of(out.toSequence(Integer.MAX_VALUE, Integer.MAX_VALUE));

            markdown.pushOptions()
                    .openPreFormatted(false)
                    .removeOptions(LineAppendable.F_WHITESPACE_REMOVAL)
                    .append(result).line()
                    .closePreFormatted()
                    .blankLine(2)
                    .popOptions();
        } else {
            context.delegateRender();
        }
    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public @Nullable Set<Class<?>> getBeforeDependents() {
            // NOTE: CoreNodeFormatter.Factory is always the last one in line so no need to add it as dependency
            return null;//Collections.singleton(CoreNodeFormatter.Factory.class);
        }

        @NotNull
        @Override
        public NodeFormatter create(@NotNull DataHolder options) {
            return new SpecExampleNodeFormatter(options);
        }
    }
}
