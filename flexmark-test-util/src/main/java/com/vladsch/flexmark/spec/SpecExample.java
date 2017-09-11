package com.vladsch.flexmark.spec;

public class SpecExample {
    public static final SpecExample NULL = new SpecExample(null, "", 0, "", "");

    private final String optionsSet;
    private final String section;
    private final int exampleNumber;
    private final String source;
    private final String html;
    private final String ast;
    private final String comment;

    public SpecExample(String optionsSet, String section, int exampleNumber, String source, String html) {
        this(optionsSet, section, exampleNumber, source, html, null);
    }

    public SpecExample(String optionsSet, String section, int exampleNumber, String source, String html, String ast) {
        this(optionsSet, section, exampleNumber, source, html, null, null);

    }
    public SpecExample(String optionsSet, String section, int exampleNumber, String source, String html, String ast, String comment) {
        this.section = section;
        this.exampleNumber = exampleNumber;
        this.source = source;
        this.html = html;
        this.ast = ast;
        this.comment = comment == null ? null : comment.trim();

        if (optionsSet == null) {
            this.optionsSet = null;
        } else {
            String trimmedSet = optionsSet.trim();
            this.optionsSet = trimmedSet.isEmpty() ? null : trimmedSet;
        }
    }

    // @formatter:off
    public SpecExample withOptionsSet(String optionsSet) { return new SpecExample(optionsSet, section, exampleNumber, source, html, ast, comment); }
    public SpecExample withSection(String section) { return new SpecExample(optionsSet, section, exampleNumber, source, html, ast, comment); }
    public SpecExample withExampleNumber(int exampleNumber) { return new SpecExample(optionsSet, section, exampleNumber, source, html, ast, comment); }
    public SpecExample withSource(String source) { return new SpecExample(optionsSet, section, exampleNumber, source, html, ast, comment); }
    public SpecExample withHtml(String html) { return new SpecExample(optionsSet, section, exampleNumber, source, html, ast, comment); }
    public SpecExample withAst(String ast) { return new SpecExample(optionsSet, section, exampleNumber, source, html, ast, comment); }
    // @formatter:on

    public boolean isFullSpecExample() {
        return this == NULL;
    }

    public boolean isNull() {
        return this == NULL;
    }

    public boolean isSpecExample() {
        return this != NULL;
    }

    public boolean isNotNull() {
        return this != NULL;
    }

    public String getOptionsSet() {
        return optionsSet;
    }

    public String getSource() {
        return source;
    }

    public String getHtml() {
        return html;
    }

    public String getAst() {
        return ast;
    }

    public String getSection() {
        return section;
    }

    public int getExampleNumber() {
        return exampleNumber;
    }

    public String getComment() {
        return comment;
    }

    public boolean hasComment() {
        return comment != null && !comment.isEmpty();
    }

    @Override
    public String toString() {
        if (this == NULL) {
            return "Full Spec Test";
        } else {
            return "Section \"" + section + "\" example " + exampleNumber;
        }
    }
}
