package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.util.Parsing;

import java.util.regex.Pattern;

class AttributeParsing {
    final Parsing myParsing;
    final Pattern ATTRIBUTES_TAG;
    final Pattern ATTRIBUTE;

    public AttributeParsing(Parsing parsing) {
        this.myParsing = parsing;
        this.ATTRIBUTE = Pattern.compile("\\s*([#.]" + myParsing.UNQUOTEDVALUE + "|" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?");
        this.ATTRIBUTES_TAG = Pattern.compile("\\{((?:" + "\\s*([#.]" + myParsing.UNQUOTEDVALUE + "|" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?" + ")" +
                "(?:" + "\\s+([#.]" + myParsing.UNQUOTEDVALUE + "|" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?" + ")*" + ")\\}");
    }
}
