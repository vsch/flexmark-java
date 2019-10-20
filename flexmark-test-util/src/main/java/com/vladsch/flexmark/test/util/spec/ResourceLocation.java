package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.function.Function;

import static com.vladsch.flexmark.test.util.spec.ResourceUrlResolver.*;

public class ResourceLocation {
    final private @NotNull Class<?> resourceClass;
    final private @NotNull String resourcePath;
    final private @NotNull String fileUrlPrefix;
    final private @NotNull String fileUrl;
    final private @NotNull String resolvedResourcePath;

    public ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        this(resourceClass, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    public ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix) {
        this(resourceClass, resourcePath, fileUrlPrefix, TestUtils.getSpecResourceFileUrl(resourceClass, resourcePath, fileUrlPrefix), TestUtils.getSpecResourceName(resourceClass.getName(), resourcePath));
    }

    public ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix, @NotNull String fileUrl) {
        this(resourceClass, resourcePath, fileUrlPrefix, fileUrl, TestUtils.getSpecResourceName(resourceClass.getName(), resourcePath));
    }

    private ResourceLocation(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix, @NotNull String fileUrl, @NotNull String resolvedResourcePath) {
        this.resourceClass = resourceClass;
        this.resourcePath = resourcePath;
        this.fileUrlPrefix = fileUrlPrefix;
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
    public String getFileUrlPrefix() {
        return fileUrlPrefix;
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
        if (!fileUrlPrefix.equals(location.fileUrlPrefix)) return false;
        if (!fileUrl.equals(location.fileUrl)) return false;
        return resolvedResourcePath.equals(location.resolvedResourcePath);
    }

    // @formatter:off
    @NotNull public ResourceLocation withResourceClass(@NotNull Class<?> resourceClass) { return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix, fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withResourcePath(@NotNull String resourcePath) { return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix, fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withFileUrlPrefix(@NotNull String fileUrlPrefix) { return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix, fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withFileUrl(@NotNull String fileUrl) { return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix, fileUrl, resolvedResourcePath); };
    @NotNull public ResourceLocation withResolvedResourcePath(@NotNull String resolvedResourcePath) { return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix, fileUrl, resolvedResourcePath); };
    // @formatter:on

    @Override
    public int hashCode() {
        int result = resourceClass.hashCode();
        result = 31 * result + resourcePath.hashCode();
        result = 31 * result + fileUrlPrefix.hashCode();
        result = 31 * result + fileUrl.hashCode();
        result = 31 * result + resolvedResourcePath.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ResourceLocation {" +
                " resourceClass=" + resourceClass +
                ", resourcePath='" + resourcePath + '\'' +
                ", fileUrlPrefix='" + fileUrlPrefix + '\'' +
                '}';
    }

    public static final ResourceLocation NULL = ResourceLocation.of(Object.class, "", "", "");

    public static @NotNull ResourceLocation of(@NotNull String resourcePath) {
        return new ResourceLocation(ComboSpecTestCase.class, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        return new ResourceLocation(resourceClass, resourcePath, TestUtils.DEFAULT_URL_PREFIX);
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix) {
        return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix);
    }

    public static @NotNull ResourceLocation of(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String fileUrlPrefix, @NotNull String fileUrl) {
        return new ResourceLocation(resourceClass, resourcePath, fileUrlPrefix, fileUrl);
    }

    private final static ArrayList<Function<String, String>> urlResolvers = new ArrayList<>();

    public static void registerUrlResolver(@NotNull Function<String, String> resolver) {
        ResourceLocation.urlResolvers.add(resolver);
    }

    @NotNull
    public static String adjustedFileUrl(@NotNull URL url) {
        String externalForm = url.toExternalForm();
        String bestProtocolMatch = null;

        for (Function<String, String> resolver : urlResolvers) {
            String filePath = resolver.apply(externalForm);
            if (filePath == null) continue;

            if (hasProtocol(filePath) && bestProtocolMatch == null) {
                bestProtocolMatch = filePath;
            } else {
                File file = new File(filePath);
                if (file.exists()) {
                    return TestUtils.FILE_PROTOCOL + filePath;
                }
            }
        }

        return bestProtocolMatch != null ? bestProtocolMatch : externalForm;
    }
}
