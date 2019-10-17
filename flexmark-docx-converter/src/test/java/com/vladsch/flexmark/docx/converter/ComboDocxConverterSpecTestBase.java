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
import com.vladsch.flexmark.test.*;
import com.vladsch.flexmark.util.ast.Document;
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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    static {
        // Set up a simple configuration that logs on the console.
        Logger root = Logger.getRootLogger();
        root.addAppender(new NullAppender());
    }

    protected final Map<String, DataHolder> optionsMap = new HashMap<>();
    private final DataHolder myDefaultOptions;

    public ComboDocxConverterSpecTestBase(SpecExample example, @Nullable DataHolder defaultOptions) {
        super(example);

        // standard options
        DataHolder OPTIONS = new MutableDataSet()
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

        myDefaultOptions = combineOptions(OPTIONS, defaultOptions);

        // add standard options
        optionsMap.put("IGNORED", new MutableDataSet().set(TestUtils.IGNORE, SKIP_IGNORED_TESTS));
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
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = combineOptions(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, Parser.builder(combineOptions).build(), DocxRenderer.builder(combineOptions).build(), true);
    }

    @Nullable
    @Override
    final public DataHolder options(String option) {
        if (option == null) return null;
        return optionsMap.get(option);
    }

    public abstract @NotNull String getProjectRootDirectory();
    public abstract @NotNull String getFileTestCaseDumpLocation();

    final public @NotNull String getFileAllTestsDumpName() {
        return getFileTestCaseDumpLocation() + "AllTests";
    }

    @Override
    public void testCase(SpecExampleRenderer exampleRenderer, SpecExampleParse exampleParse, DataHolder exampleOptions) {
        if (!DUMP_TEST_CASE_FILES) return;
        Document document = (Document) ((FlexmarkSpecExampleRenderer) exampleRenderer).getDocument();

        SpecExample specExample = getExample();
        if (!specExample.isFullSpecExample() && !specExample.getSection().isEmpty()) {
            // write it out to file, hard-coded for now                    IGNORE
            File file = new File(String.format("%s%s%s_%d.docx", getProjectRootDirectory(), getFileTestCaseDumpLocation(), specExample.getSection(), specExample.getExampleNumber()));
            File file2 = new File(String.format("%s%s%s_%d.xml", getProjectRootDirectory(), getFileTestCaseDumpLocation(), specExample.getSection(), specExample.getExampleNumber()));
            DataHolder combinedOptions = combineOptions(myDefaultOptions, exampleOptions);
            DocxRenderer withOptions = DocxRenderer.builder(combinedOptions).build();
            WordprocessingMLPackage mlPackage = DocxRenderer.getDefaultTemplate(withOptions.getOptions());
            withOptions.render(document, mlPackage);

            File parentDir = file.getParentFile();
            if (!parentDir.exists()) {
                //noinspection ResultOfMethodCallIgnored
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
                } catch (Docx4JException | IOException e) {
                    e.printStackTrace();
                }
            } catch (Docx4JException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void addSpecExample(@NotNull SpecExampleRenderer exampleRenderer, @NotNull SpecExampleParse exampleParse, DataHolder exampleOptions, boolean ignoredTestCase, @NotNull String html, @Nullable String ast) {
        if (!DUMP_ALL_TESTS_FILES) return;

        boolean failed = !ignoredTestCase && !exampleRenderer.renderHtml().equals(example.getHtml());

        // add source information
        myDocxContext.createP(myDocxContext.getRenderingOptions().HEADING_3);

        if (ignoredTestCase) {
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
        DataHolder combinedOptions = combineOptions(myDefaultOptions, exampleOptions);
        DocxRenderer.builder(combinedOptions).build().render(((FlexmarkSpecExampleRenderer)exampleRenderer).getDocument(), myPackage);
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
    public void fullTestSpecStarting() {
        if (DUMP_ALL_TESTS_FILES) {
            myPackage = DocxRenderer.getDefaultTemplate(myDefaultOptions);
            if (myPackage == null) return;

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
        }
    }

    @Override
    protected void fullTestSpecComplete() {
        if (!DUMP_ALL_TESTS_FILES || myPackage == null) return;

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
        } catch (Docx4JException | IOException e) {
            e.printStackTrace();
        }
    }
}
