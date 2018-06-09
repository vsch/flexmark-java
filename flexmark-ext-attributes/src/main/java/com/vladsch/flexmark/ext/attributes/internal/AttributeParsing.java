package com.vladsch.flexmark.ext.attributes.internal;

import com.vladsch.flexmark.ast.util.Parsing;

import java.util.regex.Pattern;

class AttributeParsing {
    final Parsing myParsing;
    final Pattern ATTRIBUTES_TAG;
    final Pattern ATTRIBUTE;

    public AttributeParsing(Parsing parsing) {
        this.myParsing = parsing;
        String unquotedValue = myParsing.UNQUOTEDVALUE;//.replace("]+","}{"); // exclude braces
        this.ATTRIBUTE = Pattern.compile("\\s*([#.]" + unquotedValue + "|" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?");
        this.ATTRIBUTES_TAG = Pattern.compile("\\{((?:" + "\\s*([#.]" + unquotedValue + "|" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?" + ")" +
                "(?:" + "\\s+([#.]" + unquotedValue + "|" + myParsing.ATTRIBUTENAME + ")\\s*(?:=\\s*(" + myParsing.ATTRIBUTEVALUE + ")?" + ")?" + ")*" + ")\\s*\\}");
    }
}
