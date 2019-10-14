package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;
import org.junit.AssumptionViolatedException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static com.vladsch.flexmark.util.Utils.suffixWithEol;

public class TestUtils {
    public static final String IGNORE_OPTION_NAME = "IGNORE";
    public static final DataKey<Boolean> IGNORE = new DataKey<>(IGNORE_OPTION_NAME, false);
    public static final String FAIL_OPTION_NAME = "FAIL";
    public static final DataKey<Boolean> FAIL = new DataKey<>(FAIL_OPTION_NAME, false);
    public static final String NO_FILE_EOL_OPTION_NAME = "NO_FILE_EOL";
    public static final DataKey<Boolean> NO_FILE_EOL = new DataKey<>(NO_FILE_EOL_OPTION_NAME, true);
    public static final String FILE_EOL_OPTION_NAME = "FILE_EOL";
    public static final String TIMED_ITERATIONS_NAME = "TIMED_ITERATIONS_NAME";
    public static final DataKey<Integer> TIMED_ITERATIONS = new DataKey<>(TIMED_ITERATIONS_NAME, 100);
    public static final String TIMED_OPTION_NAME = "TIMED";
    public static final DataKey<Boolean> EMBED_TIMED = new DataKey<>(TIMED_OPTION_NAME, false);
    public static final DataKey<Boolean> TIMED = new DataKey<>(TIMED_OPTION_NAME, false);
    public static final String EMBED_TIMED_OPTION_NAME = "EMBED_TIMED";
    public static final String TIMED_FORMAT_STRING = "Timing %s: parse %.3f ms, render %.3f ms, total %.3f\n";
    public static final DataKey<String> INCLUDED_DOCUMENT = new DataKey<>("INCLUDED_DOCUMENT", "");
    public static final DataKey<String> SOURCE_PREFIX = new DataKey<>("SOURCE_PREFIX", "");
    public static final DataKey<String> SOURCE_SUFFIX = new DataKey<>("SOURCE_SUFFIX", "");
    public static final DataKey<String> SOURCE_INDENT = new DataKey<>("SOURCE_INDENT", "");

    /**
     * process comma separated list of option sets and combine them for final set to use
     *
     * @param example    spec example instance for which options are being processed
     * @param optionSets comma separate list of option set names
     * @param optionsProvider  function to take a string option name and provide settings based on it
     * @return combined set from applying these options together
     */
    public static DataHolder getOptions(SpecExample example, String optionSets, Function<String, DataHolder> optionsProvider) {
        if (optionSets == null) return null;
        String[] optionNames = optionSets.replace('\u00A0', ' ').split(",");
        DataHolder options = null;
        boolean isFirst = true;
        for (String optionName : optionNames) {
            String option = optionName.trim();
            if (option.isEmpty() || option.startsWith("-")) continue;

            if (option.equals(IGNORE_OPTION_NAME)) {
                throwIgnoredOption(example, optionSets, option);
            } else if (option.equals(FAIL_OPTION_NAME)) {
                options = addOption(options, FAIL, true);
            } else if (option.equals(NO_FILE_EOL_OPTION_NAME)) {
                options = addOption(options, NO_FILE_EOL, true);
            } else if (option.equals(FILE_EOL_OPTION_NAME)) {
                options = addOption(options, NO_FILE_EOL, false);
            } else if (option.equals(TIMED_OPTION_NAME)) {
                options = addOption(options, TIMED, true);
            } else if (option.equals(EMBED_TIMED_OPTION_NAME)) {
                options = addOption(options, EMBED_TIMED, true);
            } else {
                if (options == null) {
                    options = optionsProvider.apply(option);

                    if (options == null) {
                        throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass");
                    }
                } else {
                    DataHolder dataSet = optionsProvider.apply(option);

                    if (dataSet != null) {
                        if (isFirst) {
                            options = new MutableDataSet(options);
                            isFirst = false;
                        }
                        ((MutableDataHolder) options).setAll(dataSet);
                    } else {
                        throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass");
                    }
                }

                if (IGNORE.getFrom(options)) {
                    throwIgnoredOption(example, optionSets, option);
                }
            }
        }
        return options;
    }

    public static <T> MutableDataSet addOption(DataHolder options, DataKey<T> key, T value) {
        if (options == null) {
            return new MutableDataSet().set(key, value);
        } else {
            return new MutableDataSet(options).set(key, value);
        }
    }

    public static void throwIgnoredOption(SpecExample example, String optionSets, String option) {
        if (example == null)
            throw new AssumptionViolatedException("Ignored: SpecExample test case options(" + optionSets + ") is using " + option + " option");
        else
            throw new AssumptionViolatedException("Ignored: example(" + example.getSection() + ": " + example.getExampleNumber() + ") options(" + optionSets + ") is using " + option + " option");
    }

    @NotNull
    public static String ast(@NotNull Node node) {
        return new AstCollectingVisitor().collectAndGetAstText(node);
    }

    public static BasedSequence stripIndent(BasedSequence input, CharSequence sourceIndent) {
        BasedSequence result = input;
        if (sourceIndent.length() != 0) {
            // strip out indent to test how segmented input parses
            List<BasedSequence> segments = new ArrayList<>();
            int lastPos = 0;
            int length = input.length();

            while (lastPos < length) {
                int pos = input.indexOf(sourceIndent, lastPos);
                int end = pos == -1 ? length : pos;

                if (lastPos < end && (pos <= 0 || input.charAt(pos - 1) == '\n')) {
                    segments.add(input.subSequence(lastPos, end));
                }
                lastPos = end + sourceIndent.length();
            }

            result = SegmentedSequence.of(segments);
        }
        return result;
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
            }
        }
        return parseSource;
    }

    public static String getFormattedTimingInfo(int iterations, long start, long parse, long render) {
        return getFormattedTimingInfo(null, 0, iterations, start, parse, render);
    }

    public static String getFormattedTimingInfo(String section, int exampleNumber, int iterations, long start, long parse, long render) {
        return String.format(TIMED_FORMAT_STRING, section == null ? "" : section.trim() + ": " + exampleNumber, (parse - start) / 1000000.0 / iterations, (render - parse) / 1000000.0 / iterations, (render - start) / 1000000.0 / iterations);
    }
}
