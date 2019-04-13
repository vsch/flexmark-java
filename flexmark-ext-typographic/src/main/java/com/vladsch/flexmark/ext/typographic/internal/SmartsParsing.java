package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ast.util.Parsing;

import java.util.regex.Pattern;

class SmartsParsing {
    final Parsing myParsing;
    final String ELIPSIS;
    final String ELIPSIS_SPACED;
    final String EN_DASH;
    final String EM_DASH;
    final Pattern SMARTS;

    public SmartsParsing(Parsing parsing) {
        this.myParsing = parsing;
        this.ELIPSIS = "...";
        this.ELIPSIS_SPACED = ". . .";
        this.EN_DASH = "--";
        this.EM_DASH = "---";
        this.SMARTS = Pattern.compile("(^\\Q" + ELIPSIS_SPACED + "\\E|^\\Q" + ELIPSIS + "\\E|^\\Q" + EM_DASH + "\\E|^\\Q" + EN_DASH + "\\E)");
    }
}
