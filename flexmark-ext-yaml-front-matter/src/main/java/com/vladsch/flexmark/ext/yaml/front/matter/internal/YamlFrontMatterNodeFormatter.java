package com.vladsch.flexmark.ext.yaml.front.matter.internal;

import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class YamlFrontMatterNodeFormatter implements PhasedNodeFormatter {
    public YamlFrontMatterNodeFormatter(DataHolder options) {
    }

    @Override
    public Set<FormattingPhase> getFormattingPhases() {
        return new HashSet<FormattingPhase>(Collections.singleton(FormattingPhase.DOCUMENT_FIRST));
    }

    @Override
    public Set<Class<?>> getNodeClasses() {
        return null;
    }

    @Override
    public void renderDocument(NodeFormatterContext context, MarkdownWriter markdown, Document document, FormattingPhase phase) {
        if (phase == FormattingPhase.DOCUMENT_FIRST) {
            Node node = document.getFirstChild();
            if (node instanceof YamlFrontMatterBlock) {
                markdown.openPreFormatted(false);
                markdown.append(node.getChars()).blankLine();
                markdown.closePreFormatted();
            }
        }
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<YamlFrontMatterBlock>(YamlFrontMatterBlock.class, new CustomNodeFormatter<YamlFrontMatterBlock>() {
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
        public NodeFormatter create(DataHolder options) {
            return new YamlFrontMatterNodeFormatter(options);
        }
    }
}
