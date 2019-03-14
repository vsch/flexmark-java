package com.vladsch.flexmark.test;

public interface ActualExampleModifier {
    String actualSource(String source, String optionSet);
    String actualHtml(String html, String optionSet);
    String actualAst(String ast, String optionSet);
}
