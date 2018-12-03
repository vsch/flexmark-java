package com.vladsch.flexmark.html.renderer;

import com.vladsch.flexmark.util.html.Attribute;
import com.vladsch.flexmark.util.html.Attributes;

public class ResolvedLink {
    private final LinkType myLinkType;
    private final String myUrl;
    private final LinkStatus myStatus;
    private Attributes myAttributes;

    public ResolvedLink(LinkType linkType, CharSequence url) {
        this(linkType, url, null, LinkStatus.UNKNOWN);
    }

    public ResolvedLink(LinkType linkType, CharSequence url, Attributes attributes) {
        this(linkType, url, attributes, LinkStatus.UNKNOWN);
    }

    public Attributes getAttributes() {
        return myAttributes;
    }

    public Attributes getNonNullAttributes() {
        if (myAttributes == null) {
            myAttributes = new Attributes();
        }
        return myAttributes;
    }

    public ResolvedLink(LinkType linkType, CharSequence url, Attributes attributes, LinkStatus status) {
        myLinkType = linkType;
        myUrl = String.valueOf(url);
        myStatus = status;
        if (attributes != null) {
            getNonNullAttributes().addValues(attributes);
        }
    }

    // @formatter:off
    public ResolvedLink withLinkType(LinkType linkType) { return linkType == this.myLinkType ? this : new ResolvedLink(linkType, myUrl, myAttributes, myStatus); }
    public ResolvedLink withStatus(LinkStatus status) { return status == this.myStatus ? this : new ResolvedLink(myLinkType, myUrl, myAttributes, status); }
    // @formatter:on

    public LinkType getLinkType() {
        return myLinkType;
    }

    public LinkStatus getStatus() {
        return myStatus;
    }

    public ResolvedLink withUrl(CharSequence url) {
        String useUrl = String.valueOf(url);
        return this.myUrl.equals(useUrl) ? this : new ResolvedLink(myLinkType, useUrl, myAttributes, myStatus);
    }

    public String getUrl() {
        return myUrl;
    }

    public ResolvedLink withTitle(CharSequence title) {
        String haveTitle = myAttributes == null ? null : myAttributes.getValue(Attribute.TITLE_ATTR);
        if (title == haveTitle || haveTitle != null && haveTitle.equals(title)) return this;

        Attributes attributes = new Attributes(myAttributes);
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

    public ResolvedLink withTarget(CharSequence target) {
        String haveTarget = myAttributes == null ? null : myAttributes.getValue(Attribute.TARGET_ATTR);
        if (target == haveTarget || haveTarget != null && haveTarget.equals(target)) return this;

        Attributes attributes = new Attributes(myAttributes);
        if (target == null) {
            attributes.remove(Attribute.TARGET_ATTR);
            if (attributes.isEmpty()) attributes = null;
        } else {
            attributes.replaceValue(Attribute.TARGET_ATTR, target);
        }
        return new ResolvedLink(myLinkType, myUrl, attributes, myStatus);
    }

    public String getTarget() {
        return myAttributes == null ? null : myAttributes.getValue(Attribute.TARGET_ATTR);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResolvedLink)) return false;

        ResolvedLink link = (ResolvedLink) o;

        if (!myLinkType.equals(link.myLinkType)) return false;
        if (!myUrl.equals(link.myUrl)) return false;
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
