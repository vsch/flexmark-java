package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.spec.SpecExample;
import com.vladsch.flexmark.test.spec.SpecReader;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public abstract class ComboSpecTestCase extends FullSpecTestCase {

    protected final @NotNull SpecExample example;

    @NotNull
    public abstract String getSpecResourceName();

    public ComboSpecTestCase(@NotNull SpecExample example) {
        this.example = example;
    }

    @NotNull
    final public SpecExample getExample() {
        return example;
    }

    @Test
    public void testHtmlRendering() {
        if (!example.isSpecExample()) return;

        if (example.getAst() != null) {
            assertRendering(example.getFileUrl(), example.getSource(), example.getHtml(), example.getAst(), example.getOptionsSet());
        } else {
            assertRendering(example.getFileUrl(), example.getSource(), example.getHtml(), example.getOptionsSet());
        }
    }

    protected void fullTestSpecStarting() {

    }

    protected void fullTestSpecComplete() {

    }

    @Test
    public void testFullSpec() {
        if (!example.isFullSpecExample()) return;

        fullTestSpecStarting();

        String specResourcePath = getSpecResourceName();
        String fullSpec = SpecReader.readSpec(this.getClass(), specResourcePath);
        SpecReader reader = SpecReader.createAndReadExamples(this.getClass(), specResourcePath, this, example.getFileUrl());
        String actual = dumpSpecReader.getFullSpec();

        fullTestSpecComplete();

        if (!reader.getFileUrl().isEmpty()) {
            assertEquals(reader.getFileUrl(), fullSpec, actual);
        } else {
            assertEquals(fullSpec, actual);
        }
    }

    protected static List<Object[]> getTestData(String specResource) {
        return TestUtils.getTestData(ComboSpecTestCase.class, specResource, TestUtils.DEFAULT_URL_PREFIX);
    }

    protected static List<Object[]> getTestData(Class<?> resourceClass, String specResource) {
        return TestUtils.getTestData(resourceClass, specResource, TestUtils.DEFAULT_URL_PREFIX);
    }
}
