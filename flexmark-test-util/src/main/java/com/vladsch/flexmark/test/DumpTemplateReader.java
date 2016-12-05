package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.TemplateEntry;
import com.vladsch.flexmark.spec.TemplateReader;

import java.io.InputStream;

class DumpTemplateReader extends TemplateReader {
    private final StringBuilder sb = new StringBuilder();
    private final TemplateTestCase testCase;

    DumpTemplateReader(InputStream stream, TemplateTestCase testCase) {
        super(stream);
        this.testCase = testCase;
    }

    public String getTemplate() {
        return sb.toString();
    }

    @Override
    protected void addSpecLine(String line) {
        sb.append(line).append("\n");
    }

    @Override
    protected void addTemplateEntry(TemplateEntry entry) {
        testCase.getExpandedEntry(entry, sb);
    }
}
