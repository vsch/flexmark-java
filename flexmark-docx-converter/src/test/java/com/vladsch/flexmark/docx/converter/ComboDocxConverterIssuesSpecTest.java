package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.runners.Parameterized;

import java.util.List;

public class ComboDocxConverterIssuesSpecTest extends ComboDocxConverterSpecTestBase {
    private static final String SPEC_RESOURCE = "/docx_converter_issues_ast_spec.md";
    private static final String FILE_TEST_CASE_DUMP_LOCATION = "/flexmark-docx-converter/src/test/resources/docx_converter_issues_ast_spec/";

    private static final DataHolder OPTIONS = new MutableDataSet(ComboDocxConverterSpecTestBase.OPTIONS);

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    private static final DocxRenderer RENDERER = DocxRenderer.builder(OPTIONS).build();

    public ComboDocxConverterIssuesSpecTest(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return getTestData(SPEC_RESOURCE);
    }

    @Override
    public String getSpecResourceName() {
        return SPEC_RESOURCE;
    }

    @Override
    public Parser parser() {
        return PARSER;
    }

    @Override
    public DocxRenderer renderer() {
        return RENDERER;
    }

    @Override
    public String getProjectRootDirectory() {
        return PROJECT_ROOT_DIRECTORY;
    }

    @Override
    public String getFileTestCaseDumpLocation() {
        return FILE_TEST_CASE_DUMP_LOCATION;
    }
}
