package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.test.util.spec.ResourceLocation;
import com.vladsch.flexmark.test.util.spec.ResourceUrlResolver;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.SegmentedSequence;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.AssumptionViolatedException;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.vladsch.flexmark.util.Utils.*;

public class TestUtils {
    static {
         // CAUTION: need to register our url resolvers
         FlexmarkResourceUrlResolver.registerUrlResolvers();
    }

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

    public static final DataHolder NO_FILE_EOL_FALSE = new MutableDataSet().set(NO_FILE_EOL, false).toImmutable();
    public static final String DEFAULT_SPEC_RESOURCE = "/spec.txt";
    public static final String DEFAULT_URL_PREFIX = "fqn://";  // use class fqn to figure it out
    public static final DataKey<Collection<Class<? extends Extension>>> UNLOAD_EXTENSIONS = LoadUnloadDataKeyAggregator.UNLOAD_EXTENSIONS;
    public static final DataKey<Collection<Extension>> LOAD_EXTENSIONS = LoadUnloadDataKeyAggregator.LOAD_EXTENSIONS;
    final public static DataKey<BiFunction<String, String, DataHolder>> CUSTOM_OPTION = new DataKey<>("CUSTOM_OPTION", (option, params) -> null);
    public static final String FILE_PROTOCOL = ResourceUrlResolver.FILE_PROTOCOL;
    public static final @NotNull ResourceLocation DEFAULT_RESOURCE_LOCATION = ResourceLocation.of(TestUtils.class, TestUtils.DEFAULT_SPEC_RESOURCE, TestUtils.DEFAULT_URL_PREFIX);


    public static DataHolder processOption(@NotNull Map<String, DataHolder> optionsMap, @NotNull String option) {
        DataHolder dataHolder = optionsMap.get(option);
        String customOption = option;
        String params = null;

        if (dataHolder == null) {
            // see if parameterized option
            Pair<String, String> optionPair = parseCustomOption(option);
            if (optionPair != null) {
                // parameterized, see if there is a handler defined for it
                customOption = optionPair.getFirst();
                params = optionPair.getSecond();
                dataHolder = optionsMap.get(customOption);
            }
        }

        // if custom option is set then delegate to it
        if (dataHolder != null && dataHolder.contains(CUSTOM_OPTION)) {
            BiFunction<String, String, DataHolder> customHandler = CUSTOM_OPTION.getFrom(dataHolder);
            dataHolder = customHandler.apply(customOption, params);
        }

        return dataHolder;
    }

    @Nullable
    public static Pair<String, String> parseCustomOption(@NotNull String option) {
        int pos = option.indexOf("[");
        if (pos > 0 && pos < option.length() && option.endsWith("]")) {
            // parameterized, see if there is a handler defined for it
            String customOption = option.substring(0, pos);
            String params = option.substring(pos + 1, option.length() - 1);
            return Pair.of(customOption, params);
        }
        return null;
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
                            throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass\n" + example.getFileUrlWithLineNumber());
                        }
                    } else {
                        DataHolder dataSet = optionsProvider.apply(option);

                        if (dataSet != null) {
                            // CAUTION: have to only aggregate actions here
                            options = DataSet.aggregateActions(options, dataSet);
                        } else {
                            throw new IllegalStateException("Option " + option + " is not implemented in the RenderingTestCase subclass\n" + example.getFileUrlWithLineNumber());
                        }
                    }

                    if (options != null && options.contains(IGNORE) && IGNORE.getFrom(options)) {
                        throwIgnoredOption(example, optionSets, option);
                    }
                    break;
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
        if (example == null) {
            throw new AssumptionViolatedException("Ignored: SpecExample test case options(" + optionSets + ") is using " + option + " option\n" + example.getFileUrlWithLineNumber());
        } else {
            throw new AssumptionViolatedException("Ignored: example(" + example.getSection() + ": " + example.getExampleNumber() + ") options(" + optionSets + ") is using " + option + " option\n" + example.getFileUrlWithLineNumber());
        }
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
        if (includeExampleCoords) { sb.append(header.toString().replace(' ', '\u00A0')); } else sb.append(header.toString());

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
        return String.format(TIMED_FORMAT_STRING, getFormattedSection(section, exampleNumber), (parse - start) / 1000000.0 / iterations, (render - parse) / 1000000.0 / iterations, (render - start) / 1000000.0 / iterations);
    }

    @NotNull
    public static String getFormattedSection(String section, int exampleNumber) {
        return section == null ? "" : section.trim() + ": " + exampleNumber;
    }

    @NotNull
    public static String getSpecResourceName(@NotNull String testClassName, @NotNull String resourcePath) {
        File specInfo = new File(resourcePath);
        File classInfo = new File("/" + testClassName.replace('.', '/'));
        return !specInfo.isAbsolute() ? new File(classInfo.getParent(), resourcePath).getPath() : resourcePath;
    }

    @NotNull
    public static String getSpecResourceFileUrl(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String urlPrefix) {
        if (resourcePath.isEmpty()) {
            throw new IllegalStateException("Empty resource paths not supported");
        } else {
            String resolvedResourcePath = getSpecResourceName(resourceClass.getName(), resourcePath);
            if (urlPrefix.equals(DEFAULT_URL_PREFIX)) {
//            return DEFAULT_URL_PREFIX + resourceClass.getName().replace('.', '/') + "?" + resolvedSpecResource;
                URL url = resourceClass.getResource(resolvedResourcePath);
                return adjustedFileUrl(url);
            }

            return urlPrefix + resolvedResourcePath;
        }
    }

    public static ArrayList<Object[]> getTestData(@NotNull Class<?> resourceClass, @NotNull String resourcePath, @NotNull String urlPrefix) {
        return getTestData(ResourceLocation.of(resourceClass, resourcePath, urlPrefix));
    }

    public static ArrayList<Object[]> getTestData(@NotNull ResourceLocation location) {
        SpecReader specReader = SpecReader.createAndReadExamples(location);
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

    public static String adjustedFileUrl(URL url) {
        return ResourceLocation.adjustedFileUrl(url);
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
    public static Map<String, DataHolder> optionsMaps(@Nullable Map<String, DataHolder> other, @Nullable Map<String, DataHolder> overrides) {
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
        fileUrl = getSpecResourceFileUrl(resourceClass, wrapWith(moduleRootPackage, "/", ".txt"), DEFAULT_URL_PREFIX);
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
}
