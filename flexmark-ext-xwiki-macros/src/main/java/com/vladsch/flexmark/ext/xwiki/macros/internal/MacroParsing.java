package com.vladsch.flexmark.ext.xwiki.macros.internal;

import com.vladsch.flexmark.ast.util.Parsing;

import java.util.regex.Pattern;

class MacroParsing {
    final Parsing myParsing;
    final String OPEN_MACROTAG;
    final String CLOSE_MACROTAG;
    final String MACROTAG;
    final Pattern MACRO_OPEN;
    final Pattern MACRO_CLOSE;
    final Pattern MACRO_CLOSE_END;
    final Pattern MACRO_ATTRIBUTE;
    final Pattern MACRO_TAG;

    public MacroParsing(Parsing parsing) {
        this.myParsing = parsing;
        this.OPEN_MACROTAG = "\\{\\{(" + myParsing.TAGNAME + ")" + myParsing.ATTRIBUTE + "*" + "\\s*/?\\}\\}";
        this.CLOSE_MACROTAG = "\\{\\{/(" + myParsing.TAGNAME + ")\\s*\\}\\}";
        this.MACRO_OPEN = Pattern.compile('^' + OPEN_MACROTAG, Pattern.CASE_INSENSITIVE);
        this.MACRO_CLOSE = Pattern.compile('^' + CLOSE_MACROTAG + "\\s*$", Pattern.CASE_INSENSITIVE);
        this.MACRO_CLOSE_END = Pattern.compile(CLOSE_MACROTAG + "\\s*$", Pattern.CASE_INSENSITIVE);
        this.MACRO_ATTRIBUTE = Pattern.compile("\\s*(" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?");

        this.MACROTAG = "(?:" + OPEN_MACROTAG + ")|(?:" + CLOSE_MACROTAG + ")";
        this.MACRO_TAG = Pattern.compile(MACROTAG);
    }
}
