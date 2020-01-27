package com.vladsch.flexmark.ext.typographic.internal;

import com.vladsch.flexmark.ast.util.Parsing;

class SmartsParsing {
    final Parsing myParsing;
    final String ELIPSIS;
    final String ELIPSIS_SPACED;
    final String EN_DASH;
    final String EM_DASH;

    public SmartsParsing(Parsing parsing) {
        this.myParsing = parsing;
        this.ELIPSIS = "...";
        this.ELIPSIS_SPACED = ". . .";
        this.EN_DASH = "--";
        this.EM_DASH = "---";
    }
}
