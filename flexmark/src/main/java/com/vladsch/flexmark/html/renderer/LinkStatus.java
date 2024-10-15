package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.NotNull;

public class LinkStatus {
  public static final LinkStatus UNKNOWN = new LinkStatus("UNKNOWN");
  public static final LinkStatus VALID = new LinkStatus("VALID");

  private final String myName;

  private LinkStatus(@NotNull String name) {
    this.myName = name;
  }

  public @NotNull String getName() {
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

  public boolean isStatus(CharSequence status) {
    return myName.equals(status instanceof String ? (String) status : String.valueOf(status));
  }
}
