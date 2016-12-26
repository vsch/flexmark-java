package com.vladsch.flexmark.test;

import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.util.options.DataHolder;
import org.junit.AssumptionViolatedException;

import java.io.InputStream;

import static com.vladsch.flexmark.test.RenderingTestCase.FAIL;

public class DumpSpecReader extends SpecReader {
    private final StringBuilder sb = new StringBuilder();
    private final FullSpecTestCase testCase;

    public DumpSpecReader(InputStream stream, FullSpecTestCase testCase) {
        super(stream);
        this.testCase = testCase;
    }

    public String getFullSpec() {
        return sb.toString();
    }

    @Override
    protected void addSpecLine(String line) {
        sb.append(line).append("\n");
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

        String parseSource = example.getSource();
        if (options != null && options.get(RenderingTestCase.NO_FILE_EOL)) {
            if (!parseSource.isEmpty() && parseSource.charAt(parseSource.length()-1) == '\n') {
                parseSource = parseSource.substring(0, parseSource.length()-1);
            }
        }

        Node node = testCase.parser().withOptions(options).parse(parseSource);
        String html = !ignoredCase && testCase.useActualHtml() ? testCase.renderer().withOptions(options).render(node) : example.getHtml();
        String ast = example.getAst() == null ? null : (!ignoredCase ? testCase.ast(node) : example.getAst());

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
                header.append("(").append(section.trim()).append(": ").append(number).append(")");
            } else {
                header.append(" ").append(section.trim()).append(": ").append(number);
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
            sb.append(showTabs(source + SpecReader.TYPE_BREAK + "\n" + html))
                    .append(SpecReader.TYPE_BREAK).append("\n")
                    .append(ast).append(SpecReader.EXAMPLE_BREAK).append("\n");
        } else {
            sb.append(showTabs(source + SpecReader.TYPE_BREAK + "\n" + html))
                    .append(SpecReader.EXAMPLE_BREAK).append("\n");
        }
    }

    public static String showTabs(String s) {
        if (s == null) return "";
        // Tabs are shown as "rightwards arrow →" for easier comparison and IntelliJ dummy identifier as ⎮23ae, CR ⏎ 23ce
        return s.replace("\u2192", "&#2192;").replace("\t", "\u2192").replace("\u23ae", "&#23ae;").replace("\u001f", "\u23ae").replace("\u23ce", "&#23ce").replace("\r","\u23ce");
    }

    public static String unShowTabs(String s) {
        if (s == null) return "";
        // Tabs are shown as "rightwards arrow" for easier comparison and IntelliJ dummy identifier as ⎮
        return s.replace("\u23ce", "\r").replace("&#23ce", "\u23ce").replace("\u23ae", "\u001f").replace("&#23ae;", "\u23ae").replace('\u2192', '\t').replace("&#2192;", "\u2192");
    }

}
