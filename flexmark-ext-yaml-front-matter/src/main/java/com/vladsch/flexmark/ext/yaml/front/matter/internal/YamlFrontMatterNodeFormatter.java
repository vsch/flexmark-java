package com.vladsch.flexmark.ext.yaml.front.matter.internal;

import com.vladsch.flexmark.ext.yaml.front.matter.YamlFrontMatterBlock;
import com.vladsch.flexmark.formatter.FormattingPhase;
import com.vladsch.flexmark.formatter.MarkdownWriter;
import com.vladsch.flexmark.formatter.NodeFormatter;
import com.vladsch.flexmark.formatter.NodeFormatterContext;
import com.vladsch.flexmark.formatter.NodeFormatterFactory;
import com.vladsch.flexmark.formatter.NodeFormattingHandler;
import com.vladsch.flexmark.formatter.PhasedNodeFormatter;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class YamlFrontMatterNodeFormatter implements PhasedNodeFormatter {
  public YamlFrontMatterNodeFormatter() {}

  @Nullable
  @Override
  public Set<FormattingPhase> getFormattingPhases() {
    return new HashSet<>(Collections.singleton(FormattingPhase.DOCUMENT_FIRST));
  }

  @Nullable
  @Override
  public Set<Class<?>> getNodeClasses() {
    return null;
  }

  @Override
  public void renderDocument(
      @NotNull NodeFormatterContext context,
      @NotNull MarkdownWriter markdown,
      @NotNull Document document,
      @NotNull FormattingPhase phase) {
    if (phase == FormattingPhase.DOCUMENT_FIRST) {
      Node node = document.getFirstChild();
      if (node instanceof YamlFrontMatterBlock) {
        markdown.openPreFormatted(false);
        markdown.append(node.getChars()).blankLine();
        markdown.closePreFormatted();
      }
    }
  }

  @Nullable
  @Override
  public Set<NodeFormattingHandler<?>> getNodeFormattingHandlers() {
    return new HashSet<>(
        Collections.singletonList(
            new NodeFormattingHandler<>(
                YamlFrontMatterBlock.class, YamlFrontMatterNodeFormatter.this::render)));
  }

  private void render(
      YamlFrontMatterBlock node, NodeFormatterContext context, MarkdownWriter markdown) {}

  public static class Factory implements NodeFormatterFactory {
    @NotNull
    @Override
    public NodeFormatter create(@NotNull DataHolder options) {
      return new YamlFrontMatterNodeFormatter();
    }
  }
}
