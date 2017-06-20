package com.vladsch.flexmark.html.renderer;

public class ResolvedLink {
    private final LinkType myLinkType;
    private final String myUrl;
    private final String myTitle;
    private final LinkStatus myStatus;
    private final String myTarget;

    public ResolvedLink(LinkType linkType, CharSequence url) {
        this(linkType, url, null, LinkStatus.UNKNOWN);
    }

    public ResolvedLink(LinkType linkType, CharSequence url, CharSequence title) {
        this(linkType, url, title, LinkStatus.UNKNOWN);
    }

    public ResolvedLink(LinkType linkType, CharSequence url, CharSequence title, LinkStatus status) {
        this(linkType, url, title, status, null);
    }

    public ResolvedLink(LinkType linkType, CharSequence url, CharSequence title, LinkStatus status, String target) {
        myLinkType = linkType;
        myUrl = url instanceof String ? (String) url : String.valueOf(url);
        myTitle = title == null ? null : title instanceof String ? (String) title : String.valueOf(title);
        myStatus = status;
        myTarget = target;
    }
    
    // @formatter:off
    public ResolvedLink withLinkType(LinkType linkType) { return linkType == this.myLinkType ? this : new ResolvedLink(linkType, myUrl, myTitle, myStatus, myTarget); }
    public ResolvedLink withStatus(LinkStatus status) { return status == this.myStatus ? this : new ResolvedLink(myLinkType, myUrl, myTitle, status, myTarget); }
    // @formatter:on
    public ResolvedLink withUrl(CharSequence url) {
        String useUrl = url instanceof String ? (String) url : String.valueOf(url);
        return this.myUrl.equals(useUrl) ? this : new ResolvedLink(myLinkType, useUrl, myTitle, myStatus, myTarget);
    }

    public ResolvedLink withTitle(CharSequence title) {
        String useTitle = title == null ? null : title instanceof String ? (String) title : String.valueOf(title);
        return this.myTitle == useTitle || this.myTitle != null && this.myTitle.equals(useTitle) ? this : new ResolvedLink(myLinkType, myUrl, useTitle, myStatus, myTarget);
    }

    public ResolvedLink withTarget(CharSequence target) {
        String useTarget = target == null ? null : target instanceof String ? (String) target : String.valueOf(target);
        return this.myTarget == useTarget || this.myTarget != null && this.myTarget.equals(useTarget) ? this : new ResolvedLink(myLinkType, myUrl, myTitle, myStatus, useTarget);
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

    public String getTarget() {
        return myTarget;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ResolvedLink)) return false;

        ResolvedLink link = (ResolvedLink) o;

        if (!myLinkType.equals(link.myLinkType)) return false;
        if (!myUrl.equals(link.myUrl)) return false;
        if (!myTarget.equals(link.myTarget)) return false;
        return myStatus.equals(link.myStatus);
    }

    @Override
    public int hashCode() {
        int result = myLinkType.hashCode();
        result = 31 * result + myUrl.hashCode();
        result = 31 * result + myStatus.hashCode();
        result = 31 * result + myTarget.hashCode();
        return result;
    }
}
