package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.*;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.misc.Extension;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.RichSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import com.vladsch.flexmark.util.sequence.SequenceUtils;
import com.vladsch.flexmark.util.sequence.builder.SequenceBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.AssumptionViolatedException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.vladsch.flexmark.test.util.spec.SpecReader.EXAMPLE_KEYWORD;
import static com.vladsch.flexmark.util.misc.Utils.*;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.isBlank;
import static com.vladsch.flexmark.util.sequence.SequenceUtils.*;

public class TestUtils {
    final public static char MARKUP_CARET_CHAR = '⦙';
    final public static char MARKUP_SELECTION_START_CHAR = '⟦';
    final public static char MARKUP_SELECTION_END_CHAR = '⟧';
    final public static String MARKUP_CARET = Character.toString(MARKUP_CARET_CHAR);
    final public static String MARKUP_SELECTION_START = Character.toString(MARKUP_SELECTION_START_CHAR);
    final public static String MARKUP_SELECTION_END = Character.toString(MARKUP_SELECTION_END_CHAR);
    final public static @NotNull CharPredicate CARET_PREDICATE = CharPredicate.anyOf(MARKUP_CARET_CHAR);
    final public static @NotNull CharPredicate MARKUP_PREDICATE = CharPredicate.anyOf(MARKUP_CARET_CHAR, MARKUP_SELECTION_START_CHAR, MARKUP_SELECTION_END_CHAR);
    final public static int[] EMPTY_OFFSETS = new int[0];
    static {
        // CAUTION: need to register our url resolvers
        FlexmarkResourceUrlResolver.registerUrlResolvers();
    }

    final public static char DISABLED_OPTION_PREFIX_CHAR = '-';
    final public static String DISABLED_OPTION_PREFIX = String.valueOf(DISABLED_OPTION_PREFIX_CHAR);

    final public static String EMBED_TIMED_OPTION_NAME = "EMBED_TIMED";
    final public static String FAIL_OPTION_NAME = "FAIL";
    final public static String FILE_EOL_OPTION_NAME = "FILE_EOL";
    final public static String IGNORE_OPTION_NAME = "IGNORE";
    final public static String NO_FILE_EOL_OPTION_NAME = "NO_FILE_EOL";
    final public static String TIMED_ITERATIONS_OPTION_NAME = "TIMED_ITERATIONS";
    final public static String TIMED_OPTION_NAME = "TIMED";

    final public static DataKey<Boolean> EMBED_TIMED = new DataKey<>(TIMED_OPTION_NAME, false);
    final public static DataKey<Boolean> FAIL = new DataKey<>(FAIL_OPTION_NAME, false);
    final public static DataKey<Boolean> IGNORE = new DataKey<>(IGNORE_OPTION_NAME, false);
    final public static DataKey<Boolean> NO_FILE_EOL = new DataKey<>(NO_FILE_EOL_OPTION_NAME, true);
    final public static DataKey<Boolean> TIMED = new DataKey<>(TIMED_OPTION_NAME, false);
    final public static DataKey<Integer> TIMED_ITERATIONS = new DataKey<>(TIMED_ITERATIONS_OPTION_NAME, 100);

    final public static String TIMED_FORMAT_STRING = "Timing %s: parse %.3f ms, render %.3f ms, total %.3f\n";

    final public static DataKey<String> INCLUDED_DOCUMENT = new DataKey<>("INCLUDED_DOCUMENT", "");
    final public static DataKey<String> SOURCE_PREFIX = new DataKey<>("SOURCE_PREFIX", "");
    final public static DataKey<String> SOURCE_SUFFIX = new DataKey<>("SOURCE_SUFFIX", "");
    final public static DataKey<String> SOURCE_INDENT = new DataKey<>("SOURCE_INDENT", "");

    final public static DataHolder NO_FILE_EOL_FALSE = new MutableDataSet().set(NO_FILE_EOL, false).toImmutable();
    final public static DataKey<Collection<Class<? extends Extension>>> UNLOAD_EXTENSIONS = LoadUnloadDataKeyAggregator.UNLOAD_EXTENSIONS;
    final public static DataKey<Collection<Extension>> LOAD_EXTENSIONS = LoadUnloadDataKeyAggregator.LOAD_EXTENSIONS;
    final private static DataHolder EMPTY_OPTIONS = new DataSet();
    final public static DataKey<BiFunction<String, String, DataHolder>> CUSTOM_OPTION = new DataKey<>("CUSTOM_OPTION", (option, params) -> EMPTY_OPTIONS);
    final public static String FILE_PROTOCOL = ResourceUrlResolver.FILE_PROTOCOL;

    public static DataHolder processOption(@NotNull Map<String, ? extends DataHolder> optionsMap, @NotNull String option) {
        DataHolder dataHolder = null;
        if (!option.startsWith(DISABLED_OPTION_PREFIX)) {
            dataHolder = optionsMap.get(option);
            String customOption = option;
            String params = null;

            if (dataHolder == null) {
                // see if parameterized option
                ExampleOption exampleOption = ExampleOption.of(option);
                if (exampleOption.isCustom) {
                    // parameterized, see if there is a handler defined for it
                    customOption = exampleOption.getOptionName();
                    params = exampleOption.getCustomParams();
                    dataHolder = optionsMap.get(customOption);
                }
            }

            // if custom option is set then delegate to it
            if (dataHolder != null && dataHolder.contains(CUSTOM_OPTION)) {
                BiFunction<String, String, DataHolder> customHandler = CUSTOM_OPTION.get(dataHolder);
                dataHolder = customHandler.apply(customOption, params);
            }
        }
        return dataHolder;
    }

    @NotNull
    public static <T> HashMap<String, T> buildOptionsMap(@NotNull String[] options, @NotNull BiFunction<ExampleOption, Integer, T> factory) {
        HashMap<String, T> hashMap = new HashMap<>();
        int i = 0;
        for (String option : options) {
            hashMap.put(option, factory.apply(ExampleOption.of(option), i));
            i++;
        }
        return hashMap;
    }

    /**
     * Build options map, optionally ensuring all built-ins are present
     *
     * @param ensureAllBuiltInPresent if true, throws IllegalStateException if some built-in options are missing
     * @param options                 array of object arrays, each row represents option values
     *                                with first element ([0]) of each row being an option string.
     *                                Each row is passed to factory to allow creating custom options.
     * @param factory                 factory creating a type from ExampleOption and given row of parameters
     * @param <T>                     type of value in the map
     * @return constructed hash map of option name
     */
    @NotNull
    public static <T> HashMap<String, T> buildOptionsMap(boolean ensureAllBuiltInPresent, @NotNull Object[][] options, @NotNull BiFunction<ExampleOption, Object[], T> factory) {
        HashMap<String, T> hashMap = new HashMap<>();
        HashSet<String> builtInSet = new HashSet<>(ExampleOption.getBuiltInOptions().keySet());

        for (Object[] optionData : options) {
            assert optionData[0] instanceof String;
            String option = (String) optionData[0];

            ExampleOption exampleOption = ExampleOption.of(option);
            hashMap.put(option, factory.apply(exampleOption, optionData));
            if (exampleOption.isBuiltIn && exampleOption.isValid && !(exampleOption.isCustom || exampleOption.isDisabled)) {
                builtInSet.remove(exampleOption.getOptionName());
            }
        }

        if (ensureAllBuiltInPresent && !builtInSet.isEmpty()) {
            DelimitedBuilder sb = new DelimitedBuilder(",\n    ");
            sb.append("    ");
            for (String option : builtInSet) {
                sb.append(option).mark();
            }

            throw new IllegalStateException("Not all built-in options present. Missing:\n" + sb.toString());
        }
        return hashMap;
    }

    @NotNull
    public static Pair<String, Integer> addSpecSection(@NotNull String headingLine, @NotNull String headingText, String[] sectionHeadings) {
        assert sectionHeadings.length == 7;
        int lastSectionLevel = Math.max(1, Math.min(6, RichSequence.of(headingLine).countLeading(CharPredicate.HASH)));
        sectionHeadings[lastSectionLevel] = headingText;
        int iMax = 7;
        for (int i = lastSectionLevel + 1; i < iMax; i++) {
            sectionHeadings[i] = null;
        }

        StringBuilder sb = new StringBuilder();
        String sep = "";
        int level = 0;
        for (String heading : sectionHeadings) {
            if (heading != null && level > 1) {
                sb.append(sep).append(heading);
                sep = " - ";
                if (level == lastSectionLevel) break;
            }
            level++;
        }

        String section = sb.toString();
        if (section.isEmpty()) section = headingText;
        return Pair.of(section, lastSectionLevel);
    }

    /**
     * process comma separated list of option sets and combine them for final set to use
     *
     * @param example         spec example instance for which options are being processed
     * @param optionSets      comma separate list of option set names
     * @param optionsProvider function to take a string option name and provide settings based on it
     * @return combined set from applying these options together
     */
    public static DataHolder getOptions(@NotNull SpecExample example, @Nullable String optionSets, @NotNull Function<String, DataHolder> optionsProvider) {
        if (optionSets == null) return null;
        String[] optionNames = optionSets.replace('\u00A0', ' ').split(",");
        DataHolder options = null;
        for (String optionName : optionNames) {
            String option = optionName.trim();
            if (option.isEmpty() || option.startsWith("-")) continue;

            switch (option) {
                case IGNORE_OPTION_NAME:
                    throwIgnoredOption(example, optionSets, option);
                    break;
                case FAIL_OPTION_NAME:
                    options = addOption(options, FAIL, true);
                    break;
                case NO_FILE_EOL_OPTION_NAME:
                    options = addOption(options, NO_FILE_EOL, true);
                    break;
                case FILE_EOL_OPTION_NAME:
                    options = addOption(options, NO_FILE_EOL, false);
                    break;
                case TIMED_OPTION_NAME:
                    options = addOption(options, TIMED, true);
                    break;
                case EMBED_TIMED_OPTION_NAME:
                    options = addOption(options, EMBED_TIMED, true);
                    break;
                default:
                    if (options == null) {
                        options = optionsProvider.apply(option);

                        if (options == null) {
                            throwIllegalStateException(example, option);
                        }

                        options = options.toImmutable();
                    } else {
                        DataHolder dataSet = optionsProvider.apply(option);

                        if (dataSet != null) {
                            // CAUTION: have to only aggregate actions here
                            options = DataSet.aggregateActions(options.toImmutable(), dataSet);
                        } else {
                            throwIllegalStateException(example, option);
                        }
                    }

                    if (options.contains(IGNORE) && IGNORE.get(options)) {
                        throwIgnoredOption(example, optionSets, option);
                    }
                    break;
            }
        }
        return options == null ? null : options.toImmutable();
    }

    public static <T> MutableDataSet addOption(DataHolder options, DataKey<T> key, T value) {
        if (options == null) {
            return new MutableDataSet().set(key, value);
        } else {
            return new MutableDataSet(options).set(key, value);
        }
    }

    public static void throwIllegalStateException(@NotNull SpecExample example, @NotNull String option) {
        throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass\n" + example.getFileUrlWithLineNumber(-1));
    }

    public static void throwIgnoredOption(@NotNull SpecExample example, @NotNull String optionSets, @NotNull String option) {
        throw new AssumptionViolatedException("Ignored: example(" + example.getSection() + ": " + example.getExampleNumber() + ") options(" + optionSets + ") is using " + option + " option\n" + example.getFileUrlWithLineNumber(-1));
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

            result = SegmentedSequence.create(input, segments);
        }
        return result;
    }

    public static String addSpecExample(boolean includeExampleStart, String source, String html, String ast, String optionsSet) {
        StringBuilder sb = new StringBuilder();
        addSpecExample(includeExampleStart, sb, source, html, ast, optionsSet, false, "", 0);
        return sb.toString();
    }

    public static void addSpecExample(boolean includeExampleStart, StringBuilder sb, String source, String html, String ast, String optionsSet, boolean includeExampleCoords, String section, int number) {
        addSpecExample(includeExampleStart, true, sb, source, html, ast, optionsSet, includeExampleCoords, section, number);
    }

    public static void addSpecExample(boolean includeExampleStart, boolean toVisibleSpecText, StringBuilder sb, String source, String html, String ast, String optionsSet, boolean includeExampleCoords, String section, int number) {
        addSpecExample(false, includeExampleStart, toVisibleSpecText, sb, source, html, ast, optionsSet, includeExampleCoords, section, number);
    }

    public static void addSpecExample(boolean useTestExample, boolean includeExampleStart, boolean toVisibleSpecText, StringBuilder sb, String source, String html, String ast, String optionsSet, boolean includeExampleCoords, String section, int number) {
        addSpecExample(
                useTestExample ? SpecReader.EXAMPLE_TEST_BREAK : SpecReader.EXAMPLE_BREAK,
                useTestExample ? SpecReader.SECTION_TEST_BREAK : SpecReader.SECTION_BREAK,
                includeExampleStart,
                toVisibleSpecText,
                sb,
                source,
                html,
                ast,
                optionsSet,
                includeExampleCoords,
                section,
                number
        );
    }

    public static void addSpecExample(
            CharSequence exampleBreak,
            CharSequence sectionBreak,
            boolean includeExampleStart,
            boolean toVisibleSpecText,
            Appendable out,
            CharSequence source,
            CharSequence html,
            CharSequence ast,
            CharSequence optionsSet,
            boolean includeExampleCoords,
            CharSequence section,
            int number
    ) {
        addSpecExample(
                (CharSequence) exampleBreak,
                (CharSequence) sectionBreak,
                (CharSequence) sectionBreak,
                (CharSequence) exampleBreak,
                includeExampleStart,
                toVisibleSpecText,
                out,
                (CharSequence) source,
                (CharSequence) html,
                (CharSequence) ast,
                (CharSequence) optionsSet,
                includeExampleCoords,
                (CharSequence) section,
                Integer.toString(number),
                EXAMPLE_KEYWORD,
                SpecReader.OPTIONS_KEYWORD);
    }

    public static void addSpecExample(
            CharSequence exampleBreakOpen,
            CharSequence htmlBreak,
            CharSequence astBreak,
            CharSequence exampleBreakClose,
            boolean includeExampleStart,
            boolean toVisibleSpecText,
            Appendable out,
            CharSequence source,
            CharSequence html,
            CharSequence ast,
            CharSequence optionsSet,
            boolean includeExampleCoords,
            CharSequence section,
            CharSequence number,
            CharSequence exampleKeyword,
            CharSequence optionsKeyword
    ) {
        // include source so that diff can be used to update spec
//        StringBuilder header = new StringBuilder();

        try {
            if (includeExampleStart) {
                out.append(exampleBreakOpen).append(' ').append(exampleKeyword);
                if (includeExampleCoords) {
                    if (optionsSet != null && !isBlank(optionsSet)) {
                        out.append("(").append(section == null || section == BasedSequence.NULL ? "" : trim(section)).append(": ").append(number).append(")");
                    } else {
                        out.append(" ").append(section == null || section == BasedSequence.NULL ? "" : trim(section)).append(": ").append(number);
                    }
                }
                if (optionsSet != null && !isBlank(optionsSet)) {
                    out.append(' ').append(optionsKeyword).append("(").append(optionsSet).append(")");
                }
                out.append("\n");
            }

            // NOTE: replacing spaces so GitHub can display example as code fence, but not for original spec which has no coords
            //   is no longer an issue since GitHub switched to CommonMark parser a while back
//        if (includeExampleCoords) { sb.append(header.toString().replace(' ', '\u00A0')); } else sb.append(header.toString());
//            out.append(header);

            // FIX: When multi-sections are implemented need a way to specify per section visibleSpecText
            if (toVisibleSpecText) {
                if (!isEmpty(source)) {
                    out.append(toVisibleSpecText(source));
                    if (!endsWithEOL(source)) out.append("\n");
                }

                out.append(htmlBreak);
                if (!endsWithEOL(htmlBreak)) out.append("\n");

                if (html != null && !isEmpty(html)) {
                    out.append(toVisibleSpecText(html));
                    if (!endsWithEOL(html)) out.append("\n");
                }
            } else {
                if (!isEmpty(source)) {
                    out.append(source);
                    if (!endsWithEOL(source)) out.append("\n");
                }

                out.append(htmlBreak);
                if (!endsWithEOL(htmlBreak)) out.append("\n");

                if (html != null && !isEmpty(html)) {
                    out.append(html);
                    if (!endsWithEOL(html)) out.append("\n");
                }
            }
            if (ast != null && ast != BasedSequence.NULL) {
                out.append(astBreak);
                if (!endsWithEOL(htmlBreak)) out.append("\n");

                if (!isEmpty(ast)) {
                    out.append(ast);
                    if (!endsWithEOL(ast)) out.append("\n");
                }
            }
            out.append(exampleBreakClose);
            if (!endsWithEOL(exampleBreakClose)) out.append("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param s text to convert to visible chars
     * @return spec test special chars converted to visible
     * @deprecated use {@link #toVisibleSpecText(String)}
     */
    @Deprecated
    public static String showTabs(String s) {
        return toVisibleSpecText(s);
    }

    /**
     * @param s text to convert to visible chars
     * @return spec test special chars converted to visible
     */
    public static String toVisibleSpecText(String s) {
        if (s == null) return "";
        // Tabs are shown as "rightwards arrow →" for easier comparison and IntelliJ dummy identifier as ⎮23ae, CR ⏎ 23ce, LS to U+27A5 ➥
        return toVisibleSpecText((CharSequence) s).toString();
    }

    /**
     * @param s text to convert to visible chars
     * @return spec test special chars converted to visible
     */
    public static CharSequence toVisibleSpecText(CharSequence s) {
        if (s == null) return "";
        // Tabs are shown as "rightwards arrow →" for easier comparison and IntelliJ dummy identifier as ⎮23ae, CR ⏎ 23ce, LS to U+27A5 ➥
        BasedSequence sequence = BasedSequence.of(s);
        return sequence
                .replace("\u2192", "&#2192;").replace("\t", "\u2192")
                .replace("\u23ae", "&#23ae;").replace("\u001f", "\u23ae")
                .replace("\u23ce", "&#23ce;").replace("\r", "\u23ce")
                .replace("\u27a5", "&#27a5;").replace(SequenceUtils.LINE_SEP, "\u27a5")
                ;
    }

    /**
     * @param s text to convert to from visible chars to normal
     * @return spec test special visible chars converted to normal
     * @deprecated use {@link #fromVisibleSpecText(String)}
     */
    @Deprecated
    public static String unShowTabs(String s) {
        return fromVisibleSpecText(s);
    }

    /**
     * @param s text to convert to from visible chars to normal
     * @return spec test special visible chars converted to normal
     */
    public static String fromVisibleSpecText(String s) {
        if (s == null) return "";
        return fromVisibleSpecText((CharSequence) s).toString();
    }

    /**
     * @param s text to convert to from visible chars to normal
     * @return spec test special visible chars converted to normal
     */
    public static CharSequence fromVisibleSpecText(CharSequence s) {
        if (s == null) return "";
        BasedSequence sequence = BasedSequence.of(s);
        return sequence
                .replace("\u27a5", SequenceUtils.LINE_SEP).replace("&#27a5;", "\u27a5")
                .replace("\u23ce", "\r").replace("&#23ce;", "\u23ce")
                .replace("\u23ae", "\u001f").replace("&#23ae;", "\u23ae")
                .replace("\u2192", "\t").replace("&#2192;", "\u2192")
                ;
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
        return String.format(TIMED_FORMAT_STRING, getFormattedSection(section, exampleNumber), (parse - start) / 1000000.0 / iterations, (render - parse) / 1000000.0 / iterations, (render - start) / 1000000.0 / iterations);
    }

    @NotNull
    public static String getFormattedSection(String section, int exampleNumber) {
        return section == null ? "" : section.trim() + ": " + exampleNumber;
    }

    @NotNull
    public static String getResolvedSpecResourcePath(@NotNull String testClassName, @NotNull String resourcePath) {
        File specInfo = new File(resourcePath);
        File classInfo = new File("/" + testClassName.replace('.', '/'));
        return !specInfo.isAbsolute() ? new File(classInfo.getParent(), resourcePath).getAbsolutePath() : resourcePath;
    }

    @NotNull
    public static String getAbsoluteSpecResourcePath(@NotNull String testClassPath, @NotNull String resourceRootPath, @NotNull String resourcePath) {
        File resourceFile = resourcePath.startsWith("/") ? new File(resourceRootPath, resourcePath.substring(1)) : new File(new File(testClassPath).getParent(), resourcePath);
        return resourceFile.getAbsolutePath();
    }

    @NotNull
    public static String getSpecResourceFileUrl(@NotNull Class<?> resourceClass, @NotNull String resourcePath) {
        if (resourcePath.isEmpty()) {
            throw new IllegalStateException("Empty resource paths not supported");
        } else {
            String resolvedResourcePath = getResolvedSpecResourcePath(resourceClass.getName(), resourcePath);
            URL url = resourceClass.getResource(resolvedResourcePath);
            assert url != null : "Resource path: '" + resolvedResourcePath + "' not found.";
            return adjustedFileUrl(url);
        }
    }

    public static ArrayList<Object[]> getTestData(@NotNull ResourceLocation location) {
        SpecReader specReader = SpecReader.createAndReadExamples(location, true);
        List<SpecExample> examples = specReader.getExamples();
        ArrayList<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.NULL.withResourceLocation(location) });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    public static @NotNull String getUrlWithLineNumber(@NotNull String fileUrl, int lineNumber) {
        return (lineNumber > 0) ? fileUrl + ":" + (lineNumber + 1) : fileUrl;
    }

    public static String adjustedFileUrl(@NotNull URL url) {
        return ResourceResolverManager.adjustedFileUrl(url);
    }

    @Nullable
    public static DataHolder combineDefaultOptions(@Nullable DataHolder[] defaultOptions) {
        DataHolder combinedOptions = null;
        if (defaultOptions != null) {
            for (DataHolder options : defaultOptions) {
                combinedOptions = DataSet.aggregate(combinedOptions, options);
            }
        }
        return combinedOptions == null ? null : combinedOptions.toImmutable();
    }

    @Nullable
    public static Map<String, ? extends DataHolder> optionsMaps(@Nullable Map<String, ? extends DataHolder> other, @Nullable Map<String, ? extends DataHolder> overrides) {
        if (other != null && overrides != null) {
            HashMap<String, DataHolder> map = new HashMap<>(other);
            map.putAll(overrides);
            return map;
        } else if (other != null) {
            return other;
        } else {
            return overrides;
        }
    }

    @Nullable
    public static DataHolder[] dataHolders(@Nullable DataHolder other, @Nullable DataHolder[] overrides) {
        if (other == null) return overrides;
        else if (overrides == null || overrides.length == 0) return new DataHolder[] { other };

        DataHolder[] holders = new DataHolder[overrides.length + 1];
        System.arraycopy(overrides, 0, holders, 1, overrides.length);
        holders[0] = other;
        return holders;
    }

    @NotNull
    public static String getTestResourceRootDirectoryForModule(@NotNull Class<?> resourceClass, @NotNull String moduleRootPackage) {
        String fileUrl;
        fileUrl = getSpecResourceFileUrl(resourceClass, wrapWith(moduleRootPackage, "/", ".txt"));
        return removePrefix(removeSuffix(fileUrl, suffixWith(moduleRootPackage, ".txt")), FILE_PROTOCOL);
    }

    @NotNull
    public static String getRootDirectoryForModule(@NotNull Class<?> resourceClass, @NotNull String moduleDirectoryName) {
        // get project root from our class file url path
        String fileUrl = SpecExample.ofCaller(0, resourceClass, "", "", "").getFileUrl();
        int pos = fileUrl.indexOf(wrapWith(moduleDirectoryName, '/'));
        if (pos != -1) {
            fileUrl = fileUrl.substring(0, pos);
        }
        fileUrl = fileUrl.substring(FILE_PROTOCOL.length());
        return fileUrl;
    }

    // handle custom string options
    public static DataHolder customStringOption(@NotNull String option, @Nullable String params, @NotNull Function<String, DataHolder> resolver) {
        if (params != null) {
            // allow escape
            String text = params
                    .replace("\\\\", "\\")
                    .replace("\\]", "]")
                    .replace("\\t", "\t")
                    .replace("\\n", "\n")
                    .replace("\\r", "\r")
                    .replace("\\b", "\b");
            return resolver.apply(text);
        }
        return resolver.apply(null);
    }

    public static DataHolder customIntOption(@NotNull String option, @Nullable String params, @NotNull Function<Integer, DataHolder> resolver) {
        int value = -1;
        if (params != null) {
            if (!params.matches("\\d*")) {
                throw new IllegalStateException("'" + option + "' option requires a numeric or empty (for default) argument");
            }

            value = Integer.parseInt(params);
        }

        return resolver.apply(value);
    }

    public static SequenceBuilder insertCaretMarkup(BasedSequence sequence, int[] offsets) {
        SequenceBuilder builder = sequence.getBuilder();
        Arrays.sort(offsets);

        int length = sequence.length();
        int lastOffset = 0;
        for (int offset : offsets) {
            int useOffset = Math.min(length, offset);

            if (useOffset > lastOffset) {
                sequence.subSequence(lastOffset, useOffset).addSegments(builder.getSegmentBuilder());
            }
            if (useOffset == offset) builder.append("⦙");
            lastOffset = useOffset;
        }

        int offset = sequence.length();
        if (offset > lastOffset) {
            sequence.subSequence(lastOffset, offset).addSegments(builder.getSegmentBuilder());
        }

        return builder;
    }

    public static Pair<BasedSequence, int[]> extractMarkup(BasedSequence input) {
        int markup = input.countOfAny(MARKUP_PREDICATE);

        if (markup > 0) {
            int carets = input.countOfAny(CARET_PREDICATE);
            int[] offsets = new int[carets];

            int selections = markup - carets;
            assert selections % 2 == 0;

            int indents = selections / 2;

            int[] starts = new int[indents];
            int[] ends = new int[indents];

            int lastPos = input.length();
            int c = carets;
            int m = markup;
            int i = indents;

            String toWrap = input.toString();
            int endIndent = -1;

            while (lastPos >= 0) {
                int pos = input.lastIndexOfAny(MARKUP_PREDICATE, lastPos);
                if (pos == -1) break;

                char ch = input.charAt(pos);
                m--;
                switch (ch) {
                    case MARKUP_CARET_CHAR:
                        c--;
                        offsets[c] = pos - m; // reduce by number of markups ahead
                        break;

                    case MARKUP_SELECTION_START_CHAR:
                        assert endIndent != -1;
                        i--;
                        starts[i] = pos - m; // reduce by number of markups ahead
                        ends[i] = endIndent; // reduce by number of markups ahead
                        endIndent = -1;
                        break;

                    case MARKUP_SELECTION_END_CHAR:
                        assert endIndent == -1;
                        endIndent = pos - m; // reduce by number of markups ahead
                        break;

                    default:
                        throw new IllegalStateException("Unexpected predicate match");
                }

                toWrap = toWrap.substring(0, pos) + toWrap.substring(pos + 1);
                lastPos = pos - 1;
            }

            assert endIndent == -1;
            assert c == 0 : "Unused caret pos: " + c;
            assert i == 0 : "Unused indent pos: " + i;

//            System.out.println("After markup removal: " + SequenceUtils.toVisibleWhitespaceString(toWrap));

            BasedSequence sequence = BasedSequence.of(toWrap);

            // now we delete the indents to simulate prefix removal
            SequenceBuilder builder = sequence.getBuilder();
            int jMax = starts.length;
            int lastOffset = 0;
            for (int j = 0; j < jMax; j++) {
                int start = starts[j];
                int end = ends[j];

                if (start > lastOffset) {
                    sequence.subSequence(lastOffset, start).addSegments(builder.getSegmentBuilder());
                }
                lastOffset = end;
            }

            int offset = sequence.length();
            if (offset > lastOffset) {
                sequence.subSequence(lastOffset, offset).addSegments(builder.getSegmentBuilder());
            }

//            System.out.println("After removal: " + builder.toStringWithRanges());

            return Pair.of(builder.toSequence(), offsets);
        }

        return Pair.of(input, EMPTY_OFFSETS);
    }

    final public static String BANNER_PADDING = "------------------------------------------------------------------------";
    final public static int BANNER_LENGTH = BANNER_PADDING.length();

    @NotNull
    public static String bannerText(@NotNull String message) {
        int leftPadding = 4; //(BANNER_LENGTH - message.length() - 2) >> 4;
        int rightPadding = BANNER_LENGTH - message.length() - 2 - leftPadding;
        return BANNER_PADDING.substring(0, leftPadding) + " " + message + " " + BANNER_PADDING.substring(0, rightPadding) + "\n";
    }

    public static void appendBanner(@NotNull StringBuilder out, @NotNull String banner) {
        appendBanner(out, banner, true);
    }

    public static void appendBanner(@NotNull StringBuilder out, @NotNull String banner, boolean addBlankLine) {
        if (out.length() > 0 && addBlankLine) {
            out.append("\n");
        }

        out.append(banner);
    }

    public static void appendBannerIfNeeded(@NotNull StringBuilder out, @NotNull String banner) {
        if (out.length() > 0) {
            out.append("\n");
            out.append(banner);
        }
    }
}
