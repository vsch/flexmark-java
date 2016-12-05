package com.vladsch.flexmark.html.renderer;

public class LinkType {
    public static final LinkType LINK = new LinkType("LINK");
    public static final LinkType IMAGE = new LinkType("IMAGE");

    private final String myName;

    public LinkType(String name) {
        this.myName = name;
    }

    public String getName() {
        return myName;
    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
