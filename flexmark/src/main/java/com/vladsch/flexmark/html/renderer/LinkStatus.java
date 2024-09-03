package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.NotNull;

public class LinkStatus {
  public static final LinkStatus UNKNOWN = new LinkStatus("UNKNOWN");
  public static final LinkStatus VALID = new LinkStatus("VALID");
  public static final LinkStatus INVALID = new LinkStatus("INVALID");
  public static final LinkStatus UNCHECKED = new LinkStatus("UNCHECKED");
  public static final LinkStatus NOT_FOUND = new LinkStatus("NOT_FOUND");

  private final String myName;

  public LinkStatus(@NotNull String name) {
    this.myName = name;
  }

  public @NotNull String getName() {
    return myName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof LinkStatus)) return false;

    LinkStatus status = (LinkStatus) o;

    return myName.equals(status.myName);
  }

  @Override
  public int hashCode() {
    return myName.hashCode();
  }

  public boolean isStatus(CharSequence status) {
    return myName.equals(status instanceof String ? (String) status : String.valueOf(status));
  }
}
