package com.vladsch.flexmark.core.test.util.html;

import com.vladsch.flexmark.core.test.util.html.HtmlRendererTest.CustomLinkResolverImpl;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;
import org.jetbrains.annotations.NotNull;

class Factory extends IndependentLinkResolverFactory {
  @NotNull
  @Override
  public LinkResolver apply(@NotNull LinkResolverBasicContext context) {
    return new CustomLinkResolverImpl(context);
  }
}
