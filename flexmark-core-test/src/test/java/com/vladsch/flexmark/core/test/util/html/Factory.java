package com.vladsch.flexmark.core.test.util.html;

import com.vladsch.flexmark.core.test.util.html.HtmlRendererTest.CustomLinkResolverImpl;
import com.vladsch.flexmark.html.LinkResolver;
import com.vladsch.flexmark.html.renderer.LinkResolverBasicContext;

class Factory extends IndependentLinkResolverFactory {

  @Override
  public LinkResolver apply(LinkResolverBasicContext context) {
    return new CustomLinkResolverImpl(context);
  }
}
