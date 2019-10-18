package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboDeDocxConverterSpec2Test extends ComboDocxConverterSpecTestBase {
    private static final String SPEC_RESOURCE = "/docx_converter_de_ast_spec2.md";
    private static final String FILE_TEST_CASE_DUMP_LOCATION = "/flexmark-docx-converter/src/test/resources/docx_converter_de_ast_spec2/";
    private static final String TEMPLATE_XML = "/DE-Template.xml";

    public ComboDeDocxConverterSpec2Test(SpecExample example) {
        super(example, new MutableDataSet()
                .set(DocxRenderer.DEFAULT_TEMPLATE_RESOURCE, TEMPLATE_XML));
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @NotNull
    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @NotNull
    @Override
    public String getProjectRootDirectory() {
        return PROJECT_ROOT_DIRECTORY;
    }

    @NotNull
    @Override
    public String getFileTestCaseDumpLocation() {
        return FILE_TEST_CASE_DUMP_LOCATION;
    }
}
