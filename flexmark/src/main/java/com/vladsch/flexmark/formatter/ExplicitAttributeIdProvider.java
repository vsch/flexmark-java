package com.vladsch.flexmark.formatter;

import com.vladsch.flexmark.util.ast.Node;

interface ExplicitAttributeIdProvider {
  /**
   * Used by AttributesExtension to insert attributes for headings during merge
   *
   * @param node node
   * @param id explicit id
   * @param context context
   * @param markdown markdown writer
   */
  void addExplicitId(Node node, String id, NodeFormatterContext context, MarkdownWriter markdown);
}
