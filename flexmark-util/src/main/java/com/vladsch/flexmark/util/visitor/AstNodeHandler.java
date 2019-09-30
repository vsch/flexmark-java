package com.vladsch.flexmark.util.visitor;

import java.util.function.Function;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
abstract public class AstNodeHandler<N, A extends AstNodeAction<N>> {
    private final Class<?> myClass;
    private final A myAdapter;

    public AstNodeHandler(Class<? extends N> klass, A adapter) {
        myClass = klass;
        myAdapter = adapter;
    }

    public Class<?> getNodeType() {
        return myClass;
    }

    public A getNodeAdapter() {
        return myAdapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AstNodeHandler<?, ?> other = (AstNodeHandler<?, ?>) o;

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
