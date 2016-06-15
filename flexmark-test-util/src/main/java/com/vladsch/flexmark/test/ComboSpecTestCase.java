package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public abstract class ComboSpecTestCase extends FullSpecTestCase {
    public static final String SPEC_RESOURCE = "/ast_spec.txt";

    private DumpSpecReader dumpSpecReader;
    protected final SpecExample example;

    /**
     * @return return resource name for the spec to use for the examples of the test
     */
    protected abstract String getSpecResourceName();

    public ComboSpecTestCase(SpecExample example) {
        this.example = example;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples();
        List<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    public SpecReader create(InputStream inputStream) {
        dumpSpecReader = new DumpSpecReader(inputStream, this);
        return dumpSpecReader;
    }

    /**
     * @return return true if actual html should be used in comparison, else only actual AST will be used in compared
     */
    protected boolean useActualHtml() {
        return true;
    }

    /**
     * @return return true if actual result spec used in comparison should be output to stdout
     */
    protected boolean outputActualFullSpec() {
        return false;
    }

    protected boolean includeExampleCoords() {
        return true;
    }

    @Test
    public void testFullSpec() throws Exception {
        if (!example.isFullSpecExample()) return;

        String specResourcePath = getSpecResourceName();
        SpecReader.readExamples(specResourcePath, this);
        String fullSpec = SpecReader.readSpec(specResourcePath);
        String actual = dumpSpecReader.getFullSpec();

        if (outputActualFullSpec()) {
            System.out.println(actual);
        }

        assertEquals(fullSpec, actual);
    }

    @Test
    public void testHtmlRendering() {
        if (!example.isSpecExample()) return;

        if (example.getAst() != null) {
            assertRenderingAst(example.getSource(), example.getHtml(), example.getAst(), example.getOptionsSet());
        } else {
            assertRendering(example.getSource(), example.getHtml(), example.getOptionsSet());
        }
    }
}
