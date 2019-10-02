package com.vladsch.flexmark.util.visitor;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
abstract public class AstHandler<N, A extends AstAction<? super N>> {
    final private Class<? extends N> myClass;
    final private A myAdapter;

    public AstHandler(Class<? extends N> klass, A adapter) {
        myClass = klass;
        myAdapter = adapter;
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public A getAdapter() {
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
