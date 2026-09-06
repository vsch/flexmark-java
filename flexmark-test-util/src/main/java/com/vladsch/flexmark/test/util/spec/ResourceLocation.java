package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.ComboSpecTestCase;
import com.vladsch.flexmark.test.util.TestUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class ResourceLocation {
    final public static ResourceLocation NULL = of(Object.class, "", "");

    final private Class<?> resourceClass;
    final private String resourcePath;
    final private String fileUrl;
    final private String resolvedResourcePath;

    public ResourceLocation(Class<?> resourceClass, String resourcePath, String fileUrl) {
        this(resourceClass, resourcePath, fileUrl, TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath));
    }

    private ResourceLocation(Class<?> resourceClass, String resourcePath, String fileUrl, String resolvedResourcePath) {
        this.resourceClass = resourceClass;
        this.resourcePath = resourcePath;
        this.fileUrl = fileUrl;
        this.resolvedResourcePath = resolvedResourcePath;
    }

    public Class<?> getResourceClass() {
        return resourceClass;
    }

    public String getResourcePath() {
        return resourcePath;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public String getFileDirectoryUrl() {
        int pos = fileUrl.lastIndexOf(File.separatorChar);
        if (pos > 0) {
            return fileUrl.substring(0, pos + 1);
        }
        return fileUrl;
    }

    public String getFileUrl(int lineNumber) {
        return TestUtils.getUrlWithLineNumber(getFileUrl(), lineNumber);
    }

    public String getResolvedResourcePath() {
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
    public ResourceLocation withResourceClass(Class<?> resourceClass) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    public ResourceLocation withResourcePath(String resourcePath) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    public ResourceLocation withFileUrl(String fileUrl) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
    public ResourceLocation withResolvedResourcePath(String resolvedResourcePath) { return new ResourceLocation(resourceClass, resourcePath,  fileUrl, resolvedResourcePath); };
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

    public static ResourceLocation of(String resourcePath) {
        return new ResourceLocation(ComboSpecTestCase.class, resourcePath,
                TestUtils.getSpecResourceFileUrl(ComboSpecTestCase.class, resourcePath),
                TestUtils.getResolvedSpecResourcePath(ComboSpecTestCase.class.getName(), resourcePath)
        );
    }

    public static ResourceLocation of(Class<?> resourceClass, String resourcePath) {
        return new ResourceLocation(resourceClass, resourcePath,
                TestUtils.getSpecResourceFileUrl(resourceClass, resourcePath),
                TestUtils.getResolvedSpecResourcePath(resourceClass.getName(), resourcePath)
        );
    }

    public static ResourceLocation of(Class<?> resourceClass, String resourcePath, String fileUrl) {
        return new ResourceLocation(resourceClass, resourcePath, fileUrl);
    }

    public static String getResourceText(ResourceLocation location) {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            InputStream inputStream = getResourceInputStream(location);
            InputStreamReader streamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(streamReader);
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            streamReader.close();
            inputStream.close();
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getResourceInputStream(ResourceLocation location) {
        String useSpecResource = location.getResolvedResourcePath();
        InputStream stream = location.getResourceClass().getResourceAsStream(useSpecResource);
        if (stream == null) {
            throw new IllegalStateException("Could not load " + location);
        }

        return stream;
    }
}
