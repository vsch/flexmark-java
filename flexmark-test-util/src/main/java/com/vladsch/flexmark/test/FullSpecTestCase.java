package com.vladsch.flexmark.test;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.SpecReaderFactory;
import com.vladsch.flexmark.util.options.DataHolder;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public abstract class FullSpecTestCase extends RenderingTestCase implements SpecReaderFactory {
    public static final String SPEC_RESOURCE = "/ast_spec.md";

    protected DumpSpecReader dumpSpecReader;

    @Override
    public SpecReader create(InputStream inputStream) {
        dumpSpecReader = new DumpSpecReader(inputStream, this);
        return dumpSpecReader;
    }

    /**
     * @return  return resource name for the spec to use for the examples of the test
     */
    public abstract String getSpecResourceName();

    public void addSpecExample(SpecExample example, Node node, DataHolder options, boolean ignoredCase, String html, String ast) {

    }

    /**
     * @return return true if actual html should be used in comparison, else only actual AST will be used in compared
     */
    public boolean useActualHtml() {
        return true;
    }

    /**
     * @return return true if actual result spec used in comparison should be output to stdout
     */
    public boolean outputActualFullSpec() {
        return false;
    }

    public boolean includeExampleCoords() {
        return true;
    }

    @Test
    public void testFullSpec() throws Exception {
        String specResourcePath = getSpecResourceName();
        SpecReader.readExamples(specResourcePath, this);
        String fullSpec = SpecReader.readSpec(specResourcePath);
        String actual = dumpSpecReader.getFullSpec();

        if (outputActualFullSpec()) {
            System.out.println(actual);
        }

        assertEquals(fullSpec, actual);
    }
}
