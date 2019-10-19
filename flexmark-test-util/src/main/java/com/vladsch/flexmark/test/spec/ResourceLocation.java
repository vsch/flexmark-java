package com.vladsch.flexmark.test.spec;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import org.jetbrains.annotations.NotNull;

public class ResourceLocation {
    final private @NotNull Class<?> resourceClass;
    final private @NotNull String resourcePath;
    final private @NotNull String fileUrlPrefix;

    public ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        this(resourceClass, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    public ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix) {
        this.resourceClass = resourceClass;
        this.resourcePath = resourcePath;
        this.fileUrlPrefix = fileUrlPrefix;
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
    public String getFileUrlPrefix() {
        return fileUrlPrefix;
    }

    @NotNull
    public String getFileUrl() {
        return TestUtils.getSpecResourceFileUrl(this);
    }

    public static @NotNull ResourceLocation of(@NotNull String resourcePath) {
        return new ResourceLocation(ComboSpecTestCase.class, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        return new ResourceLocation(resourceClass, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix) {
        return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix);
    }
}
