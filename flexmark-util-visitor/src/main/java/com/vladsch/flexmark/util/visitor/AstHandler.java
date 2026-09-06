package com.vladsch.flexmark.util.visitor;


/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
abstract public class AstHandler<N, A extends AstAction<? super N>> {
    final private Class<? extends N> aClass;
    final private A adapter;

    public AstHandler(Class<? extends N> klass, A adapter) {
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AstHandler<?, ?> other = (AstHandler<?, ?>) o;

        if (aClass != other.aClass) return false;
        return adapter == other.adapter;
    }

    @Override
    public int hashCode() {
        int result = aClass.hashCode();
        result = 31 * result + adapter.hashCode();
        return result;
    }
}
