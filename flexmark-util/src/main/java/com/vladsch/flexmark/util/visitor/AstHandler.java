package com.vladsch.flexmark.util.visitor;

import org.jetbrains.annotations.NotNull;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
abstract public class AstHandler<N, A extends AstAction<? super N>> {
    final private @NotNull Class<? extends N> myClass;
    final private @NotNull A myAdapter;

    public AstHandler(@NotNull Class<? extends N> klass, @NotNull A adapter) {
        myClass = klass;
        myAdapter = adapter;
    }

    public @NotNull Class<? extends N> getNodeType() {
        return myClass;
    }

    public @NotNull A getAdapter() {
        return myAdapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AstHandler<?, ?> other = (AstHandler<?, ?>) o;

        if (myClass != other.myClass) return false;
        return myAdapter == other.myAdapter;
    }

    @Override
    public int hashCode() {
        int result = myClass.hashCode();
        result = 31 * result + myAdapter.hashCode();
        return result;
    }
}
