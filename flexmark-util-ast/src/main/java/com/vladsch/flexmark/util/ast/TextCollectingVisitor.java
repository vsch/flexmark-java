package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import java.util.function.BiConsumer;

public class TextCollectingVisitor {
  private final NodeVisitor myVisitor;
  private SpaceInsertingSequenceBuilder out;
  private int flags; // flags defined by TextContainer

  public TextCollectingVisitor() {
    myVisitor =
        new NodeVisitor() {
          @Override
          public void processNode(
              Node node, boolean withChildren, BiConsumer<Node, Visitor<Node>> processor) {
            if (!node.isOrDescendantOfType(DoNotCollectText.class)) {
              out.setLastNode(node);
              if (node instanceof Block && out.isNotEmpty()) {
                out.appendEol();
              }
              if (node instanceof TextContainer) {
                final TextContainer textContainer = (TextContainer) node;
                if (textContainer.collectText(out, flags, myVisitor)) {
                  if (node instanceof BlankLineBreakNode && out.isNotEmpty()) {
                    out.appendEol();
                  }
                  processChildren(node, processor);
                }
                textContainer.collectEndText(out, flags, myVisitor);
              } else {
                processChildren(node, processor);
              }
              if (node instanceof LineBreakNode && out.needEol()) {
                out.appendEol();
              }
            }
          }
        };
  }

  public String getText() {
    return out.toString();
  }

  public BasedSequence getSequence() {
    return out.toSequence();
  }

  public String collectAndGetText(Node node) {
    return collectAndGetText(node, 0);
  }

  private void collect(Node node, int flags) {
    out = SpaceInsertingSequenceBuilder.emptyBuilder(node.getChars(), flags);
    this.flags = flags;
    myVisitor.visit(node);
  }

  public String collectAndGetText(Node node, int flags) {
    collect(node, flags);
    return out.toString();
  }
}
