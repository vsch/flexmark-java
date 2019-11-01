package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import org.jetbrains.annotations.NotNull;

public class ResourceLocation {
    public static final ResourceLocation NULL = of(Object.class, "", "");

    final private @NotNull Class<?> resourceClass;
    final private @NotNull String resourcePath;
    final private @NotNull String fileUrl;
    final private @NotNull String resolvedResourcePath;

    public ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrl) {
        this(resourceClass, resourcePath, fileUrl, TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath));
    }

    private ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrl, @NotNull String resolvedResourcePath) {
        this.resourceClass = resourceClass;
        this.resourcePath = resourcePath;
        this.fileUrl = fileUrl;
        this.resolvedResourcePath = resolvedResourcePath;
    }

    @NotNull
    public Class<?> getResourceClass() {
        return resourceClass;
    }

    @NotNull
    public String getResourcePath() {
        return resourcePath;
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
    public String getResolvedResourcePath() {
        return resolvedResourcePath;
    }

    public boolean isNull() {
        return this == NULL;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ResourceLocation location = (ResourceLocation) o;

        if (!resourceClass.equals(location.resourceClass)) return false;
        if (!resourcePath.equals(location.resourcePath)) return false;
        if (!fileUrl.equals(location.fileUrl)) return false;
        return resolvedResourcePath.equals(location.resolvedResourcePath);
    }

    // @formatter:off
    @NotNull public ResourceLocation withResourceClass(@NotNull Class<?> resourceClass) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withResourcePath(@NotNull String resourcePath) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withFileUrl(@NotNull String fileUrl) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withResolvedResourcePath(@NotNull String resolvedResourcePath) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    // @formatter:on

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
        return "ResourceLocation {" +
                " resourceClass=" + resourceClass +
                ", resourcePath='" + resourcePath + '\'' +
                '}';
    }

    public static @NotNull ResourceLocation of(@NotNull String resourcePath) {
        return new ResourceLocation(ComboSpecTestCase.class, resourcePath,
                TestUtils.getSpecResourceFileUrl(ComboSpecTestCase.class, resourcePath),
                TestUtils.getResolvedSpecResourcePath(ComboSpecTestCase.class.getName(), resourcePath)
        );
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        return new ResourceLocation(resourceClass, resourcePath,
                TestUtils.getSpecResourceFileUrl(resourceClass, resourcePath),
                TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath)
        );
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrl) {
        return new ResourceLocation(resourceClass, resourcePath, fileUrl);
    }
}
