package com.vladsch.flexmark.docx.converter.internal;

import org.docx4j.openpackaging.packages.WordprocessingMLPackage;

public class DocxWriter {
    private final WordprocessingMLPackage myPackage;
    private DocxRendererContext context;

    public DocxWriter(WordprocessingMLPackage out) {
        myPackage = out;
    }

    public void setContext(DocxRendererContext context) {
        this.context = context;
    }

    public DocxRendererContext getContext() {
        return context;
    }
}

