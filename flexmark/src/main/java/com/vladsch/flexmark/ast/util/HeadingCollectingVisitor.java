package com.vladsch.flexmark.ast.util;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.util.ast.BlockNodeVisitor;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.ast.NodeVisitor;
import com.vladsch.flexmark.util.ast.VisitHandler;
import java.util.ArrayList;
import java.util.List;

public class HeadingCollectingVisitor {
  private final List<Heading> headings = new ArrayList<>();
  private final NodeVisitor myVisitor;

  public HeadingCollectingVisitor() {
    myVisitor = new BlockNodeVisitor(new VisitHandler<>(Heading.class, headings::add));
  }

  public void collect(Node node) {
    myVisitor.visit(node);
  }

  public List<Heading> collectAndGetHeadings(Node node) {
    myVisitor.visit(node);
    return headings;
  }

  public List<Heading> getHeadings() {
    return headings;
  }
}
