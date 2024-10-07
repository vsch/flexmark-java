package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import org.jetbrains.annotations.NotNull;

public class ResourceLocation {
  public static final ResourceLocation NULL = of(Object.class, "", "");

  private final @NotNull Class<?> resourceClass;
  private final @NotNull String resourcePath;
  private final @NotNull String fileUrl;
  private final @NotNull String resolvedResourcePath;

  public ResourceLocation(
      @NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrl) {
    this(
        resourceClass,
        resourcePath,
        fileUrl,
        TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath));
  }

  private ResourceLocation(
      @NotNull Class<?> resourceClass,
      @NotNull String resourcePath,
      @NotNull String fileUrl,
      @NotNull String resolvedResourcePath) {
    this.resourceClass = resourceClass;
    this.resourcePath = resourcePath;
    this.fileUrl = fileUrl;
    this.resolvedResourcePath = resolvedResourcePath;
  }

  @NotNull
  private Class<?> getResourceClass() {
    return resourceClass;
  }

  @NotNull
  public String getFileUrl() {
    return fileUrl;
  }

  @NotNull
  public String getFileUrl(int lineNumber) {
    return TestUtils.getUrlWithLineNumber(getFileUrl(), lineNumber);
  }

  @NotNull
  private String getResolvedResourcePath() {
    return resolvedResourcePath;
  }

  public boolean isNull() {
    return this == NULL;
  }

  @NotNull
  public InputStream getResourceInputStream() {
    return getResourceInputStream(this);
  }

  @NotNull
  public String getResourceText() {
    return getResourceText(this);
  }

  @Override
  public boolean equals(Object object) {
    if (this == object) {
      return true;
    }
    if (object == null || getClass() != object.getClass()) {
      return false;
    }

    ResourceLocation location = (ResourceLocation) object;

    if (!resourceClass.equals(location.resourceClass)) {
      return false;
    }
    if (!resourcePath.equals(location.resourcePath)) {
      return false;
    }
    if (!fileUrl.equals(location.fileUrl)) {
      return false;
    }
    return resolvedResourcePath.equals(location.resolvedResourcePath);
  }

  @Override
  public int hashCode() {
    int result = resourceClass.hashCode();
    result = 31 * result + resourcePath.hashCode();
    result = 31 * result + fileUrl.hashCode();
    result = 31 * result + resolvedResourcePath.hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "ResourceLocation {"
        + " resourceClass="
        + resourceClass
        + ", resourcePath='"
        + resourcePath
        + '\''
        + '}';
  }

  public static @NotNull ResourceLocation of(@NotNull String resourcePath) {
    return new ResourceLocation(
        ComboSpecTestCase.class,
        resourcePath,
        TestUtils.getSpecResourceFileUrl(ComboSpecTestCase.class, resourcePath),
        TestUtils.getResolvedSpecResourcePath(ComboSpecTestCase.class.getName(), resourcePath));
  }

  public static @NotNull ResourceLocation of(
      @NotNull Class<?> resourceClass, @NotNull String resourcePath) {
    return new ResourceLocation(
        resourceClass,
        resourcePath,
        TestUtils.getSpecResourceFileUrl(resourceClass, resourcePath),
        TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath));
  }

  private static @NotNull ResourceLocation of(
      @NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrl) {
    return new ResourceLocation(resourceClass, resourcePath, fileUrl);
  }

  @NotNull
  private static String getResourceText(@NotNull ResourceLocation location) {
    StringBuilder sb = new StringBuilder();
    try {
      String line;
      try (InputStream inputStream = getResourceInputStream(location);
          Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
          BufferedReader bufferedReader = new BufferedReader(reader)) {
        while ((line = bufferedReader.readLine()) != null) {
          sb.append(line);
          sb.append("\n");
        }
      }
      return sb.toString();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @NotNull
  private static InputStream getResourceInputStream(@NotNull ResourceLocation location) {
    String useSpecResource = location.getResolvedResourcePath();
    InputStream stream = location.getResourceClass().getResourceAsStream(useSpecResource);
    if (stream == null) {
      throw new IllegalStateException("Could not load " + location);
    }

    return stream;
  }
}
