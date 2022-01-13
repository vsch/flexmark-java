package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.docx.converter.internal.DocxLinkResolver;
import com.vladsch.flexmark.docx.converter.util.DocxContextImpl;
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
import com.vladsch.flexmark.ext.jekyll.tag.JekyllTagExtension;
import com.vladsch.flexmark.ext.macros.MacrosExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.html.renderer.AttributablePart;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.*;
import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.data.SharedDataKeys;
import com.vladsch.flexmark.util.html.Attributes;
import com.vladsch.flexmark.util.html.MutableAttributes;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static com.vladsch.flexmark.util.misc.Utils.*;

public abstract class ComboDocxConverterSpecTestBase extends ComboSpecTestCase {
    // RELEASE : change to true for release
    static final boolean SKIP_IGNORED_TESTS = true;
    static final boolean DUMP_TEST_CASE_FILES = !SKIP_IGNORED_TESTS;
    static final boolean DUMP_ALL_TESTS_FILES = !SKIP_IGNORED_TESTS;
    static final String PROJECT_ROOT_DIRECTORY = TestUtils.getRootDirectoryForModule(ComboDocxConverterSpecTestBase.class, "flexmark-docx-converter");
    static final String TEST_ROOT_DIRECTORY = TestUtils.getTestResourceRootDirectoryForModule(ComboDocxConverterSpecTestBase.class, "com.vladsch.flexmark.docx.converter");
    final public static String[] EMPTY_STRINGS = new String[0];

    // standard options
    final private static DataHolder OPTIONS = new MutableDataSet()
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
                    JekyllTagExtension.create(),
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
            .set(DocxRenderer.DOC_RELATIVE_URL, String.format("%s", PROJECT_ROOT_DIRECTORY))
            .set(DocxRenderer.DOC_ROOT_URL, String.format("%s/assets", PROJECT_ROOT_DIRECTORY))
            .set(SharedDataKeys.RUNNING_TESTS, SKIP_IGNORED_TESTS)
            .set(JekyllTagExtension.ENABLE_INLINE_TAGS, false)
            .set(JekyllTagExtension.LINK_RESOLVER_FACTORIES, Collections.singletonList(new DocxLinkResolver.Factory()))
            .set(JekyllTagExtension.EMBED_INCLUDED_CONTENT, true)
            .set(DocxRenderer.SUPPRESS_HTML, true);

    private static HashMap<String, DataHolder> optionsMap = new HashMap<>();
    static {
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
        optionsMap.put("form-controls-input", new MutableDataSet().set(DocxRenderer.FORM_CONTROLS, "input"));
        optionsMap.put("form-controls-form", new MutableDataSet().set(DocxRenderer.FORM_CONTROLS, "form"));
        optionsMap.put("page-a4", new MutableDataSet().set(DocxRenderer.PAGE_SIZE, "a4"));
        optionsMap.put("page-letter", new MutableDataSet().set(DocxRenderer.PAGE_SIZE, "letter"));
        optionsMap.put("page-landscape", new MutableDataSet().set(DocxRenderer.PAGE_LANDSCAPE, true));

        HashMap<String, String> props = new HashMap<>();
        props.put("File Name", "TestFile");
        props.put("Company", "Test Company Name");
        optionsMap.put("properties", new MutableDataSet().set(DocxRenderer.CUSTOM_PROPERTIES, props));
    }
    private static String removeFileUri(String uri) {
        if (uri.startsWith("file://")) return uri.substring("file://".length());
        if (uri.startsWith("file:/")) return uri.substring("file:/".length());
        return uri;
    }

    protected static DataHolder getDefaultOptions(ResourceLocation resourceLocation) {
        String fileUrl = resourceLocation.getFileDirectoryUrl();
        String filePath = removeFileUri(fileUrl);
        return new MutableDataSet().set(DocxRenderer.DOC_RELATIVE_URL, filePath);
    }

    protected static DataHolder getDefaultOptions(ResourceLocation resourceLocation, DataHolder options) {
        String fileUrl = resourceLocation.getFileDirectoryUrl();
        String filePath = removeFileUri(fileUrl);
        return new MutableDataSet(options).set(DocxRenderer.DOC_RELATIVE_URL, filePath);
    }

    public ComboDocxConverterSpecTestBase(@NotNull SpecExample example, @Nullable Map<String, ? extends DataHolder> optionMap, @Nullable DataHolder... defaultOptions) {
        super(example, optionsMaps(optionsMap, optionMap), dataHolders(OPTIONS, defaultOptions));
    }

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combineOptions = aggregate(myDefaultOptions, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combineOptions, Parser.builder(combineOptions).build(), DocxRenderer.builder(combineOptions).build(), true);
    }

    final public @NotNull String getFileTestCaseDumpLocation() {
        String specResource = removeSuffix(removePrefix(getSpecResourceLocation().getResourcePath(), '/'), '/');
        String testDir = suffixWith(removeSuffix(specResource, ".md"), '/');
        return TEST_ROOT_DIRECTORY + testDir;
    }

    final public @NotNull String getFileAllTestsDumpName() {
        return getFileTestCaseDumpLocation() + "AllTests";
    }

    @Override
    final public void addSpecExample(SpecExampleRenderer exampleRenderer, SpecExampleParse exampleParse, DataHolder exampleOptions) {
        if (!DUMP_TEST_CASE_FILES) return;
        Document document = (Document) ((FlexmarkSpecExampleRenderer) exampleRenderer).getDocument();

        SpecExample specExample = exampleRenderer.getExample();
        if (!specExample.isFullSpecExample() && specExample.getSection() != null && !specExample.getSection().isEmpty()) {
            // write it out to file, hard-coded for now                    IGNORE
            File file = new File(String.format("%s%s_%d.docx", getFileTestCaseDumpLocation(), specExample.getSection(), specExample.getExampleNumber()));
            File file2 = new File(String.format("%s%s_%d.xml", getFileTestCaseDumpLocation(), specExample.getSection(), specExample.getExampleNumber()));
            DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);
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
    final public void addFullSpecExample(@NotNull SpecExampleRenderer exampleRenderer, @NotNull SpecExampleParse exampleParse, DataHolder exampleOptions, boolean ignoredTestCase, @NotNull String html, @Nullable String ast) {
        if (!DUMP_ALL_TESTS_FILES) return;

        SpecExample example = exampleRenderer.getExample();
        boolean failed = !ignoredTestCase && !exampleRenderer.getHtml().equals(html);

        // add source information
        myDocxContext.createP(myDocxContext.getRenderingOptions().HEADING_3);

        if (ignoredTestCase) {
            // ignored
            myDocxContext.createColor().setVal("BB002F");
            myDocxContext.addBold();
            myDocxContext.addText("Ignored ");
        } else if (failed) {
            // does not match
            myDocxContext.createColor().setVal("BB002F");
            myDocxContext.addBold();
            myDocxContext.addText("Failed ");
        } else {
            myDocxContext.createColor().setVal("008000");
            myDocxContext.addBold();
            myDocxContext.addText("Passed ");
        }

        StringBuilder header = new StringBuilder();
        String section = example.getSection();

        header.append(section == null ? "" : section.trim()).append(": ").append(example.getExampleNumber());
        String optionsSet = example.getOptionsSet();
        myDocxContext.addText(header.toString());

        if (optionsSet != null) {
            myDocxContext.createR();
            myDocxContext.createHpsMeasure(28);
            myDocxContext.createColor().setVal("7B56A0");
            myDocxContext.addText((SpecReader.OPTIONS_STRING + "(") + optionsSet + ")");
        }

        myDocxContext.createHorizontalLine();
        DataHolder combinedOptions = aggregate(myDefaultOptions, exampleOptions);
        DocxRenderer.builder(combinedOptions).build().render(((FlexmarkSpecExampleRenderer) exampleRenderer).getDocument(), myPackage);
        myDocxContext.createHorizontalLine();

        if (example.hasComment()) {
            String comment = example.getComment();
            String[] lines = comment == null ? EMPTY_STRINGS : comment.split("\n\\s*\n");
            for (String line : lines) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    myDocxContext.createP(myDocxContext.getRenderingOptions().LOOSE_PARAGRAPH_STYLE);
                    myDocxContext.createColor().setVal("808080");
                    myDocxContext.addText(trimmed);
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
    final public void fullTestSpecStarting() {
        if (DUMP_ALL_TESTS_FILES) {
            myPackage = DocxRenderer.getDefaultTemplate(myDefaultOptions);
            if (myPackage == null) return;

            myDocxContext = new DocxContextImpl<Node>(myPackage, null) {
                @Override
                public MutableAttributes extendRenderingNodeAttributes(AttributablePart part, Attributes attributes) {
                    return null;
                }

                @Override
                public MutableAttributes extendRenderingNodeAttributes(Node node, AttributablePart part, Attributes attributes) {
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
    final protected void fullTestSpecComplete() {
        if (!DUMP_ALL_TESTS_FILES || myPackage == null) return;

        // write it out to file, hard-coded for now                    IGNORE
        File file = new File(String.format("%s.docx", getFileAllTestsDumpName()));
        File file2 = new File(String.format("%s.xml", getFileAllTestsDumpName()));
        WordprocessingMLPackage mlPackage = myPackage;

        File parentDir = file.getParentFile();
        if (!parentDir.exists()) {
            //noinspection ResultOfMethodCallIgnored
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
