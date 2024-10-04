package com.vladsch.flexmark.test.util.spec;

import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.util.misc.Utils;
import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class SpecExample {
  public static final SpecExample NULL =
      new SpecExample(ResourceLocation.NULL, 0, null, "", 0, "", "", null, null, true);

  private final @NotNull ResourceLocation resourceLocation;
  private final int lineNumber;
  private final @Nullable String optionsSet;
  private final @Nullable String section;
  private final int exampleNumber;
  private final @NotNull String source;
  private final @NotNull String html;
  private final @Nullable String ast;
  private final @Nullable String comment;
  private final boolean isNull;

  public SpecExample(
      @NotNull ResourceLocation resourceLocation,
      int lineNumber,
      @Nullable String optionsSet,
      @Nullable String section,
      int exampleNumber,
      @NotNull String source,
      @NotNull String html,
      @Nullable String ast,
      String comment) {
    this(
        resourceLocation,
        lineNumber,
        optionsSet,
        section,
        exampleNumber,
        source,
        html,
        ast,
        comment,
        false);
  }

  private SpecExample(
      @NotNull ResourceLocation resourceLocation,
      int lineNumber,
      @Nullable String optionsSet,
      @Nullable String section,
      int exampleNumber,
      @NotNull String source,
      @NotNull String html,
      @Nullable String ast,
      String comment,
      boolean isNull) {
    this.resourceLocation = resourceLocation;
    this.lineNumber = lineNumber;
    this.section = section;
    this.exampleNumber = exampleNumber;
    this.source = source;
    this.html = html;
    this.ast = ast;
    this.comment = comment == null ? null : comment.trim();
    this.isNull = isNull;

    if (optionsSet == null) {
      this.optionsSet = null;
    } else {
      String trimmedSet = optionsSet.trim();
      this.optionsSet = trimmedSet.isEmpty() ? null : trimmedSet;
    }
  }

  public SpecExample withResourceLocation(@NotNull ResourceLocation location) {
    return new SpecExample(
        location,
        lineNumber,
        optionsSet,
        section,
        exampleNumber,
        source,
        html,
        ast,
        comment,
        isNull);
  }

  public SpecExample withSource(@NotNull String source) {
    return new SpecExample(
        resourceLocation,
        lineNumber,
        optionsSet,
        section,
        exampleNumber,
        source,
        html,
        ast,
        comment,
        isNull);
  }

  public SpecExample withHtml(@NotNull String html) {
    return new SpecExample(
        resourceLocation,
        lineNumber,
        optionsSet,
        section,
        exampleNumber,
        source,
        html,
        ast,
        comment,
        isNull);
  }

  public SpecExample withAst(@Nullable String ast) {
    return new SpecExample(
        resourceLocation,
        lineNumber,
        optionsSet,
        section,
        exampleNumber,
        source,
        html,
        ast,
        comment,
        isNull);
  }

  public boolean isFullSpecExample() {
    return this != NULL
        && isNull
        && !Objects.equals(this.resourceLocation, NULL.resourceLocation)
        && Objects.equals(this.optionsSet, NULL.optionsSet)
        && Objects.equals(this.section, NULL.section)
        && this.exampleNumber == NULL.exampleNumber
        && Objects.equals(this.source, NULL.source)
        && Objects.equals(this.html, NULL.html)
        && Objects.equals(this.ast, NULL.ast)
        && Objects.equals(this.comment, NULL.comment);
  }

  @Nullable
  public String getOptionsSet() {
    return optionsSet;
  }

  @NotNull
  public String getFileUrlWithLineNumber() {
    return getFileUrlWithLineNumber(0);
  }

  @NotNull
  public String getFileUrlWithLineNumber(int lineOffset) {
    return resourceLocation.getFileUrl(Utils.minLimit(lineNumber + lineOffset, 0));
  }

  @NotNull
  public ResourceLocation getResourceLocation() {
    return resourceLocation;
  }

  @NotNull
  public String getSource() {
    return source;
  }

  @NotNull
  public String getHtml() {
    return html;
  }

  @Nullable
  public String getAst() {
    return ast;
  }

  @Nullable
  public String getSection() {
    return section;
  }

  public int getExampleNumber() {
    return exampleNumber;
  }

  @Override
  public String toString() {
    if (this.isFullSpecExample()) {
      return "Full Spec";
    } else if (this == NULL) {
      return "NULL";
    } else {
      return "" + section + ": " + exampleNumber;
    }
  }

  private static final ConcurrentMap<String, String> classMap = new ConcurrentHashMap<>();

  public static @NotNull SpecExample ofCaller(
      int callNesting,
      Class<?> resourceClass,
      @NotNull String source,
      @NotNull String html,
      @Nullable String ast) {
    StackTraceElement[] trace = Thread.currentThread().getStackTrace();
    StackTraceElement traceElement = trace[callNesting + 2];
    String javaClassFile = classMap.get(resourceClass.getName());
    if (javaClassFile == null) {
      String fileName = traceElement.getFileName();
      // need path to class, so fake it with class resource file
      String javaFilePath = resourceClass.getName().replace('.', '/');
      File javaPathFile = new File("/" + javaFilePath).getParentFile();
      String javaPath = javaPathFile.getPath() + "/" + fileName;
      URL url = null;
      String prefix = null;
      String resourcePath = null;
      while (true) {
        String absolutePath = Utils.removeSuffix(javaPathFile.getPath(), "/");
        resourcePath = Utils.removePrefix(absolutePath, '/').replace('/', '.') + ".txt";
        url = resourceClass.getResource("/" + resourcePath);
        if (url != null) {
          prefix = Utils.getResourceAsString(resourceClass, "/" + resourcePath).trim();
          break;
        }
        javaPathFile = javaPathFile.getParentFile();
        if (javaPathFile == null) break;
      }
      if (url == null) {
        throw new IllegalStateException(
            "Class mapping file not found for Class "
                + resourceClass
                + " add file under test resources with package for name and .txt extension");
      }
      String fileUrl = TestUtils.adjustedFileUrl(url);
      javaClassFile =
          fileUrl.replaceFirst(
              "/resources((?:/[^/]*?)*)/" + resourcePath,
              Utils.prefixWith(Utils.removeSuffix(prefix, '/'), '/') + "$1" + javaPath);
      classMap.put(resourceClass.getName(), javaClassFile);
    }

    ResourceLocation location = new ResourceLocation(resourceClass, "", javaClassFile);
    return new SpecExample(
        location,
        traceElement.getLineNumber() - 1,
        null,
        traceElement.getMethodName(),
        0,
        source,
        html,
        ast,
        "");
  }
}
