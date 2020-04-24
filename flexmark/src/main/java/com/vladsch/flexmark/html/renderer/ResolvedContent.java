package com.vladsch.flexmark.html.renderer;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class ResolvedContent {
    final private @NotNull ResolvedLink resolvedLink;
    final private @NotNull LinkStatus status;
    final private @Nullable byte[] content;

    public ResolvedContent(@NotNull ResolvedLink resolvedLink, @NotNull LinkStatus status, @Nullable byte[] content) {
        this.resolvedLink = resolvedLink;
        this.status = status;
        this.content = content;
    }
    
    
    // @formatter:off
    public ResolvedContent withStatus(@NotNull LinkStatus status) { return status == this.status ? this : new ResolvedContent(resolvedLink, status, content); }
    public ResolvedContent withContent(@Nullable byte[] content) { return Arrays.equals(this.content, content) ? this : new ResolvedContent(resolvedLink, status, content); }
    // @formatter:on

    public @NotNull ResolvedLink getResolvedLink() {
        return resolvedLink;
    }

    public @NotNull LinkStatus getStatus() {
        return status;
    }

    public @Nullable byte[] getContent() {
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
