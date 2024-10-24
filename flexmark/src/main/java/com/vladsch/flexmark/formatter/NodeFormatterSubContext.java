package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;
import java.io.IOException;
import java.util.List;

abstract class NodeFormatterSubContext implements NodeFormatterContext {
  final MarkdownWriter markdown;
  Node renderingNode;
  List<NodeFormattingHandler<?>> rendererList = null;
  int rendererIndex = -1;

  NodeFormatterSubContext(MarkdownWriter markdown) {
    this.markdown = markdown;
    this.renderingNode = null;
  }

  @Override
  public MarkdownWriter getMarkdown() {
    return markdown;
  }

  void flushTo(Appendable out, int maxBlankLines, int maxTrailingBlankLines) {
    markdown.line();
    try {
      markdown.appendTo(out, maxBlankLines, maxTrailingBlankLines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
