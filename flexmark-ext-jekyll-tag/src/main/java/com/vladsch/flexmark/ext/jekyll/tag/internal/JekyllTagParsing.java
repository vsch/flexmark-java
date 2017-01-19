package com.vladsch.flexmark.ext.jekyll.tag.internal;

import com.vladsch.flexmark.ast.util.Parsing;

import java.util.regex.Pattern;

class JekyllTagParsing {
    final Parsing myParsing;
    final String OPEN_MACROTAG;
    final Pattern MACRO_OPEN;
    final Pattern MACRO_TAG;

    public JekyllTagParsing(Parsing parsing) {
        this.myParsing = parsing;
        this.OPEN_MACROTAG = "\\{%\\s+(" + myParsing.TAGNAME + ")(?:\\s+.+)?\\s+%\\}";
        this.MACRO_OPEN = Pattern.compile('^' + OPEN_MACROTAG + "\\s*$", Pattern.CASE_INSENSITIVE);
        this.MACRO_TAG = Pattern.compile(OPEN_MACROTAG);
    }
}
