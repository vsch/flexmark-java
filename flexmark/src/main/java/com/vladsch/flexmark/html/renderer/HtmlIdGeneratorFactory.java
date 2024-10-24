package com.vladsch.flexmark.html.renderer;

/** Factory for instantiating new node renderers when rendering is done. */
public interface HtmlIdGeneratorFactory {
  /**
   * Create an id generator
   *
   * @return an html id generator
   */
  HtmlIdGenerator create();
}
