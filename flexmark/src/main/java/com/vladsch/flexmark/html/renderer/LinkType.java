package com.vladsch.flexmark.html.renderer;

public class LinkType {
  public static final LinkType LINK = new LinkType("LINK");
  static final LinkType IMAGE = new LinkType("IMAGE");
  static final LinkType LINK_REF = new LinkType("LINK_REF");
  static final LinkType IMAGE_REF = new LinkType("IMAGE_REF");

  private final String myName;

  private LinkType(String name) {
    this.myName = name;
  }

  public String getName() {
    return myName;
  }

  @Override
  public boolean equals(Object object) {
    return this == object;
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
