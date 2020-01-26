package com.vladsch.flexmark.util.visitor;

import org.jetbrains.annotations.NotNull;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
abstract public class AstHandler<N, A extends AstAction<? super N>> {
    final private @NotNull Class<? extends N> aClass;
    final private @NotNull A adapter;

    public AstHandler(@NotNull Class<? extends N> klass, @NotNull A adapter) {
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
