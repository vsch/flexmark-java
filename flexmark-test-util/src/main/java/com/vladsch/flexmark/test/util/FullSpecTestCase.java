package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public abstract class FullSpecTestCase extends RenderingTestCase implements SpecExampleProcessor {
    @NotNull
    public DumpSpecReader create(@NotNull ResourceLocation location) {
        return SpecReader.create(location, (stream, fileUrl) -> new DumpSpecReader(stream, this, fileUrl, compoundSections()));
    }

    protected boolean compoundSections() {
        return false;
    }

    abstract protected @NotNull ResourceLocation getSpecResourceLocation();

    protected void fullTestSpecStarting() {

    }

    protected void fullTestSpecComplete() {

    }

    @Test
    public void testSpecExample() {
        ResourceLocation location = getSpecResourceLocation();
        if (location.isNull()) return;

        fullTestSpecStarting();
        DumpSpecReader reader = create(location);
        reader.readExamples();
        fullTestSpecComplete();

        String actual = reader.getFullSpec();
        String expected = reader.getExpectedFullSpec();

//        // NOTE: reading the full spec does not work when examples are modified by checkExample()
//        String fullSpec = SpecReader.readSpec(location);
//        assertEquals(reader.getFileUrl(), expected, fullSpec);

        if (!reader.getFileUrl().isEmpty()) {
            assertEquals(reader.getFileUrl(), expected, actual);
        } else {
            assertEquals(expected, actual);
        }
    }
}
