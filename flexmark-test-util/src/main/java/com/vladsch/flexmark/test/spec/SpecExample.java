package com.vladsch.flexmark.test.spec;

import com.vladsch.flexmark.test.util.TestUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class SpecExample {
    public static final SpecExample NULL = new SpecExample("", 0, null, "", 0, "", "", null, null, true);

    private final @NotNull String fileUrl;
    private final int lineNumber;
    private final @Nullable String optionsSet;
    private final @Nullable String section;
    private final int exampleNumber;
    private final @NotNull String source;
    private final @NotNull String html;
    private final @Nullable String ast;
    private final @Nullable String comment;
    private final boolean isNull;

    public SpecExample(@NotNull String fileUrl, int lineNumber, @Nullable String optionsSet, @Nullable String section, int exampleNumber, @NotNull String source, @NotNull String html, @Nullable String ast, String comment) {
        this(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment, false);
    }

    private SpecExample(@NotNull String fileUrl, int lineNumber, @Nullable String optionsSet, @Nullable String section, int exampleNumber, @NotNull String source, @NotNull String html, @Nullable String ast, String comment, boolean isNull) {
        this.fileUrl = fileUrl;
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

    // @formatter:off
    public SpecExample withFileUrl(@NotNull String fileUrl) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    public SpecExample withOptionsSet(@Nullable String optionsSet) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    public SpecExample withSection(@Nullable String section) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    public SpecExample withExampleNumber(int exampleNumber) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    public SpecExample withSource(@NotNull String source) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    public SpecExample withHtml(@NotNull String html) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    public SpecExample withAst(@Nullable String ast) { return new SpecExample(fileUrl, lineNumber, optionsSet, section, exampleNumber, source, html, ast, comment , isNull); }
    // @formatter:on

    public boolean isFullSpecExample() {
        return this != NULL && isNull
                && !Objects.equals(this.fileUrl, NULL.fileUrl)
                && Objects.equals(this.optionsSet, NULL.optionsSet)
                && Objects.equals(this.section, NULL.section)
                && this.exampleNumber == NULL.exampleNumber
                && Objects.equals(this.source, NULL.source)
                && Objects.equals(this.html, NULL.html)
                && Objects.equals(this.ast, NULL.ast)
                && Objects.equals(this.comment, NULL.comment)
                ;
    }

    public boolean isNull() {
        return isNull;
    }

    public boolean isSpecExample() {
        return isNotNull() && !isFullSpecExample();
    }

    public boolean isNotNull() {
        return !isNull;
    }

    @Nullable
    public String getOptionsSet() {
        return optionsSet;
    }

    @NotNull
    public String getFileUrl() {
        return TestUtils.getUrlWithLineNumber(fileUrl, lineNumber);
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

    @Nullable
    public String getComment() {
        return comment;
    }

    public boolean hasComment() {
        return comment != null && !comment.isEmpty();
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
}
