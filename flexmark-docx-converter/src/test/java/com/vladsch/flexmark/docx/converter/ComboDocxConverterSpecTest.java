package com.vladsch.flexmark.docx.converter;

import com.vladsch.flexmark.IRender;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.docx.converter.internal.DocxHelper;
import com.vladsch.flexmark.docx.converter.internal.DocxRenderer;
import com.vladsch.flexmark.docx.converter.internal.XmlFormatter;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.test.ComboSpecTestCase;
import com.vladsch.flexmark.util.Utils;
import com.vladsch.flexmark.util.ValueRunnable;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.apache.log4j.Logger;
import org.apache.log4j.varia.NullAppender;
import org.docx4j.Docx4J;
import org.docx4j.openpackaging.exceptions.Docx4JException;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.openpackaging.parts.WordprocessingML.MainDocumentPart;
import org.docx4j.wml.*;
import org.junit.runners.Parameterized;

import javax.xml.bind.JAXBElement;
import java.io.*;
import java.math.BigInteger;
import java.util.*;

import static com.vladsch.flexmark.docx.converter.BlockFormatProvider.HORIZONTAL_LINE_STYLE;
import static com.vladsch.flexmark.docx.converter.BlockFormatProvider.PREFORMATTED_TEXT_STYLE;

public class ComboDocxConverterSpecTest extends ComboSpecTestCase {
    // set to true to dump DOCX and XML files to pre-dermined location
    private static final boolean DUMP_TEST_CASE_FILES = true;
    private static final boolean DUMP_ALL_TESTS_FILES = true;
    private static final String PROJECT_ROOT_DIRECTORY = "/Users/vlad/src/flexmark-java";
    private static final String FILE_TEST_CASE_DUMP_LOCATION = "/flexmark-docx-converter/src/test/resources/docx_converter_ast_spec/";
    private static final String FILE_ALL_TESTS_DUMP_NAME = "/flexmark-docx-converter/src/test/resources/docx_converter_ast_spec/AllTests";

    private static final String SPEC_RESOURCE = "/docx_converter_ast_spec.md";
    private static final DataHolder OPTIONS = new MutableDataSet()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    DefinitionExtension.create(),
                    EmojiExtension.create(),
                    FootnoteExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    InsExtension.create(),
                    SuperscriptExtension.create(),
                    TablesExtension.create(),
                    TocExtension.create(),
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
    static {
        //optionsMap.put("src-pos", new MutableDataSet().set(HtmlRenderer.SOURCE_POSITION_ATTRIBUTE, "md-pos"));
        //optionsMap.put("option1", new MutableDataSet().set(DocxConverterExtension.DOCX_CONVERTER_OPTION1, true));
        optionsMap.put("IGNORES", new MutableDataSet().set(IGNORE, false));
        optionsMap.put("url", new MutableDataSet().set(DocxRenderer.DOC_RELATIVE_URL, String.format("file://%s", PROJECT_ROOT_DIRECTORY)));
        optionsMap.put("caption-before", new MutableDataSet().set(DocxRenderer.TABLE_CAPTION_BEFORE_TABLE, true));

        // Set up a simple configuration that logs on the console.
        //BasicConfigurator.configure();
        Logger root = Logger.getRootLogger();
        root.addAppender(new NullAppender());
    }
    private static DataHolder optionsSet(String optionSet) {
        if (optionSet == null) return null;
        return optionsMap.get(optionSet);
    }

    public ComboDocxConverterSpecTest(SpecExample example) {
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
            WordprocessingMLPackage mlPackage = DocxRenderer.getDefaultTemplate();
            RENDERER.withOptions(options).render(node, mlPackage);

            try {
                mlPackage.save(file, Docx4J.FLAG_SAVE_ZIP_FILE);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                try {
                    mlPackage.save(outputStream, Docx4J.FLAG_SAVE_FLAT_XML);
                    final String xml = outputStream.toString("UTF-8");
                    final String s = XmlFormatter.format(xml);
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

        // add source information
        myPrValueRunnable = mySectionPPr;
        createP();

        final boolean failed = !ignoredCase && !actualHTML.equals(example.getHtml());

        if (ignoredCase) {
            // does not match, need more stuff
            R r = createR();
            RPr rPr = myFactory.createRPr();
            r.setRPr(rPr);

            Color color = myFactory.createColor();
            color.setVal("BB002F");
            rPr.setColor(color);
            rPr.setB(myFactory.createBooleanDefaultTrue());
            rPr.setBCs(myFactory.createBooleanDefaultTrue());

            Text text = addWrappedText(r);
            text.setValue("Ignored ");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        } else if (failed) {
            // does not match, need more stuff
            R r = createR();
            RPr rPr = myFactory.createRPr();
            r.setRPr(rPr);

            Color color = myFactory.createColor();
            color.setVal("BB002F");
            rPr.setColor(color);
            rPr.setB(myFactory.createBooleanDefaultTrue());
            rPr.setBCs(myFactory.createBooleanDefaultTrue());

            Text text = addWrappedText(r);
            text.setValue("Failed ");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        } else {
            // does not match, need more stuff
            R r = createR();
            RPr rPr = myFactory.createRPr();
            r.setRPr(rPr);

            Color color = myFactory.createColor();
            color.setVal("008000");
            rPr.setColor(color);
            rPr.setB(myFactory.createBooleanDefaultTrue());
            rPr.setBCs(myFactory.createBooleanDefaultTrue());

            Text text = addWrappedText(r);
            text.setValue("Passed ");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        }

        StringBuilder header = new StringBuilder();
        final String section = example.getSection();

        header.append(section == null ? "" : section.trim()).append(": ").append(example.getExampleNumber());
        final String optionsSet = example.getOptionsSet();
        text(header.toString());

        if (optionsSet != null) {
            R r = createR();
            RPr rPr = myFactory.createRPr();
            r.setRPr(rPr);

            // Create object for sz
            HpsMeasure hpsmeasure = myFactory.createHpsMeasure();
            rPr.setSz(hpsmeasure);
            hpsmeasure.setVal(BigInteger.valueOf(28));

            Color color = myFactory.createColor();
            //color.setVal("7500C5");
            color.setVal("7B56A0");
            rPr.setColor(color);

            Text text = addWrappedText(r);
            text.setValue((SpecReader.OPTIONS_STRING + "(") + optionsSet + ")");
            text.setSpace(RunFormatProvider.SPACE_PRESERVE);
        }

        P p = createP(HORIZONTAL_LINE_STYLE);
        R r = createR();
        RENDERER.withOptions(options).render(node, myPackage);
        p = createP(HORIZONTAL_LINE_STYLE);
        r = createR();

        if (example.hasComment()) {
            final String comment = example.getComment();
            String[] lines = comment.split("\n\\s*\n");
            for (String line : lines) {
                String trimmed = line.trim();
                if (!trimmed.isEmpty()) {
                    p = createP(BlockFormatProvider.LOOSE_PARAGRAPH_STYLE);
                    r = createR();
                    RPr rPr = myFactory.createRPr();
                    r.setRPr(rPr);

                    Color color = myFactory.createColor();
                    //color.setVal("7500C5");
                    color.setVal("808080");
                    rPr.setColor(color);

                    Text text = addWrappedText(r);
                    text.setValue(trimmed);
                }
            }
        }

        myPrValueRunnable = myFencedCodePPr;
        renderCodeLines(example.getSource());
        if (failed) {
            // add expected and actual text but that would be too much
        }
        myPrValueRunnable = null;
    }

    public void addLineBreak() {
        addBreak(null);
    }

    public void addVisibleLineBreak() {
        addLineBreak();
        //R r = createR();
        //RPr rPr = myFactory.createRPr();
        //r.setRPr(rPr);
        //
        //// Create object for sz
        //HpsMeasure hpsmeasure = myFactory.createHpsMeasure();
        //rPr.setSz(hpsmeasure);
        //hpsmeasure.setVal(BigInteger.valueOf(12));
        //
        //// Create object for position
        //CTSignedHpsMeasure signedhpsmeasure = myFactory.createCTSignedHpsMeasure();
        //rPr.setPosition(signedhpsmeasure);
        //signedhpsmeasure.setVal(BigInteger.valueOf(6));
        //
        //Color color = myFactory.createColor();
        //color.setVal("0366D6");
        //rPr.setColor(color);
        //
        //Text text = addWrappedText(r);
        //text.setValue(myVisibleLineBreak);
        //addBreak(null);
    }

    public void addPageBreak() {
        addBreak(STBrType.PAGE);
    }

    public void addBreak(STBrType breakType) {
        // Create object for br
        R r = getR();
        Br br = myFactory.createBr();
        if (breakType != null) br.setType(breakType);
        r.getContent().add(br);
    }

    private void renderCodeLines(CharSequence source) {
        addBlankLine(myBefore, BlockFormatProvider.DEFAULT_STYLE);

        BasedSequence basedSequence = BasedSequenceImpl.of(source);
        BasedSequence[] lines = basedSequence.split("\n");
        int[] leadColumns = new int[lines.length];
        int minSpaces = Integer.MAX_VALUE;
        int i = 0;
        for (BasedSequence line : lines) {
            leadColumns[i] = line.countLeadingColumns(0, " \t");
            minSpaces = Utils.min(minSpaces, leadColumns[i]);
            i++;
        }

        createP();

        ArrayList<BasedSequence> trimmedLines = new ArrayList<BasedSequence>();
        i = 0;
        for (BasedSequence line : lines) {
            StringBuilder sb = new StringBuilder();

            int spaces = leadColumns[i] - minSpaces;
            while (spaces-- > 0) sb.append(' ');
            final BasedSequence trimmed = line.trim();
            sb.append(trimmed);

            // Create object for p

            text(sb.toString());
            if (trimmed.isEmpty()) {
                addLineBreak();
            } else {
                addVisibleLineBreak();
            }

            i++;
        }

        addBlankLine(myAfter, BlockFormatProvider.DEFAULT_STYLE);
    }

    public org.docx4j.wml.Text addWrappedText(final R r) {
        // Create object for t (wrapped in JAXBElement)
        org.docx4j.wml.Text text = myFactory.createText();
        JAXBElement<Text> textWrapped = myFactory.createRT(text);
        r.getContent().add(textWrapped);
        return text;
    }

    public org.docx4j.wml.Text text(final String text) {
        R r = createR();
        org.docx4j.wml.Text textElem = addWrappedText(r);
        textElem.setValue(text);
        textElem.setSpace(RunFormatProvider.SPACE_PRESERVE);
        return textElem;
    }

    public void createPStyle(PPr pPr, String style) {
        // Create object for pStyle if one does not already exist
        PPrBase.PStyle basePStyle = myFactory.createPPrBasePStyle();
        pPr.setPStyle(basePStyle);
        basePStyle.setVal(style);
    }

    public P createP() {
        return createP(null);
    }

    public P createP(String style) {
        P p = myFactory.createP();
        PPr pPr = myFactory.createPPr();

        p.setPPr(pPr);
        myDocumentPart.getContent().add(p);
        this.p = p;

        if (style == null) {
            if (myPrValueRunnable != null) {
                myPrValueRunnable.run(pPr);
            } else {
                // Create object for pStyle if one does not already exist
                createPStyle(pPr, BlockFormatProvider.DEFAULT_STYLE);
            }
        } else {
            createPStyle(pPr, style);
        }

        // Create object for rPr
        ParaRPr pararpr = pPr.getRPr();
        if (pararpr == null) {
            pararpr = myFactory.createParaRPr();
            pPr.setRPr(pararpr);
        }

        return p;
    }

    public P getP() {
        if (p != null) {
            return p;
        }
        return createP();
    }

    public R createR() {
        P p = getP();
        R r = myFactory.createR();
        RPr rPr = myFactory.createRPr();
        r.setRPr(rPr);

        p.getContent().add(r);

        return r;
    }

    public R getR() {
        if (p == null || p.getContent().isEmpty() || !(p.getContent().get(p.getContent().size() - 1) instanceof R)) {
            return createR();
        } else {
            return (R) p.getContent().get(p.getContent().size() - 1);
        }
    }

    public Style getStyle(final String styleName) {
        return myDocumentPart.getStyleDefinitionsPart().getStyleById(styleName);
    }

    private void addBlankLine(final BigInteger size, final String styleId) {
        if (size.compareTo(BigInteger.ZERO) > 0) {
            // now add empty for spacing
            P p = createP();
            PPr pPr = p.getPPr();

            if (styleId != null && !styleId.isEmpty()) {
                if (pPr.getPStyle() == null) {
                    PPrBase.PStyle pStyle = myFactory.createPPrBasePStyle();
                    pPr.setPStyle(pStyle);
                }
                pPr.getPStyle().setVal(styleId);
            }

            // Create new Spacing which we override
            PPrBase.Spacing spacing = myFactory.createPPrBaseSpacing();
            pPr.setSpacing(spacing);

            spacing.setBefore(BigInteger.ZERO);
            spacing.setAfter(BigInteger.ZERO);
            spacing.setLine(size);
            spacing.setLineRule(STLineSpacingRule.EXACT);

            R r = createR();
        }
    }

    private WordprocessingMLPackage myPackage;
    private MainDocumentPart myDocumentPart;
    ObjectFactory myFactory;
    private BigInteger myBefore;
    private BigInteger myAfter;
    private Style myStyle;
    private P p;
    DocxHelper myHelper;
    private ValueRunnable<PPr> myPrValueRunnable;
    private ValueRunnable<PPr> myFencedCodePPr;
    private ValueRunnable<PPr> mySectionPPr;
    private String myVisibleLineBreak;

    @Override
    public void fullTestSpecStarting() {
        if (!DUMP_ALL_TESTS_FILES) return;

        myPackage = DocxRenderer.getDefaultTemplate();
        if (myPackage == null) return;

        myDocumentPart = myPackage.getMainDocumentPart();
        myFactory = new ObjectFactory();
        myStyle = getStyle(PREFORMATTED_TEXT_STYLE);
        myHelper = new DocxHelper(myPackage, myFactory);
        myVisibleLineBreak = "¶";

        if (myStyle != null) {
            // Should always be true
            myBefore = myHelper.safeSpacingBefore(myStyle.getPPr());
            myAfter = myHelper.safeSpacingAfter(myStyle.getPPr());
        } else {
            myBefore = BigInteger.ZERO;
            myAfter = BigInteger.ZERO;
        }

        myFencedCodePPr = new ValueRunnable<PPr>() {
            @Override
            public void run(final PPr pPr) {
                createPStyle(pPr, BlockFormatProvider.PREFORMATTED_TEXT_STYLE);

                myHelper.ensureSpacing(pPr);
                final PPrBase.Spacing spacing = pPr.getSpacing();
                spacing.setBefore(BigInteger.ZERO);
                spacing.setAfter(BigInteger.ZERO);

                ParaRPr rPr = myFactory.createParaRPr();
                pPr.setRPr(rPr);

                // Create object for sz
                HpsMeasure hpsmeasure = myFactory.createHpsMeasure();
                rPr.setSz(hpsmeasure);
                hpsmeasure.setVal(BigInteger.valueOf(19));
            }
        };

        mySectionPPr = new ValueRunnable<PPr>() {
            @Override
            public void run(final PPr pPr) {
                // Create object for pStyle if one does not already exist
                createPStyle(pPr, "Heading3");

                // Create object for rPr
                ParaRPr pararpr = pPr.getRPr();
                if (pararpr == null) {
                    pararpr = myFactory.createParaRPr();
                    pPr.setRPr(pararpr);
                }

                // Create object for numPr
                PPrBase.NumPr baseNumPr = myFactory.createPPrBaseNumPr();
                pPr.setNumPr(baseNumPr);

                // Create object for numId
                PPrBase.NumPr.NumId prNumId = myFactory.createPPrBaseNumPrNumId();
                baseNumPr.setNumId(prNumId);
                prNumId.setVal(BigInteger.valueOf(0));

                // Create object for ilvl
                PPrBase.NumPr.Ilvl prIlvl = myFactory.createPPrBaseNumPrIlvl();
                baseNumPr.setIlvl(prIlvl);
                prIlvl.setVal(BigInteger.valueOf(3));
            }
        };
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
            final String s = XmlFormatter.format(xml);
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
