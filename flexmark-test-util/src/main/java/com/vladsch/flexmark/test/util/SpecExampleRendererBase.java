package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;

public abstract class SpecExampleRendererBase implements SpecExampleRenderer {
  protected final SpecExample myExample;
  protected final DataHolder myOptions;
  protected final boolean myIncludeExampleInfo;
  private boolean myIsFinalized;
  private String myRenderedHtml;
  private String myRenderedAst;

  protected SpecExampleRendererBase(SpecExample example, DataHolder options) {
    this(example, options, true);
  }

  protected SpecExampleRendererBase(
      SpecExample example, DataHolder options, boolean includeExampleInfo) {
    myExample = example;
    myOptions = options == null ? new DataSet() : options.toImmutable();
    myIncludeExampleInfo = includeExampleInfo;
  }

  public boolean isFinalized() {
    return myIsFinalized;
  }

  @Override
  public final String getHtml() {
    if (myRenderedHtml == null || !isFinalized()) {
      myRenderedHtml = renderHtml();
    }
    return myRenderedHtml;
  }

  @Override
  public final String getAst() {
    if (myRenderedAst == null || !isFinalized()) {
      myRenderedAst = renderAst();
    }
    return myRenderedAst;
  }

  protected abstract String renderHtml();

  protected abstract String renderAst();

  @Override
  public void finalizeRender() {
    myIsFinalized = true;
  }

  @Override
  public boolean includeExampleInfo() {
    return myIncludeExampleInfo;
  }

  @Override
  public SpecExample getExample() {
    return myExample;
  }

  @Override
  public DataHolder getOptions() {
    return myOptions.toImmutable();
  }
}
