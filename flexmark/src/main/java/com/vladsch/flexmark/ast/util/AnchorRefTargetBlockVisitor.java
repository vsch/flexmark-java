package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.AnchorRefTarget;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitorBase;

/**
 * Abstract visitor that visits only children of blocks excluding Paragraphs
 *
 * <p>Can be used to only process block nodes efficiently skipping text. If you override a method
 * and want visiting to descend into children, call {@link #visitChildren}.
 */
public abstract class AnchorRefTargetBlockVisitor extends NodeVisitorBase {
  protected abstract void visit(AnchorRefTarget node);

  protected abstract boolean preVisit(Node node);

  @Override
  public void visit(Node node) {
    if (node instanceof AnchorRefTarget) visit((AnchorRefTarget) node);

    if (preVisit(node) && node instanceof Block) {
      visitChildren(node);
    }
  }
}
