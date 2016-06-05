package com.vladsch.flexmark.spec;

public class AstSpecExample {

    private final String section;
    private final int exampleNumber;
    private final String source;
    private final String html;
    private final String ast;

    public AstSpecExample(String section, int exampleNumber, String source, String html, String ast) {
        this.section = section;
        this.exampleNumber = exampleNumber;
        this.source = source;
        this.html = html;
        this.ast = ast;
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

    @Override
    public String toString() {
        return "Section \"" + section + "\" example " + exampleNumber;
    }
}
