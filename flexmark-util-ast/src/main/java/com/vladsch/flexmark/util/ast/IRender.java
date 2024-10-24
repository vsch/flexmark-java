package com.vladsch.flexmark.util.ast;

import com.vladsch.flexmark.util.data.DataHolder;

/** Render interface for rendering implementation for RenderingTestCase */
public interface IRender {
  // CAUTION: the reason this is not a Document, which it always is in practice is for tests which
  // generate
  //    a fake NODE and generating a fake Document (unless made into an interface and without
  // extras) would be too difficult
  void render(Node document, Appendable output);

  /**
   * Render the tree of nodes to HTML.
   *
   * @param document the root node
   * @return the rendered HTML
   */
  default String render(Node document) {
    StringBuilder sb = new StringBuilder();
    render(document, sb);
    return sb.toString();
  }

  /**
   * Get Options for parsing
   *
   * @return DataHolder for options
   */
  DataHolder getOptions();
}
