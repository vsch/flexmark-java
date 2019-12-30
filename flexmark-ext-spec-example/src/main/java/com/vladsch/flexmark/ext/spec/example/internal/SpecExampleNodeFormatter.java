package com.vladsch.flexmark.ext.spec.example.internal;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ext.spec.example.SpecExampleBlock;
import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.formatter.NodeFormatter;
import com.vladsch.flexmark.formatter.NodeFormatterContext;
import com.vladsch.flexmark.formatter.NodeFormatterFactory;
import com.vladsch.flexmark.formatter.NodeFormattingHandler;
import com.vladsch.flexmark.formatter.internal.CoreNodeFormatter;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.html.LineAppendable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class SpecExampleNodeFormatter implements NodeFormatter {
    final private SpecExampleOptions formatOptions;
    final private String[] mySections = new String[7];// 0 is not used and signals no section when indexed by lastSectionLevel
    private String mySection = "";
    private int myLastSectionLevel = 1;
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
            myLastSectionLevel = pair.getSecond();
            myFlexMarkExampleCount = 0;
        }

        context.delegateRender();
    }

    void render(SpecExampleBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
        if (!context.isTransformingText()) {
            markdown.blankLine();
            StringBuilder out = new StringBuilder();
            TestUtils.addSpecExample(
                    formatOptions.exampleBreak,
                    formatOptions.sectionBreak,
                    true,
                    false,
                    out,
                    node.getSource().toStringOrNull(),
                    node.getHtml().toStringOrNull(),
                    node.getAst().toStringOrNull(),
                    node.getOptions().trim().nullIfBlank().toStringOrNull(),
                    true,
                    mySection,
                    myFlexMarkExampleCount + 1
            );

            markdown.pushOptions()
                    .removeOptions(LineAppendable.F_WHITESPACE_REMOVAL)
                    .append(out.toString()).line()
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
