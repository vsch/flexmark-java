package com.vladsch.flexmark.html.renderer;

public class LinkType {
    final public static LinkType LINK = new LinkType("LINK");
    final public static LinkType IMAGE = new LinkType("IMAGE");
    
    final private String myName;

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
