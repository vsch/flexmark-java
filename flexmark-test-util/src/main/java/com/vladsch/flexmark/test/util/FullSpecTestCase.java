package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.SpecReader;
import com.vladsch.flexmark.test.spec.SpecReaderFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public abstract class FullSpecTestCase extends RenderingTestCase implements SpecExampleProcessor, SpecReaderFactory {
    protected DumpSpecReader dumpSpecReader;

    @NotNull
    @Override
    public SpecReader create(@NotNull InputStream inputStream, @Nullable final String fileUrl) {
        dumpSpecReader = new DumpSpecReader(inputStream, this, fileUrl);
        return dumpSpecReader;
    }

    @NotNull
    public abstract String getSpecResourceName();

    @Test
    public void testFullSpec() {
        String specResourcePath = getSpecResourceName();
        SpecReader reader = SpecReader.createAndReadExamples(this.getClass(), specResourcePath, this, dumpSpecReader == null ? "" : dumpSpecReader.getFileUrl());
        String fullSpec = SpecReader.readSpec(this.getClass(), specResourcePath);
        String actual = dumpSpecReader.getFullSpec();

        if (!reader.getFileUrl().isEmpty()) {
            assertEquals(reader.getFileUrl(), fullSpec, actual);
        } else {
            assertEquals(fullSpec, actual);
        }
    }
}
