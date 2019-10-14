package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public abstract class ComboSpecTestCase extends FullSpecTestCase {
    public static final String SPEC_RESOURCE = "/ast_spec.md";

    protected final SpecExample example;

    //@Rule
    //public Timeout timeout = new Timeout(5, TimeUnit.SECONDS);
    //
    //@Rule
    //public Stopwatch stopwatch = new Stopwatch() {
    //    @Override
    //    protected void finished(long nanos, Description description) {
    //        System.err.println(description.getDisplayName() + " took " + (nanos / 1000000) + " ms");
    //    }
    //};

    /**
     * @return return resource name for the spec to use for the examples of the test
     */
    @NotNull
    public abstract String getSpecResourceName();

    public ComboSpecTestCase(SpecExample example) {
        this.example = example;
    }

    @NotNull
    final public SpecExample getExample() {
        return example;
    }

    //@Parameterized.Parameters(name = "{0}")
    //public static List<Object[]> data() {
    //    List<SpecExample> examples = SpecReader.readExamples();
    //    List<Object[]> data = new ArrayList<Object[]>();
    //
    //    // NULL example runs full spec test
    //    data.add(new Object[] { SpecExample.getNull() });
    //
    //    for (SpecExample example : examples) {
    //        data.add(new Object[] { example });
    //    }
    //    return data;
    //}

    public static List<Object[]> getTestData(String specResource) {
        return getTestData(specResource, null);
    }

    public static List<Object[]> getTestData(String specResource, String specUrlString) {
        List<SpecExample> examples = SpecReader.readExamples(specResource, specUrlString);
        List<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.getNull() });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    protected String readResource(String resourcePath) {
        InputStream stream = ComboSpecTestCase.class.getResourceAsStream(resourcePath);
        return readStream(stream);
    }

    protected String readStream(InputStream stream) {
        try {
            String line;
            StringBuilder out = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            while ((line = reader.readLine()) != null) {
                out.append(line).append('\n');
            }

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    /**
     */
    protected void fullTestSpecStarting() {

    }

    protected void fullTestSpecComplete() {

    }

    @Test
    public void testFullSpec() {
        if (!example.isFullSpecExample()) return;

        fullTestSpecStarting();

        String specResourcePath = getSpecResourceName();
        String fullSpec = SpecReader.readSpec(specResourcePath);
        SpecReader reader = example.getFileUrl() == null ? SpecReader.createAndReadExamples(specResourcePath, this)
                : SpecReader.createAndReadExamples(specResourcePath, this, example.getFileUrl().toString());
        String actual = dumpSpecReader.getFullSpec();

        fullTestSpecComplete();

        if (reader.getFileUrl() != null) {
            assertEquals(reader.getFileUrl().toString(), fullSpec, actual);
        } else {
            assertEquals(fullSpec, actual);
        }
    }
}
