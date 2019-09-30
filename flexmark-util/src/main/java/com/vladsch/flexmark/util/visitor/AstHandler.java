package com.vladsch.flexmark.util.visitor;

/**
 * Base class for tracking generic node to specific node handler
 *
 * @param <N> node subclass
 * @param <A> node action
 */
abstract public class AstHandler<N, A extends AstAction<N>> {
    final private Class<N> myClass;
    final private A myAdapter;

    public AstHandler(Class<N> klass, A adapter) {
        myClass = klass;
        myAdapter = adapter;
    }

    public Class<N> getNodeType() {
        return myClass;
    }

    public A getNodeAdapter() {
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
