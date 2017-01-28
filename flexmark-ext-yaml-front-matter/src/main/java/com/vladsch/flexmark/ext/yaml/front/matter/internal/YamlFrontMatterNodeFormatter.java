
package com.vladsch.flexmark.ext.yaml.front.matter.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.formatter.CustomNodeFormatter;
import com.vladsch.flexmark.formatter.internal.*;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class YamlFrontMatterNodeFormatter implements PhasedNodeFormatter {
    public YamlFrontMatterNodeFormatter(DataHolder options) {
    }

    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return new HashSet<>(Collections.singleton(FormattingPhase.DOCUMENT_FIRST));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public void renderDocument(final NodeFormatterContext context, final MarkdownWriter markdown, final Document document, final FormattingPhase phase) {
        if (phase == FormattingPhase.DOCUMENT_FIRST) {
            final Node node = document.getFirstChild();
            if (node instanceof YamlFrontMatterBlock) {
                markdown.append(node.getChars()).blankLine();
            }
        }
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<>(YamlFrontMatterBlock.class, new CustomNodeFormatter<YamlFrontMatterBlock>() {
                    @Override
                    public void render(YamlFrontMatterBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        YamlFrontMatterNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(YamlFrontMatterBlock node, NodeFormatterContext context, MarkdownWriter markdown) {

    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(final DataHolder options) {
            return new YamlFrontMatterNodeFormatter(options);
        }
    }
}
