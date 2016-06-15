package com.vladsch.flexmark.spec;

public class SpecExample {

    private final String optionsSet;
    private final String section;
    private final int exampleNumber;
    private final String source;
    private final String html;
    private final String ast;

    public SpecExample(String optionsSet, String section, int exampleNumber, String source, String html) {
        String trimmedSet = optionsSet.trim();
        this.optionsSet = trimmedSet.isEmpty() ? null : trimmedSet;
        this.section = section;
        this.exampleNumber = exampleNumber;
        this.source = source;
        this.html = html;
        this.ast = null;
    }

    public SpecExample(String optionsSet, String section, int exampleNumber, String source, String html, String ast) {
        String trimmedSet = optionsSet.trim();
        this.optionsSet = trimmedSet.isEmpty() ? null : trimmedSet;
        this.section = section;
        this.exampleNumber = exampleNumber;
        this.source = source;
        this.html = html;
        this.ast = ast;
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

    @Override
    public String toString() {
        return "Section \"" + section + "\" example " + exampleNumber;
    }
}
