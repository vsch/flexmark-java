package com.vladsch.flexmark.ext.gfm.strikethrough.internal;

import com.vladsch.flexmark.ext.gfm.strikethrough.Strikethrough;
import com.vladsch.flexmark.ext.gfm.strikethrough.Subscript;
import com.vladsch.flexmark.html.HtmlWriter;
import com.vladsch.flexmark.html.renderer.NodeRenderer;
import com.vladsch.flexmark.html.renderer.NodeRendererContext;
import com.vladsch.flexmark.html.renderer.NodeRendererFactory;
import com.vladsch.flexmark.html.renderer.NodeRenderingHandler;
import com.vladsch.flexmark.util.data.DataHolder;
import java.util.HashSet;
import java.util.Set;

public class StrikethroughYouTrackRenderer implements NodeRenderer {
  public StrikethroughYouTrackRenderer() {}

  @Override
  public Set<NodeRenderingHandler<?>> getNodeRenderingHandlers() {
    Set<NodeRenderingHandler<?>> set = new HashSet<>();
    set.add(new NodeRenderingHandler<>(Strikethrough.class, this::render));
    set.add(new NodeRenderingHandler<>(Subscript.class, this::render));
    return set;
  }

  private void render(Strikethrough node, NodeRendererContext context, HtmlWriter html) {
    html.raw("--");
    context.renderChildren(node);
    html.raw("--");
  }

  private void render(Subscript node, NodeRendererContext context, HtmlWriter html) {
    html.raw("~");
    context.renderChildren(node);
    html.raw("~");
  }

  public static class Factory implements NodeRendererFactory {

    @Override
    public NodeRenderer apply(DataHolder options) {
      return new StrikethroughYouTrackRenderer();
    }
  }
}
