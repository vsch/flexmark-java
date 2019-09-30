package com.vladsch.flexmark.util.visitor;

/**
 * intended to be extended with specific handler function. see {@link AstVisitHandler}
 *
 * @param <N> subclass of {@link Node}
 * @param <A> subclass of {@link AstNodeAdaptingVisitor}
 */
public abstract class AstNodeAdaptingVisitHandler<Node, N extends Node, A extends AstNodeAdaptingVisitor<Node, N>> {
    protected final Class<? extends N> myClass;
    protected final A myAdapter;

    public AstNodeAdaptingVisitHandler(Class<? extends N> aClass, A adapter) {
        myClass = aClass;
        myAdapter = adapter;
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public A getNodeAdapter() {
        return myAdapter;
    }

//    // implement whatever function interface is desired for the adapter
//    @Override
//    public void render(Node node, NodeRendererContext context, HtmlWriter html) {
//        //noinspection unchecked
//        myAdapter.render((N) node, context, html);
//    }
//
//    @Override
//    public void render(Node node, NodeFormatterContext context, MarkdownWriter markdown) {
//        //noinspection unchecked
//        myAdapter.render((N) node, context, markdown);
//    }
//    @Override
//    public void render(Node node, DocxRendererContext context) {
//        //noinspection unchecked
//        myAdapter.render((N) node, context);
//    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AstNodeAdaptingVisitHandler<Node, ?, ?> other = (AstNodeAdaptingVisitHandler<Node, ?, ?>) o;

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
