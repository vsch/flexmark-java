package com.vladsch.flexmark.html.renderer;

import java.util.Arrays;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ResolvedContent {
  private final @NotNull ResolvedLink resolvedLink;
  private final @NotNull LinkStatus status;
  private final @Nullable byte[] content;

  private ResolvedContent(
      @NotNull ResolvedLink resolvedLink, @NotNull LinkStatus status, @Nullable byte[] content) {
    this.resolvedLink = resolvedLink;
    this.status = status;
    this.content = content;
  }

  ResolvedContent withStatus(@NotNull LinkStatus status) {
    return status == this.status ? this : new ResolvedContent(resolvedLink, status, content);
  }

  ResolvedContent withContent(@Nullable byte[] content) {
    return Arrays.equals(this.content, content)
        ? this
        : new ResolvedContent(resolvedLink, status, content);
  }

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
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    ResolvedContent content1 = (ResolvedContent) object;

    if (!resolvedLink.equals(content1.resolvedLink)) {
      return false;
    }
    if (!status.equals(content1.status)) {
      return false;
    }
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
