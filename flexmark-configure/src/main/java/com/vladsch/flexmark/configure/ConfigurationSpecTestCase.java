package com.vladsch.flexmark.configure;

import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.test.DumpSpecReader;
import com.vladsch.flexmark.test.FullSpecTestCase;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.assertEquals;

public abstract class ConfigurationSpecTestCase extends FullSpecTestCase {
    public static final String SPEC_RESOURCE = "/configuration_spec.md";

    private DumpSpecReader dumpSpecReader;

    @Override
    public SpecReader create(InputStream inputStream) {
        dumpSpecReader = new DumpSpecReader(inputStream, this);
        return dumpSpecReader;
    }

    /**
     * @return  resource name for the spec to use for the examples of the test
     */
    protected abstract String getSpecResourceName();

    /**
     * @return  resource name for the rendered html to be matched to flexmark options
     */
    protected abstract String getHtmlResourceName();

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

