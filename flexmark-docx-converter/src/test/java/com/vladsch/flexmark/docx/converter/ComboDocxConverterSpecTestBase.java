package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.docx.converter.util.DocxContextImpl;
import com.vladsch.flexmark.docx.converter.util.RunFormatProvider;
import com.vladsch.flexmark.docx.converter.util.XmlDocxSorter;
import com.vladsch.flexmark.ext.aside.AsideExtension;
import com.vladsch.flexmark.ext.attributes.AttributesExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.emoji.EmojiImageType;
import com.vladsch.flexmark.ext.emoji.EmojiShortcutType;
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
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.html.Attributes;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.Text;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class ComboDocxConverterSpecTestBase extends ComboSpecTestCase {
    // RELEASE : change to true for release
    static final boolean SKIP_IGNORED_TESTS = true;
    static final boolean DUMP_TEST_CASE_FILES = !SKIP_IGNORED_TESTS;
    static final boolean DUMP_ALL_TESTS_FILES = !SKIP_IGNORED_TESTS;
    static final String PROJECT_ROOT_DIRECTORY = "/Users/vlad/src/projects/flexmark-java";

    // standard options
    protected static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    AsideExtension.create(),
                    AttributesExtension.create(),
                    AutolinkExtension.create(),
                    DefinitionExtension.create(),
                    EmojiExtension.create(),
                    EnumeratedReferenceExtension.create(),
                    FootnoteExtension.create(),
                    GitLabExtension.create(),
                    InsExtension.create(),
                    MacrosExtension.create(),
                    SimTocExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    SuperscriptExtension.create(),
                    TablesExtension.create(),
                    TocExtension.create(),
                    WikiLinkExtension.create()
            ))
            .set(DocxRenderer.RENDER_BODY_ONLY, true)
            .set(DocxRenderer.DOC_RELATIVE_URL, String.format("file://%s", PROJECT_ROOT_DIRECTORY))
            .set(DocxRenderer.DOC_ROOT_URL, String.format("file://%s/assets", PROJECT_ROOT_DIRECTORY))
            .set(DocxRenderer.SUPPRESS_HTML, true);
    static {
        // Set up a simple configuration that logs on the console.
        Logger root = Logger.getRootLogger();
        root.addAppender(new NullAppender());
    }

    protected static void deleteTestCaseDumpFiles(String path) {
        File dir = new File(path);
        for (File file : dir.listFiles()) {
            file.delete();
        }
    }

    protected final Map<String, DataHolder> optionsMap = new HashMap<String, DataHolder>();

    public ComboDocxConverterSpecTestBase(SpecExample example) {
        super(example);

        // add standard options
        optionsMap.put("IGNORED", new MutableDataSet().set(IGNORE, SKIP_IGNORED_TESTS));
        optionsMap.put("caption-before", new MutableDataSet().set(DocxRenderer.TABLE_CAPTION_BEFORE_TABLE, true));
        optionsMap.put("emoji-github", new MutableDataSet().set(EmojiExtension.USE_SHORTCUT_TYPE, EmojiShortcutType.ANY_GITHUB_PREFERRED));
        optionsMap.put("emoji-unicode", new MutableDataSet().set(EmojiExtension.USE_IMAGE_TYPE, EmojiImageType.UNICODE_FALLBACK_TO_IMAGE));
        optionsMap.put("full-render", new MutableDataSet().set(DocxRenderer.RENDER_BODY_ONLY, false));
        optionsMap.put("highlight-code", new MutableDataSet().set(DocxRenderer.CODE_HIGHLIGHT_SHADING, "yellow"));
        optionsMap.put("highlight-shade", new MutableDataSet().set(DocxRenderer.CODE_HIGHLIGHT_SHADING, "shade"));
        optionsMap.put("hyperlink-suffix", new MutableDataSet().set(DocxRenderer.LOCAL_HYPERLINK_SUFFIX, "_1"));
        optionsMap.put("no-missing-hyperlink", new MutableDataSet().set(DocxRenderer.LOCAL_HYPERLINK_MISSING_HIGHLIGHT, ""));
        optionsMap.put("no-www-prefix", new MutableDataSet().set(DocxRenderer.PREFIX_WWW_LINKS, false));
        optionsMap.put("table-no-span", new MutableDataSet().set(TablesExtension.COLUMN_SPANS, false));
        optionsMap.put("url", new MutableDataSet().set(DocxRenderer.DOC_RELATIVE_URL, String.format("file://%s", PROJECT_ROOT_DIRECTORY)));
        optionsMap.put("yellow-missing-hyperlink", new MutableDataSet().set(DocxRenderer.LOCAL_HYPERLINK_MISSING_HIGHLIGHT, "yellow"));
    }

    @Override
    public abstract DocxRenderer renderer();

    @Override
    final public DataHolder options(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    // start of base
    public boolean isDumpTestCaseFiles() {
        return DUMP_TEST_CASE_FILES;
    }

    public boolean isDumpAllTestCaseFiles() {
        return DUMP_ALL_TESTS_FILES;
    }

    public abstract String getProjectRootDirectory();
    public abstract String getFileTestCaseDumpLocation();

    final public String getFileAllTestsDumpName() {
        return getFileTestCaseDumpLocation() + "AllTests";
    }

    @Override
    protected void testCase(Node node, DataHolder options) {
        if (!isDumpTestCaseFiles()) return;

        SpecExample specExample = example();
        if (!specExample.isFullSpecExample() && !specExample.getSection().isEmpty()) {
            // write it out to file, hard-coded for now                    IGNORE
            File file = new File(String.format("%s%s%s_%d.docx", getProjectRootDirectory(), getFileTestCaseDumpLocation(), specExample.getSection(), specExample.getExampleNumber()));
            File file2 = new File(String.format("%s%s%s_%d.xml", getProjectRootDirectory(), getFileTestCaseDumpLocation(), specExample.getSection(), specExample.getExampleNumber()));
            DocxRenderer withOptions = renderer().withOptions(options);
            WordprocessingMLPackage mlPackage = DocxRenderer.getDefaultTemplate(withOptions.getOptions());
            withOptions.render(node, mlPackage);

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try {
                mlPackage.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    mlPackage.save(outputStream, Docx4J.FLAG_SAVE_FLAT_XML);
                    String xml = outputStream.toString("UTF-8");
                    String s = XmlDocxSorter.sortDocumentParts(xml);
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
        if (!isDumpAllTestCaseFiles()) return;

        boolean failed = !ignoredCase && !actualHTML.equals(example.getHtml());

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
        String section = example.getSection();

        header.append(section == null ? "" : section.trim()).append(": ").append(example.getExampleNumber());
        String optionsSet = example.getOptionsSet();
        myDocxContext.text(header.toString());

        if (optionsSet != null) {
            myDocxContext.createHpsMeasure(28);
            myDocxContext.createColor().setVal("7B56A0");
            Text text = myDocxContext.addWrappedText();
            text.setValue((SpecReader.OPTIONS_STRING + "(") + optionsSet + ")");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        }

        myDocxContext.createHorizontalLine();
        renderer().withOptions(options).render(node, myPackage);
        myDocxContext.createHorizontalLine();

        if (example.hasComment()) {
            String comment = example.getComment();
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

    protected DocxContextImpl<Node> myDocxContext;
    protected WordprocessingMLPackage myPackage;
    protected String myVisibleLineBreak;

    @Override
    public boolean fullTestSpecStarting() {
        if (!isDumpAllTestCaseFiles()) return true;

        myPackage = DocxRenderer.getDefaultTemplate(renderer().getOptions());
        if (myPackage == null) return true;

        myDocxContext = new DocxContextImpl<Node>(myPackage, null) {
            @Override
            public Attributes extendRenderingNodeAttributes(AttributablePart part, Attributes attributes) {
                return null;
            }

            @Override
            public Attributes extendRenderingNodeAttributes(Node node, AttributablePart part, Attributes attributes) {
                return null;
            }

            @Override
            public String getNodeId(Node node) {
                return null;
            }

            @Override
            public String getValidBookmarkName(String id) {
                return null;
            }

            @Override
            public Node getNodeFromId(String nodeId) {
                return null;
            }
        };
        myVisibleLineBreak = "¶";
        return true;
    }

    @Override
    protected void fullTestSpecComplete() {
        if (!isDumpAllTestCaseFiles() || myPackage == null) return;

        // write it out to file, hard-coded for now                    IGNORE
        File file = new File(String.format("%s%s.docx", getProjectRootDirectory(), getFileAllTestsDumpName()));
        File file2 = new File(String.format("%s%s.xml", getProjectRootDirectory(), getFileAllTestsDumpName()));
        WordprocessingMLPackage mlPackage = myPackage;

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            parentDir.mkdirs();
        }

        try {
            mlPackage.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            mlPackage.save(outputStream, Docx4J.FLAG_SAVE_FLAT_XML);
            String xml = outputStream.toString("UTF-8");
            String s = XmlDocxSorter.sortDocumentParts(xml);
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
