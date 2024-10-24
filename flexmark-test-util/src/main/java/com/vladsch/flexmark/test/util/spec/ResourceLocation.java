package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

public class ResourceLocation {
  public static final ResourceLocation NULL = of(Object.class, "", "");

  private final Class<?> resourceClass;
  private final String resourcePath;
  private final String fileUrl;
  private final String resolvedResourcePath;

  public ResourceLocation(Class<?> resourceClass, String resourcePath, String fileUrl) {
    this(
        resourceClass,
        resourcePath,
        fileUrl,
        TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath));
  }

  private ResourceLocation(
      Class<?> resourceClass, String resourcePath, String fileUrl, String resolvedResourcePath) {
    this.resourceClass = resourceClass;
    this.resourcePath = resourcePath;
    this.fileUrl = fileUrl;
    this.resolvedResourcePath = resolvedResourcePath;
  }

  private Class<?> getResourceClass() {
    return resourceClass;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public String getFileUrl(int lineNumber) {
    return TestUtils.getUrlWithLineNumber(getFileUrl(), lineNumber);
  }

  private String getResolvedResourcePath() {
    return resolvedResourcePath;
  }

  public boolean isNull() {
    return this == NULL;
  }

  public InputStream getResourceInputStream() {
    return getResourceInputStream(this);
  }

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

  public static ResourceLocation of(String resourcePath) {
    return new ResourceLocation(
        ComboSpecTestCase.class,
        resourcePath,
        TestUtils.getSpecResourceFileUrl(ComboSpecTestCase.class, resourcePath),
        TestUtils.getResolvedSpecResourcePath(ComboSpecTestCase.class.getName(), resourcePath));
  }

  public static ResourceLocation of(Class<?> resourceClass, String resourcePath) {
    return new ResourceLocation(
        resourceClass,
        resourcePath,
        TestUtils.getSpecResourceFileUrl(resourceClass, resourcePath),
        TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath));
  }

  private static ResourceLocation of(Class<?> resourceClass, String resourcePath, String fileUrl) {
    return new ResourceLocation(resourceClass, resourcePath, fileUrl);
  }

  private static String getResourceText(ResourceLocation location) {
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

  private static InputStream getResourceInputStream(ResourceLocation location) {
    String useSpecResource = location.getResolvedResourcePath();
    InputStream stream = location.getResourceClass().getResourceAsStream(useSpecResource);
    if (stream == null) {
      throw new IllegalStateException("Could not load " + location);
    }

    return stream;
  }
}
