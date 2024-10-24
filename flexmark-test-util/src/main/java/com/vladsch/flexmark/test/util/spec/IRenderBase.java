package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;

public abstract class IRenderBase implements IRender {
  private final DataHolder myOptions;

  protected IRenderBase() {
    this(null);
  }

  protected IRenderBase(DataHolder options) {
    myOptions = options;
  }

  @Override
  public String render(Node document) {
    StringBuilder out = new StringBuilder();
    render(document, out);
    return out.toString();
  }

  @Override
  public DataHolder getOptions() {
    return myOptions;
  }
}
