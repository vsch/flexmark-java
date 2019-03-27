package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.docx.converter.util.DocxContextImpl;
import com.vladsch.flexmark.docx.converter.util.RunFormatProvider;
import com.vladsch.flexmark.docx.converter.util.XmlDocxSorter;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.enumerated.reference.EnumeratedReferenceExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gitlab.GitLabExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Text;
import org.junit.runners.Parameterized;

import java.io.*;
import java.util.*;

public class ComboEnDocxConverterSpec2Test extends ComboSpecTestCase {
    // set to false to dump DOCX and XML files to pre-determined location
    // RELEASE : change to true for release
    static final boolean SKIP_IGNORED_TESTS = ComboDocxConverterSpecTest.SKIP_IGNORED_TESTS;
    private static final boolean DUMP_TEST_CASE_FILES = !SKIP_IGNORED_TESTS;
    private static final boolean DUMP_ALL_TESTS_FILES = !SKIP_IGNORED_TESTS;
    private static final String PROJECT_ROOT_DIRECTORY = ComboDocxConverterSpecTest.PROJECT_ROOT_DIRECTORY;
    private static final String FILE_TEST_CASE_DUMP_LOCATION = "/flexmark-docx-converter/src/test/resources/docx_converter_en_ast_spec/";
    private static final String FILE_ALL_TESTS_DUMP_NAME = FILE_TEST_CASE_DUMP_LOCATION + "AllTests2";

    private static final String SPEC_RESOURCE = "/docx_converter_ast_spec2.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AsideExtension.create(),
                    AttributesExtension.create(),
                    DefinitionExtension.create(),
                    EmojiExtension.create(),
                    EnumeratedReferenceExtension.create(),
                    FootnoteExtension.create(),
                    GitLabExtension.create(),
                    InsExtension.create(),
                    MacrosExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    SuperscriptExtension.create(),
                    TablesExtension.create(),
                    TocExtension.create(),
                    SimTocExtension.create(),
                    WikiLinkExtension.create()
            ))
            .set(DocxRenderer.RENDER_BODY_ONLY, true)
            .set(DocxRenderer.DOC_RELATIVE_URL, String.format("file://%s", PROJECT_ROOT_DIRECTORY))
            .set(DocxRenderer.DOC_ROOT_URL, String.format("file://%s/assets", PROJECT_ROOT_DIRECTORY))
            .set(DocxRenderer.SUPPRESS_HTML, true)
            //.set(HtmlRenderer.PERCENT_ENCODE_URLS, true)
            ;

    private static final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();
    //private static WordprocessingMLPackage ourPackage = new WordprocessingMLPackage();;

    private static final Parser PARSER = Parser.builder(OPTIONS).build();
    // The spec says URL-escaping is optional, but the examples assume that it's enabled.
    private static final DocxRenderer RENDERER = DocxRenderer.builder(OPTIONS).build();
    private static final String TEMPLATE_XML = "/EN-Template.xml";

    static {
        //optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        //optionsMap.put("option1", new MutableDataSet().set(DocxConverterExtension.DOCX_CONVERTER_OPTION1, true));
        optionsMap.put("IGNORED", new MutableDataSet().set(IGNORE, SKIP_IGNORED_TESTS));
        optionsMap.put("url", new MutableDataSet().set(DocxRenderer.DOC_RELATIVE_URL, String.format("file://%s", PROJECT_ROOT_DIRECTORY)));
        optionsMap.put("caption-before", new MutableDataSet().set(DocxRenderer.TABLE_CAPTION_BEFORE_TABLE, true));
        optionsMap.put("highlight-code", new MutableDataSet().set(DocxRenderer.CODE_HIGHLIGHT_SHADING, "yellow"));
        optionsMap.put("highlight-shade", new MutableDataSet().set(DocxRenderer.CODE_HIGHLIGHT_SHADING, "shade"));
        optionsMap.put("hyperlink-suffix", new MutableDataSet().set(DocxRenderer.LOCAL_HYPERLINK_SUFFIX, "_1"));
        optionsMap.put("yellow-missing-hyperlink", new MutableDataSet().set(DocxRenderer.LOCAL_HYPERLINK_MISSING_HIGHLIGHT, ""));
        optionsMap.put("table-no-span", new MutableDataSet().set(TablesExtension.COLUMN_SPANS, false));
        //optionsMap.put("heading-id-suffix", new MutableDataSet().set(DocxRenderer.FIRST_HEADING_ID_SUFFIX, "_1"));

        // Set up a simple configuration that logs on the console.
        //BasicConfigurator.configure();
        Logger root = Logger.getRootLogger();
        root.addAppender(new NullAppender());
    }
    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboEnDocxConverterSpec2Test(SpecExample example) {
        super(example);
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<SpecExample> examples = SpecReader.readExamples(SPEC_RESOURCE);
        List<Object[]> data = new ArrayList<Object[]>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Override
    public DataHolder options(String optionSet) {
        return optionsSet(optionSet);
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
    public IRender renderer() {
        return RENDERER;
    }

    @Override
    protected void testCase(final Node node, final DataHolder options) {
        if (!DUMP_TEST_CASE_FILES) return;

        final SpecExample specExample = example();
        if (!specExample.isFullSpecExample() && !specExample.getSection().isEmpty()) {
            // write it out to file, hard-coded for now                    IGNORE
            File file = new File(String.format("%s%s%s_%d.docx", PROJECT_ROOT_DIRECTORY, FILE_TEST_CASE_DUMP_LOCATION, specExample.getSection(), specExample.getExampleNumber()));
            File file2 = new File(String.format("%s%s%s_%d.xml", PROJECT_ROOT_DIRECTORY, FILE_TEST_CASE_DUMP_LOCATION, specExample.getSection(), specExample.getExampleNumber()));
            WordprocessingMLPackage mlPackage = DocxRenderer.getDefaultTemplate(TEMPLATE_XML);
            RENDERER.withOptions(options).render(node, mlPackage);

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try {
                mlPackage.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    mlPackage.save(outputStream, Docx4J.FLAG_SAVE_FLAT_XML);
                    final String xml = outputStream.toString("UTF-8");
                    final String s = XmlDocxSorter.sortDocumentParts(xml);
                    FileWriter fileWriter = new FileWriter(file2);
                    fileWriter.append(s);
                    fileWriter.append('\n');
                    fileWriter.close();
                } catch (Docx4JException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addSpecExample(SpecExample example, Node node, DataHolder options, boolean ignoredCase, String actualHTML, String actualAST) {
        if (!DUMP_ALL_TESTS_FILES) return;

        final boolean failed = !ignoredCase && !actualHTML.equals(example.getHtml());

        // add source information
        myDocxContext.createP(myDocxContext.getRenderingOptions().HEADING_3);

        if (ignoredCase) {
            // does not match, need more stuff

            myDocxContext.createColor().setVal("BB002F");
            myDocxContext.addBold();
            Text text = myDocxContext.addWrappedText();
            text.setValue("Ignored ");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        } else if (failed) {
            // does not match, need more stuff
            myDocxContext.createColor().setVal("BB002F");
            myDocxContext.addBold();
            Text text = myDocxContext.addWrappedText();
            text.setValue("Failed ");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        } else {
            // does not match, need more stuff
            myDocxContext.createColor().setVal("008000");
            myDocxContext.addBold();
            Text text = myDocxContext.addWrappedText();
            text.setValue("Passed ");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        }

        StringBuilder header = new StringBuilder();
        final String section = example.getSection();

        header.append(section == null ? "" : section.trim()).append(": ").append(example.getExampleNumber());
        final String optionsSet = example.getOptionsSet();
        myDocxContext.text(header.toString());

        if (optionsSet != null) {
            myDocxContext.createHpsMeasure(28);
            myDocxContext.createColor().setVal("7B56A0");
            Text text = myDocxContext.addWrappedText();
            text.setValue((SpecReader.OPTIONS_STRING + "(") + optionsSet + ")");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        }

        myDocxContext.createHorizontalLine();
        RENDERER.withOptions(options).render(node, myPackage);
        myDocxContext.createHorizontalLine();

        if (example.hasComment()) {
            final String comment = example.getComment();
            String[] lines = comment.split("\n\\s*\n");
            for (String line : lines) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    myDocxContext.createP(myDocxContext.getRenderingOptions().LOOSE_PARAGRAPH_STYLE);
                    myDocxContext.createColor().setVal("808080");
                    Text text = myDocxContext.addWrappedText();
                    text.setValue(trimmed);
                }
            }
        }

        myDocxContext.renderFencedCodeLines(example.getSource().split("\n"));

        //noinspection StatementWithEmptyBody
        if (failed) {
            // could add expected and actual text but that would be too much
        }
    }

    private DocxContextImpl<Node> myDocxContext;
    private WordprocessingMLPackage myPackage;
    private String myVisibleLineBreak;

    @Override
    public boolean fullTestSpecStarting() {
        if (!DUMP_ALL_TESTS_FILES) return true;

        myPackage = DocxRenderer.getDefaultTemplate(TEMPLATE_XML);
        if (myPackage == null) return true;

        myDocxContext = new DocxContextImpl<Node>(myPackage, null) {
            @Override
            public Attributes extendRenderingNodeAttributes(final AttributablePart part, final Attributes attributes) {
                return null;
            }

            @Override
            public Attributes extendRenderingNodeAttributes(final Node node, final AttributablePart part, final Attributes attributes) {
                return null;
            }

            @Override
            public String getNodeId(final Node node) {
                return null;
            }

            @Override
            public String getValidBookmarkName(final String id) {
                return null;
            }

            @Override
            public Node getNodeFromId(final String nodeId) {
                return null;
            }
        };
        myVisibleLineBreak = "¶";
        return true;
    }

    @Override
    protected void fullTestSpecComplete() {
        if (!DUMP_ALL_TESTS_FILES || myPackage == null) return;

        // write it out to file, hard-coded for now                    IGNORE
        File file = new File(String.format("%s%s.docx", PROJECT_ROOT_DIRECTORY, FILE_ALL_TESTS_DUMP_NAME));
        File file2 = new File(String.format("%s%s.xml", PROJECT_ROOT_DIRECTORY, FILE_ALL_TESTS_DUMP_NAME));
        WordprocessingMLPackage mlPackage = myPackage;

        try {
            mlPackage.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mlPackage.save(outputStream, Docx4J.FLAG_SAVE_FLAT_XML);
            final String xml = outputStream.toString("UTF-8");
            final String s = XmlDocxSorter.sortDocumentParts(xml);
            FileWriter fileWriter = new FileWriter(file2);
            fileWriter.append(s);
            fileWriter.append('\n');
            fileWriter.close();
        } catch (Docx4JException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
