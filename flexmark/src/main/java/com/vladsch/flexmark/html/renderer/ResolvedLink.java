package com.vladsch.flexmark.html.renderer;

public class ResolvedLink {
    private final LinkType myLinkType;
    private final String myUrl;
    private final String myTitle;
    private final LinkStatus myStatus;

    public ResolvedLink(LinkType linkType, CharSequence url) {
        this(linkType, url, null, LinkStatus.UNKNOWN);
    }

    public ResolvedLink(LinkType linkType, CharSequence url, CharSequence title) {
        this(linkType, url, title, LinkStatus.UNKNOWN);
    }

    public ResolvedLink(LinkType linkType, CharSequence url, CharSequence title, LinkStatus status) {
        myLinkType = linkType;
        myUrl = url instanceof String ? (String) url : String.valueOf(url);
        myTitle = title == null ? null : title instanceof String ? (String) title : String.valueOf(title);
        myStatus = status;
    }

    // @formatter:off
    public ResolvedLink withLinkType(LinkType linkType) { return linkType == this.myLinkType ? this : new ResolvedLink(linkType, myUrl, myTitle, myStatus); }
    public ResolvedLink withStatus(LinkStatus status) { return status == this.myStatus ? this : new ResolvedLink(myLinkType, myUrl, myTitle, status); }
    // @formatter:on
    public ResolvedLink withUrl(CharSequence url) {
        String useUrl = url instanceof String ? (String) url : String.valueOf(url);
        return this.myUrl.equals(useUrl) ? this : new ResolvedLink(myLinkType, useUrl, myTitle, myStatus);
    }

    public ResolvedLink withTitle(CharSequence title) {
        String useTitle = title == null ? null : title instanceof String ? (String) title : String.valueOf(title);
        return this.myTitle == useTitle || this.myTitle.equals(useTitle) ? this : new ResolvedLink(myLinkType, myUrl, useTitle, myStatus);
    }

    public LinkType getLinkType() {
        return myLinkType;
    }

    public String getUrl() {
        return myUrl;
    }

    public String getTitle() {
        return myTitle;
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
