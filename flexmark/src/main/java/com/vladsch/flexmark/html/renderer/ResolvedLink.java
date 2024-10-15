package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolvedLink {
  private final @NotNull LinkType myLinkType;
  private final @NotNull String myUrl;
  private final @NotNull LinkStatus myStatus;
  private @Nullable MutableAttributes myAttributes;

  public ResolvedLink(@NotNull LinkType linkType, @NotNull CharSequence url) {
    this(linkType, url, null, LinkStatus.UNKNOWN);
  }

  public ResolvedLink(
      @NotNull LinkType linkType, @NotNull CharSequence url, @Nullable Attributes attributes) {
    this(linkType, url, attributes, LinkStatus.UNKNOWN);
  }

  @Nullable
  public Attributes getAttributes() {
    return myAttributes == null ? null : myAttributes.toImmutable();
  }

  public @NotNull Attributes getNonNullAttributes() {
    if (myAttributes == null) {
      myAttributes = new MutableAttributes();
    }
    return myAttributes.toImmutable();
  }

  public @NotNull MutableAttributes getMutableAttributes() {
    if (myAttributes == null) {
      myAttributes = new MutableAttributes();
    }
    return myAttributes;
  }

  private ResolvedLink(
      @NotNull LinkType linkType,
      CharSequence url,
      @Nullable Attributes attributes,
      @NotNull LinkStatus status) {
    myLinkType = linkType;
    myUrl = String.valueOf(url);
    myStatus = status;
    if (attributes != null) {
      getMutableAttributes().addValues(attributes);
    }
  }

  public ResolvedLink withStatus(@NotNull LinkStatus status) {
    return status == this.myStatus
        ? this
        : new ResolvedLink(myLinkType, myUrl, myAttributes, status);
  }

  @NotNull
  public LinkType getLinkType() {
    return myLinkType;
  }

  @NotNull
  public LinkStatus getStatus() {
    return myStatus;
  }

  public @NotNull ResolvedLink withUrl(@NotNull CharSequence url) {
    String useUrl = String.valueOf(url);
    return this.myUrl.equals(useUrl)
        ? this
        : new ResolvedLink(myLinkType, useUrl, myAttributes, myStatus);
  }

  @NotNull
  public String getUrl() {
    return myUrl;
  }

  public @NotNull String getPageRef() {
    // parse out the anchor marker and ref
    int pos = myUrl.indexOf('#');
    if (pos < 0) {
      return myUrl;
    }

    return myUrl.substring(0, pos);
  }

  public @Nullable String getAnchorRef() {
    // parse out the anchor marker and ref
    int pos = myUrl.indexOf('#');
    if (pos < 0) {
      return null;
    }

    return myUrl.substring(pos + 1);
  }

  @NotNull
  ResolvedLink withTitle(@Nullable CharSequence title) {
    String haveTitle = myAttributes == null ? null : myAttributes.getValue(Attribute.TITLE_ATTR);
    if (title == haveTitle || haveTitle != null && title != null && haveTitle.contentEquals(title))
      return this;

    MutableAttributes attributes = new MutableAttributes(myAttributes);
    if (title == null) {
      attributes.remove(Attribute.TITLE_ATTR);
      if (attributes.isEmpty()) attributes = null;
    } else {
      attributes.replaceValue(Attribute.TITLE_ATTR, title);
    }
    return new ResolvedLink(myLinkType, myUrl, attributes, myStatus);
  }

  public @Nullable String getTitle() {
    return myAttributes == null ? null : myAttributes.getValue(Attribute.TITLE_ATTR);
  }

  public String getTarget() {
    return myAttributes == null ? null : myAttributes.getValue(Attribute.TARGET_ATTR);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (!(object instanceof ResolvedLink)) {
      return false;
    }

    ResolvedLink link = (ResolvedLink) object;

    if (!myLinkType.equals(link.myLinkType)) {
      return false;
    }
    if (!myUrl.equals(link.myUrl)) {
      return false;
    }
    return myStatus.equals(link.myStatus);
  }

  @Override
  public int hashCode() {
    int result = myLinkType.hashCode();
    result = 31 * result + myUrl.hashCode();
    result = 31 * result + myStatus.hashCode();
    return result;
  }
}
