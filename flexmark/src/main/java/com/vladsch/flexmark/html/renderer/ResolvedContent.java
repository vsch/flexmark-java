package com.vladsch.flexmark.html.renderer;

import org.jspecify.annotations.Nullable;

import java.util.Arrays;

public class ResolvedContent {
    final private ResolvedLink resolvedLink;
    final private LinkStatus status;
    final private byte @Nullable [] content;

    public ResolvedContent(ResolvedLink resolvedLink, LinkStatus status, byte @Nullable [] content) {
        this.resolvedLink = resolvedLink;
        this.status = status;
        this.content = content;
    }
    
    
    // @formatter:off
    public ResolvedContent withStatus(LinkStatus status) { return status == this.status ? this : new ResolvedContent(resolvedLink, status, content); }
    public ResolvedContent withContent(byte @Nullable [] content) { return Arrays.equals(this.content, content) ? this : new ResolvedContent(resolvedLink, status, content); }
    // @formatter:on

    public ResolvedLink getResolvedLink() {
        return resolvedLink;
    }

    public LinkStatus getStatus() {
        return status;
    }

    public byte @Nullable [] getContent() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResolvedContent content1 = (ResolvedContent) o;

        if (!resolvedLink.equals(content1.resolvedLink)) return false;
        if (!status.equals(content1.status)) return false;
        return Arrays.equals(content, content1.content);
    }

    @Override
    public int hashCode() {
        int result = resolvedLink.hashCode();
        result = 31 * result + status.hashCode();
        result = 31 * result + Arrays.hashCode(content);
        return result;
    }
}
