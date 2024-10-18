package com.vladsch.flexmark.ast;

import com.vladsch.flexmark.util.ast.BlankLineContainer;
import com.vladsch.flexmark.util.ast.Block;
import com.vladsch.flexmark.util.ast.Node;
import org.jetbrains.annotations.NotNull;

public abstract class ListBlock extends Block implements BlankLineContainer {
  private boolean tight;

  protected ListBlock() {}

  public boolean isTight() {
    return tight;
  }

  public boolean isLoose() {
    return !tight;
  }

  public void setTight(boolean tight) {
    this.tight = tight;
  }

  public void setLoose(boolean loose) {
    this.tight = !loose;
  }

  @Override
  public Node getLastBlankLineChild() {
    return getLastChild();
  }

  @Override
  public void getAstExtra(@NotNull StringBuilder out) {
    super.getAstExtra(out);
    if (isTight()) {
      out.append(" isTight");
    } else {
      out.append(" isLoose");
    }
  }
}
