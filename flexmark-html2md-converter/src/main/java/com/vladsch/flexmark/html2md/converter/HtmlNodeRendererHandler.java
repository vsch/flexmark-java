package com.vladsch.flexmark.html2md.converter;

import org.jsoup.nodes.Node;

public class HtmlNodeRendererHandler<N extends Node> implements CustomHtmlNodeRenderer<N> {
    protected final String myTagName;
    protected final Class<? extends N> myClass;
    protected final CustomHtmlNodeRenderer<N> myAdapter;

    public HtmlNodeRendererHandler(String tagName, Class<? extends N> aClass, CustomHtmlNodeRenderer<N> adapter) {
        myTagName = tagName;
        myClass = aClass;
        myAdapter = adapter;
    }

    @Override
    public void render(Node node, HtmlNodeConverterContext context, HtmlMarkdownWriter markdown) {
        //noinspection unchecked
        myAdapter.render((N) node, context, markdown);
    }

    public Class<? extends N> getNodeType() {
        return myClass;
    }

    public String getTagName() {
        return myTagName;
    }

    public CustomHtmlNodeRenderer<N> getNodeAdapter() {
        return myAdapter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        HtmlNodeRendererHandler<?> other = (HtmlNodeRendererHandler<?>) o;

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
