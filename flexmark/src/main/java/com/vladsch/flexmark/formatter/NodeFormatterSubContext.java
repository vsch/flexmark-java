package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import java.io.IOException;
import java.util.List;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class NodeFormatterSubContext implements NodeFormatterContext {
  final MarkdownWriter markdown;
  Node renderingNode;
  List<NodeFormattingHandler<?>> rendererList = null;
  int rendererIndex = -1;

  NodeFormatterSubContext(@NotNull MarkdownWriter markdown) {
    this.markdown = markdown;
    this.renderingNode = null;
  }

  @Nullable
  Node getRenderingNode() {
    return renderingNode;
  }

  void setRenderingNode(@Nullable Node renderingNode) {
    this.renderingNode = renderingNode;
  }

  @Override
  public @NotNull MarkdownWriter getMarkdown() {
    return markdown;
  }

  void flushTo(@NotNull Appendable out, int maxBlankLines, int maxTrailingBlankLines) {
    markdown.line();
    try {
      markdown.appendTo(out, maxBlankLines, maxTrailingBlankLines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
