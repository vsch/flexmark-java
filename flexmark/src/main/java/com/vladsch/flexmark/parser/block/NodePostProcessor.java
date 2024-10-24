package com.vladsch.flexmark.parser.block;

import com.vladsch.flexmark.parser.PostProcessor;
import com.vladsch.flexmark.util.ast.Document;

public abstract class NodePostProcessor implements PostProcessor {
  /**
   * @param document the node to post-process
   * @return the result of post-processing, may be a modified {@code document} argument
   */
  @Override
  public final Document processDocument(Document document) {
    return document;
  }
}
