package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.SpecReaderFactory;
import com.vladsch.flexmark.spec.UrlString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public abstract class FullSpecTestCase extends RenderingTestCase implements SpecExampleProcessor, SpecReaderFactory {
    public static final String SPEC_RESOURCE = "/ast_spec.md";

    protected DumpSpecReader dumpSpecReader;

    @NotNull
    @Override
    public SpecReader create(@NotNull InputStream inputStream, @Nullable final String fileUrl) {
        dumpSpecReader = new DumpSpecReader(inputStream, this, fileUrl);
        return dumpSpecReader;
    }

    public SpecReader create(InputStream inputStream) {
        return create(inputStream, new UrlString(SpecReader.getSpecInputFileUrl(this.getSpecResourceName())).toString());
    }

    @NotNull
    public abstract String getSpecResourceName();

    @Test
    public void testFullSpec() throws Exception {
        String specResourcePath = getSpecResourceName();
        SpecReader reader = dumpSpecReader == null || dumpSpecReader.getFileUrl() == null ? SpecReader.createAndReadExamples(specResourcePath, this)
                : SpecReader.createAndReadExamples(specResourcePath, this, dumpSpecReader.getFileUrl().toString());
        String fullSpec = SpecReader.readSpec(specResourcePath);
        String actual = dumpSpecReader.getFullSpec();

        if (reader.getFileUrl() != null) {
            assertEquals(reader.getFileUrl().toString(), fullSpec, actual);
        } else {
            assertEquals(fullSpec, actual);
        }
    }
}
