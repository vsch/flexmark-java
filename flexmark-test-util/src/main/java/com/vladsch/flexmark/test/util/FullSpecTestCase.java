package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.ResourceLocation;
import com.vladsch.flexmark.test.spec.SpecReader;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class FullSpecTestCase extends RenderingTestCase implements SpecExampleProcessor {
    @NotNull
    public DumpSpecReader create(@NotNull ResourceLocation location) {
        return SpecReader.create(location, (stream, fileUrl) -> new DumpSpecReader(stream, this, fileUrl));
    }

    protected abstract @NotNull ResourceLocation getSpecResourceLocation();

    protected void fullTestSpecStarting() {

    }

    protected void fullTestSpecComplete() {

    }

    @Test
    public void testFullSpec() {

        fullTestSpecStarting();
        ResourceLocation location = getSpecResourceLocation();
        DumpSpecReader reader = create(location);
        reader.readExamples();
        fullTestSpecComplete();

        String actual = reader.getFullSpec();
        String expected = reader.getExpectedFullSpec();

//        // NOTE: reading the full spec does not work when examples are modified
//        String fullSpec = SpecReader.readSpec(location);
//        assertEquals(reader.getFileUrl(), expected, fullSpec);

        if (!reader.getFileUrl().isEmpty()) {
            assertEquals(reader.getFileUrl(), expected, actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
