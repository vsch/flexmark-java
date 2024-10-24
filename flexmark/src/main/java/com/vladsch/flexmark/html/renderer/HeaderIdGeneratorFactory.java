package com.vladsch.flexmark.html.renderer;

/** Factory for instantiating an HTML id generator */
public interface HeaderIdGeneratorFactory extends HtmlIdGeneratorFactory {
  /**
   * Create a new HeaderIdGenerator for the specified resolver context.
   *
   * @param context the context for link resolution
   * @return an HTML id generator.
   */
  HtmlIdGenerator create(LinkResolverContext context);
}
