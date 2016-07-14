package com.vladsch.flexmark.html.renderer;

public class ResolvedLink {
    final private LinkType myLinkType;
    final private String myUrl;
    final private LinkStatus myStatus;

    public ResolvedLink(LinkType linkType, String url) {
        this(linkType, url, LinkStatus.UNKNOWN);
    }

    public ResolvedLink(LinkType linkType, String url, LinkStatus status) {
        myLinkType = linkType;
        myUrl = url;
        myStatus = status;
    }

    // @formatter:off
    public ResolvedLink withLinkType(LinkType myLinkType) { return myLinkType == this.myLinkType ? this : new ResolvedLink(myLinkType, myUrl, myStatus); }
    public ResolvedLink withUrl(String myUrl) { return myUrl.equals(this.myUrl) ? this : new ResolvedLink(myLinkType, myUrl, myStatus); }
    public ResolvedLink withStatus(LinkStatus myStatus) { return myStatus == this.myStatus ? this : new ResolvedLink(myLinkType, myUrl, myStatus); }
    // @formatter:on

    public LinkType getLinkType() {
        return myLinkType;
    }

    public String getUrl() {
        return myUrl;
    }

    public LinkStatus getStatus() {
        return myStatus;
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
