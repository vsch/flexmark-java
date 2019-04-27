package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.spec.UrlString;
import com.vladsch.flexmark.util.ast.IParse;
import com.vladsch.flexmark.util.ast.IRender;
import com.vladsch.flexmark.util.ast.Document;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;
import org.junit.AssumptionViolatedException;

import java.io.InputStream;

import static com.vladsch.flexmark.test.RenderingTestCase.FAIL;
import static com.vladsch.flexmark.util.Utils.suffixWith;
import static com.vladsch.flexmark.util.Utils.suffixWithEol;

public class DumpSpecReader extends SpecReader implements ActualExampleModifier {
    protected final StringBuilder sb = new StringBuilder();
    protected final FullSpecTestCase testCase;
    protected StringBuilder exampleComment;
    protected final ActualExampleModifier exampleModifier;

    public DumpSpecReader(InputStream stream, FullSpecTestCase testCase, ActualExampleModifier exampleModifier) {
        this(stream, testCase, new UrlString(SpecReader.getSpecInputFileUrl(testCase.getSpecResourceName())).toString(), exampleModifier);
    }

    public DumpSpecReader(InputStream stream, FullSpecTestCase testCase, String fileUrl, ActualExampleModifier exampleModifier) {
        super(stream, fileUrl);
        this.exampleModifier = exampleModifier;
        this.testCase = testCase;
    }

    public String getFullSpec() {
        return sb.toString();
    }

    @Override
    public void addSpecLine(String line) {
        sb.append(line).append("\n");
    }

    @Override
    public String actualSource(final String source, final String optionSet) {
        return exampleModifier.actualSource(source, optionSet);
    }

    @Override
    public String actualHtml(final String html, final String optionSet) {
        return exampleModifier.actualHtml(html, optionSet);
    }

    @Override
    public String actualAst(final String ast, final String optionSet) {
        return exampleModifier.actualAst(ast, optionSet);
    }

    @Override
    protected void addSpecExample(SpecExample example) {
        DataHolder options;
        boolean ignoredCase = false;
        try {
            options = testCase.getOptions(example, example.getOptionsSet());
        } catch (AssumptionViolatedException ignored) {
            ignoredCase = true;
            options = null;
        }

        if (options != null && options.get(FAIL)) {
            ignoredCase = true;
        }

        IParse parserWithOptions = testCase.parser().withOptions(options);
        IRender rendererWithOptions = testCase.renderer().withOptions(options);

        String parseSource = example.getSource();
        if (options != null && options.get(RenderingTestCase.NO_FILE_EOL)) {
            parseSource = trimTrailingEOL(parseSource);
        }

        BasedSequence input;
        String sourcePrefix = RenderingTestCase.SOURCE_PREFIX.getFrom(parserWithOptions.getOptions());
        String sourceSuffix = RenderingTestCase.SOURCE_SUFFIX.getFrom(parserWithOptions.getOptions());
        String sourceIndent = RenderingTestCase.SOURCE_INDENT.getFrom(parserWithOptions.getOptions());

        if (!sourcePrefix.isEmpty() || !sourceSuffix.isEmpty()) {
            String combinedSource = sourcePrefix + suffixWith(parseSource, "\n") + sourceSuffix;
            input = BasedSequenceImpl.of(combinedSource).subSequence(sourcePrefix.length(), combinedSource.length() - sourceSuffix.length());
        } else {
            input = BasedSequenceImpl.of(parseSource);
        }

        input = RenderingTestCase.stripIndent(input, sourceIndent);

        Node includedDocument = null;

        String includedText = RenderingTestCase.INCLUDED_DOCUMENT.getFrom(parserWithOptions.getOptions());
        if (includedText != null && !includedText.isEmpty()) {
            // need to parse and transfer references
            includedDocument = parserWithOptions.parse(includedText);

            if (includedDocument instanceof Document) {
                parserWithOptions = testCase.adjustParserForInclusion(parserWithOptions, (Document) includedDocument);
            }
        }

        boolean timed = RenderingTestCase.TIMED.getFrom(parserWithOptions.getOptions());
        int iterations = timed ? RenderingTestCase.TIMED_ITERATIONS.getFrom(parserWithOptions.getOptions()) : 1;

        String inputText = input.toString();
        String useSource = actualSource(inputText, optionsSet);
        BasedSequence inputSource = inputText == useSource ? input : BasedSequenceImpl.of(useSource);

        Node node = parserWithOptions.parse(inputSource);

        long start = System.nanoTime();
        for (int i = 1; i < iterations; i++) parserWithOptions.parse(input);
        long parse = System.nanoTime();

        if (node instanceof Document && includedDocument instanceof Document) {
            parserWithOptions.transferReferences((Document) node, (Document) includedDocument);
        }

        String actualHTML = rendererWithOptions.render(node);
        for (int i = 1; i < iterations; i++) rendererWithOptions.render(node);
        long render = System.nanoTime();

        boolean embedTimed = RenderingTestCase.EMBED_TIMED.getFrom(node.getDocument());

        if (timed || embedTimed) {
            System.out.println(String.format(RenderingTestCase.TIMED_FORMAT_STRING, example.getSection() == null ? "" : example.getSection().trim() + ": " + example.getExampleNumber(), (parse - start) / 1000000.0 / iterations, (render - parse) / 1000000.0 / iterations, (render - start) / 1000000.0 / iterations));
        }

        final String actualAST = testCase.ast(node);
        String html = !ignoredCase && testCase.useActualHtml() ? actualHtml(actualHTML, optionsSet) : example.getHtml();
        String ast = example.getAst() == null ? null : (!ignoredCase ? actualAST : example.getAst());

        // allow other formats to accumulate
        testCase.addSpecExample(example, node, options, ignoredCase, actualHTML, actualAST);

        if (embedTimed) {
            sb.append(String.format(RenderingTestCase.TIMED_FORMAT_STRING, example.getSection() == null ? "" : example.getSection().trim() + ": " + example.getExampleNumber(), (parse - start) / 1000000.0 / iterations, (render - parse) / 1000000.0 / iterations, (render - start) / 1000000.0 / iterations));
        }

        // include source so that diff can be used to update spec
        addSpecExample(sb, example.getSource(), html, ast, example.getOptionsSet(), testCase.includeExampleCoords(), example.getSection(), example.getExampleNumber());
    }

    public static String addSpecExample(String source, String html, String ast, String optionsSet) {
        StringBuilder sb = new StringBuilder();
        addSpecExample(sb, source, html, ast, optionsSet, false, "", 0);
        return sb.toString();
    }

    public static void addSpecExample(StringBuilder sb, String source, String html, String ast, String optionsSet, boolean includeExampleCoords, String section, int number) {
        // include source so that diff can be used to update spec
        StringBuilder header = new StringBuilder();

        header.append(SpecReader.EXAMPLE_START);
        if (includeExampleCoords) {
            if (optionsSet != null) {
                header.append("(").append(section == null ? "" : section.trim()).append(": ").append(number).append(")");
            } else {
                header.append(" ").append(section == null ? "" : section.trim()).append(": ").append(number);
            }
        }
        if (optionsSet != null) {
            header.append(SpecReader.OPTIONS_STRING + "(").append(optionsSet).append(")");
        }
        header.append("\n");

        // replace spaces so GitHub can display example as code fence, but not for original spec which has no coords
        if (includeExampleCoords) sb.append(header.toString().replace(' ', '\u00A0'));
        else sb.append(header.toString());

        if (ast != null) {
            sb.append(showTabs(suffixWithEol(source) + SpecReader.TYPE_BREAK + "\n" + suffixWithEol(html)))
                    .append(SpecReader.TYPE_BREAK).append("\n")
                    .append(ast).append(SpecReader.EXAMPLE_BREAK).append("\n");
        } else {
            sb.append(showTabs(suffixWithEol(source) + SpecReader.TYPE_BREAK + "\n" + suffixWithEol(html)))
                    .append(SpecReader.EXAMPLE_BREAK).append("\n");
        }
    }

    public static String showTabs(String s) {
        if (s == null) return "";
        // Tabs are shown as "rightwards arrow →" for easier comparison and IntelliJ dummy identifier as ⎮23ae, CR ⏎ 23ce
        return s.replace("\u2192", "&#2192;").replace("\t", "\u2192").replace("\u23ae", "&#23ae;").replace("\u001f", "\u23ae").replace("\u23ce", "&#23ce").replace("\r", "\u23ce");
    }

    public static String unShowTabs(String s) {
        if (s == null) return "";
        // Tabs are shown as "rightwards arrow" for easier comparison and IntelliJ dummy identifier as ⎮
        return s.replace("\u23ce", "\r").replace("&#23ce", "\u23ce").replace("\u23ae", "\u001f").replace("&#23ae;", "\u23ae").replace('\u2192', '\t').replace("&#2192;", "\u2192");
    }

    public static String trimTrailingEOL(String parseSource) {
        if (!parseSource.isEmpty() && parseSource.charAt(parseSource.length() - 1) == '\n') {
            // if previous line is blank, then no point in removing this EOL, just leave it
            int pos = parseSource.lastIndexOf('\n', parseSource.length() - 2);
            if (pos == -1 || !parseSource.substring(pos + 1).trim().isEmpty()) {
                parseSource = parseSource.substring(0, parseSource.length() - 1);
            } else {
                int tmp = 0;
            }
        }
        return parseSource;
    }
}
