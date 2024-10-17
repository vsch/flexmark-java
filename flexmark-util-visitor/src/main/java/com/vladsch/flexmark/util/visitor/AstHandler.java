package com.vladsch.flexmark.util.visitor;

import org.jetbrains.annotations.NotNull;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
public abstract class AstHandler<N, A extends AstAction> {
  private final @NotNull Class<? extends N> aClass;
  private final @NotNull A adapter;

  protected AstHandler(@NotNull Class<? extends N> klass, @NotNull A adapter) {
    aClass = klass;
    this.adapter = adapter;
  }

  public @NotNull Class<? extends N> getNodeType() {
    return aClass;
  }

  public @NotNull A getAdapter() {
    return adapter;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    AstHandler<?, ?> other = (AstHandler<?, ?>) object;

    if (aClass != other.aClass) {
      return false;
    }
    return adapter == other.adapter;
  }

  @Override
  public int hashCode() {
    int result = aClass.hashCode();
    result = 31 * result + adapter.hashCode();
    return result;
  }
}
