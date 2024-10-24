package com.vladsch.flexmark.html.renderer;

public class LinkStatus {
  public static final LinkStatus UNKNOWN = new LinkStatus("UNKNOWN");
  public static final LinkStatus VALID = new LinkStatus("VALID");

  private final String myName;

  private LinkStatus(String name) {
    this.myName = name;
  }

  public String getName() {
    return myName;
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof LinkStatus)) {
      return false;
    }

    LinkStatus status = (LinkStatus) object;

    return myName.equals(status.myName);
  }

  @Override
  public int hashCode() {
    return myName.hashCode();
  }
}
