package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.NotNull;

public class LinkType {
    final public static LinkType LINK = new LinkType("LINK");
    final public static LinkType IMAGE = new LinkType("IMAGE");
    final public static LinkType LINK_REF = new LinkType("LINK_REF");
    final public static LinkType IMAGE_REF = new LinkType("IMAGE_REF");

    final private @NotNull String myName;

    public LinkType(@NotNull String name) {
        this.myName = name;
    }

    public @NotNull String getName() {
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
