package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;

public class ResolvedLink {
  private final LinkType myLinkType;
  private final String myUrl;
  private final LinkStatus myStatus;
  private MutableAttributes myAttributes;

  public ResolvedLink(LinkType linkType, CharSequence url, Attributes attributes) {
    this(linkType, url, attributes, LinkStatus.UNKNOWN);
  }

  public Attributes getAttributes() {
    return myAttributes == null ? null : myAttributes.toImmutable();
  }

  public Attributes getNonNullAttributes() {
    if (myAttributes == null) {
      myAttributes = new MutableAttributes();
    }
    return myAttributes.toImmutable();
  }

  public MutableAttributes getMutableAttributes() {
    if (myAttributes == null) {
      myAttributes = new MutableAttributes();
    }
    return myAttributes;
  }

  private ResolvedLink(
      LinkType linkType, CharSequence url, Attributes attributes, LinkStatus status) {
    myLinkType = linkType;
    myUrl = String.valueOf(url);
    myStatus = status;
    if (attributes != null) {
      getMutableAttributes().addValues(attributes);
    }
  }

  public ResolvedLink withStatus(LinkStatus status) {
    return status == this.myStatus
        ? this
        : new ResolvedLink(myLinkType, myUrl, myAttributes, status);
  }

  public LinkType getLinkType() {
    return myLinkType;
  }

  public LinkStatus getStatus() {
    return myStatus;
  }

  public ResolvedLink withUrl(CharSequence url) {
    String useUrl = String.valueOf(url);
    return this.myUrl.equals(useUrl)
        ? this
        : new ResolvedLink(myLinkType, useUrl, myAttributes, myStatus);
  }

  public String getUrl() {
    return myUrl;
  }

  public String getPageRef() {
    // parse out the anchor marker and ref
    int pos = myUrl.indexOf('#');
    if (pos < 0) {
      return myUrl;
    }

    return myUrl.substring(0, pos);
  }

  public String getAnchorRef() {
    // parse out the anchor marker and ref
    int pos = myUrl.indexOf('#');
    if (pos < 0) {
      return null;
    }

    return myUrl.substring(pos + 1);
  }

  ResolvedLink withTitle(CharSequence title) {
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

  public String getTitle() {
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
