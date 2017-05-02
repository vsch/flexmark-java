package com.vladsch.flexmark.html.renderer;

public class LinkType {
    public static final LinkType LINK = new LinkType("LINK");
    public static final LinkType IMAGE = new LinkType("IMAGE");
    public static final LinkType LINK_REF = new LinkType("LINK_REF");
    public static final LinkType IMAGE_REF = new LinkType("IMAGE_REF");

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
