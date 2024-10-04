package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class IRenderBase implements IRender {
  private final DataHolder myOptions;

  public IRenderBase() {
    this(null);
  }

  public IRenderBase(DataHolder options) {
    myOptions = options;
  }

  @NotNull
  @Override
  public String render(@NotNull Node document) {
    StringBuilder out = new StringBuilder();
    render(document, out);
    return out.toString();
  }

  @Override
  @Nullable
  public DataHolder getOptions() {
    return myOptions;
  }
}
