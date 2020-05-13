package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.util.misc.DelimitedBuilder;
import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Utils;
import com.vladsch.flexmark.util.options.*;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class TocLevelsOptionParser implements OptionParser<TocOptions> {
    final public static String OPTION_0_VALUE_1_NOT_IN_RANGE = "{0} option value {1} is not an integer in the range [1, 6]";
    final public static String KEY_OPTION_0_VALUE_1_NOT_IN_RANGE = "options.parser.toc-levels-option.not-in-range";
    final public static String OPTION_0_VALUE_1_NOT_INTEGER = "{0} option value {1} is not an integer";
    final public static String KEY_OPTION_0_VALUE_1_NOT_INTEGER = "options.parser.toc-levels-option.not-integer";
    final public static String OPTION_0_VALUE_1_TRUNCATED_TO_RANGE_2 = "{0} option value {1} truncated to range [{2}]";
    final public static String KEY_OPTION_0_VALUE_1_TRUNCATED_TO_RANGE_2 = "options.parser.toc-levels-option.truncated-to-range";
    final public static String OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE = "{0} option value {1} truncated to empty range []";
    final public static String KEY_OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE = "options.parser.toc-levels-option.truncated-to-empty";
    final private String myOptionName;

    final private static Map<Integer, String> TOC_LEVELS_MAP = new HashMap<>();
    static {
        TOC_LEVELS_MAP.put(0x04, "2");
        TOC_LEVELS_MAP.put(0x0C, "3");
        TOC_LEVELS_MAP.put(0x1C, "4");
        TOC_LEVELS_MAP.put(0x3C, "5");
        TOC_LEVELS_MAP.put(0x7C, "6");
        TOC_LEVELS_MAP.put(1 << 1, "1");
        //TOC_LEVELS_MAP.put(1 << 2, "2,2");
        TOC_LEVELS_MAP.put(1 << 3, "3,3");
        TOC_LEVELS_MAP.put(1 << 4, "4,4");
        TOC_LEVELS_MAP.put(1 << 5, "5,5");
        TOC_LEVELS_MAP.put(1 << 6, "6,6");
    }
    
    public TocLevelsOptionParser(String optionName) {
        this.myOptionName = optionName;
    }

    @Override
    public String getOptionName() {
        return myOptionName;
    }

    @Override
    public Pair<TocOptions, List<ParsedOption<TocOptions>>> parseOption(BasedSequence optionText, TocOptions options, MessageProvider provider) {
        // may have levels
        TocOptions result = options;
        BasedSequence[] levelsOptionValue = optionText.split(",");
        final ParserParams parserParams = new ParserParams();

        if (provider == null) provider = MessageProvider.DEFAULT;

        int newLevels = 0;
        int i = 0;

        final MessageProvider finalProvider = provider;
        Function<BasedSequence, Integer> convertWithMessage = option -> {
            try {
                return option.isEmpty() ? null : Integer.parseInt(option.toString());
            } catch (Exception ignored) {
                parserParams.add(new ParserMessage(option, ParsedOptionStatus.ERROR, finalProvider.message(KEY_OPTION_0_VALUE_1_NOT_INTEGER, OPTION_0_VALUE_1_NOT_INTEGER, myOptionName, option)));
                parserParams.skip = true;
                return null;
            }
        };

        for (BasedSequence option : levelsOptionValue) {
            BasedSequence[] optionRange = option.split("-", 2, BasedSequence.SPLIT_TRIM_PARTS | BasedSequence.SPLIT_INCLUDE_DELIM_PARTS);

            Integer rangeStart;
            Integer rangeEnd;
            parserParams.skip = false;

            if (optionRange.length >= 2) {
                rangeStart = convertWithMessage.apply(optionRange[0]);
                rangeEnd = optionRange.length >= 3 ? convertWithMessage.apply(optionRange[2]) : null;
                if (rangeStart == null) rangeStart = 1;
                if (rangeEnd == null) rangeEnd = 6;
            } else {
                // NOTE: 1 means heading level 1 only, 2 means 2 only, rest mean 2-x
                Integer optionValue = convertWithMessage.apply(optionRange[0]);
                if (optionValue != null && optionValue <= 2) {
                    rangeStart = optionValue;
                    rangeEnd = rangeStart;
                } else {
                    rangeStart = optionValue == null ? null : 2;
                    rangeEnd = optionValue;
                }
            }

            if (!parserParams.skip) {
                if (rangeStart == null) {
                    parserParams.add(new ParserMessage(option, ParsedOptionStatus.IGNORED, finalProvider.message(KEY_OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE, OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE, myOptionName, option)));
                } else {
                    if (rangeEnd < rangeStart) {
                        int tmp = rangeStart;
                        rangeStart = rangeEnd;
                        rangeEnd = tmp;
                    }

                    if (rangeEnd < 1 || rangeStart > 6) {
                        if (rangeStart == (int) rangeEnd) {
                            parserParams.add(new ParserMessage(option, ParsedOptionStatus.IGNORED, provider.message(KEY_OPTION_0_VALUE_1_NOT_IN_RANGE, OPTION_0_VALUE_1_NOT_IN_RANGE, myOptionName, option)));
                        } else {
                            parserParams.add(new ParserMessage(option, ParsedOptionStatus.WARNING, finalProvider.message(KEY_OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE, OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE, myOptionName, option)));
                        }
                    } else {
                        int wasStart = rangeStart;
                        int wasEnd = rangeEnd;
                        rangeStart = Utils.minLimit(rangeStart, 1);
                        rangeEnd = Utils.maxLimit(rangeEnd, 6);
                        if (wasStart != rangeStart || wasEnd != rangeEnd) {
                            parserParams.add(new ParserMessage(option, ParsedOptionStatus.WEAK_WARNING, finalProvider.message(KEY_OPTION_0_VALUE_1_TRUNCATED_TO_RANGE_2, OPTION_0_VALUE_1_TRUNCATED_TO_RANGE_2, myOptionName, option, rangeStart + ", " + rangeEnd)));
                        }
                        for (int b = rangeStart; b <= rangeEnd; b++) newLevels = newLevels | (1 << b);
                    }
                }
            }
            i++;
        }

        if (newLevels != 0) result = result.withLevels(newLevels);

        return new Pair<>(result, (List<ParsedOption<TocOptions>>) Collections.<ParsedOption<TocOptions>>singletonList(new ParsedOption<>(optionText, this, parserParams.status, parserParams.messages)));
    }

    @Override
    public String getOptionText(TocOptions options, TocOptions defaultOptions) {
        if (defaultOptions == null || options.levels != defaultOptions.levels) {
            DelimitedBuilder out = new DelimitedBuilder();
            out.append("levels=");

            String fixedLevels = TOC_LEVELS_MAP.get(options.levels);

            if (fixedLevels != null) {
                out.append(fixedLevels).mark();
            } else {
                out.push(",");

                int firstBit = 0;
                int lastBit = 0;
                for (int i = 1; i <= 6; i++) {
                    if (options.isLevelIncluded(i)) {
                        if (firstBit == 0) {
                            firstBit = i;
                            lastBit = i;
                        } else {
                            if (lastBit + 1 != i) {
                                if (firstBit != lastBit) {
                                    if (firstBit + 1 == lastBit) out.append(firstBit).mark().append(lastBit).mark();
                                    else out.append(firstBit).append('-').append(lastBit).mark();
                                } else {
                                    out.append(firstBit).mark();
                                }
                                firstBit = i;
                                lastBit = i;
                            } else {
                                lastBit = i;
                            }
                        }
                    }
                }

                if (firstBit != 0) {
                    if (firstBit != lastBit) {
                        if (firstBit == 2) out.append(lastBit).mark();
                        else if (firstBit + 1 == lastBit) out.append(firstBit).mark().append(lastBit).mark();
                        else out.append(firstBit).append('-').append(lastBit).mark();
                    } else {
                        out.append(firstBit).mark();
                    }
                }

                out.pop().mark();
            }
            return out.toString();
        }
        return "";
    }
}
