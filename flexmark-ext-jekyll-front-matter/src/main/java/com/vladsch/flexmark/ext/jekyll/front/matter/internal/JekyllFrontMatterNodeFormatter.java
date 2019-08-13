package com.vladsch.flexmark.ext.jekyll.front.matter.internal;

import com.vladsch.flexmark.ext.jekyll.front.matter.JekyllFrontMatterBlock;
import com.vladsch.flexmark.formatter.*;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class JekyllFrontMatterNodeFormatter implements PhasedNodeFormatter {
    public JekyllFrontMatterNodeFormatter(DataHolder options) {
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
            if (node instanceof JekyllFrontMatterBlock) {
                markdown.openPreFormatted(false);
                markdown.append(node.getChars()).blankLine();
                markdown.closePreFormatted();
            }
        }
    }

    @Override
    public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
        return new HashSet<NodeFormattingHandler<?>>(Arrays.asList(
                new NodeFormattingHandler<JekyllFrontMatterBlock>(JekyllFrontMatterBlock.class, new CustomNodeFormatter<JekyllFrontMatterBlock>() {
                    @Override
                    public void render(JekyllFrontMatterBlock node, NodeFormatterContext context, MarkdownWriter markdown) {
                        JekyllFrontMatterNodeFormatter.this.render(node, context, markdown);
                    }
                })
        ));
    }

    private void render(JekyllFrontMatterBlock node, NodeFormatterContext context, MarkdownWriter markdown) {

    }

    public static class Factory implements NodeFormatterFactory {
        @Override
        public NodeFormatter create(DataHolder options) {
            return new JekyllFrontMatterNodeFormatter(options);
        }
    }
}
