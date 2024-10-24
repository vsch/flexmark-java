package com.vladsch.flexmark.util.visitor;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
public abstract class AstHandler<N, A extends AstAction> {
  private final Class<? extends N> aClass;
  private final A adapter;

  protected AstHandler(Class<? extends N> klass, A adapter) {
    aClass = klass;
    this.adapter = adapter;
  }

  public Class<? extends N> getNodeType() {
    return aClass;
  }

  public A getAdapter() {
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
