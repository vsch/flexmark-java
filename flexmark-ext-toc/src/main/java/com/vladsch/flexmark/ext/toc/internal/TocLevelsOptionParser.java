/*
 * Copyright (c) 2015-2016 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */

package com.vladsch.flexmark.ext.toc.internal;

import com.vladsch.flexmark.internal.util.Computable;
import com.vladsch.flexmark.internal.util.Pair;
import com.vladsch.flexmark.internal.util.sequence.BasedSequence;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TocLevelsOptionParser implements OptionParser<TocOptions> {
    public static final String OPTION_0_VALUE_1_NOT_IN_RANGE = "{0} option value {1} is not an integer in the range [1, 6]";
    public static final String KEY_OPTION_0_VALUE_1_NOT_IN_RANGE = "options.parser.toc-levels-option.not-in-range";
    public static final String OPTION_0_VALUE_1_NOT_INTEGER = "{0} option value {1} is not an integer";
    public static final String KEY_OPTION_0_VALUE_1_NOT_INTEGER = "options.parser.toc-levels-option.not-integer";
    public static final String OPTION_0_VALUE_1_TRUNCATED_TO_RANGE_2 = "{0} option value {1} truncated to range [{2}]";
    public static final String KEY_OPTION_0_VALUE_1_TRUNCATED_TO_RANGE_2 = "options.parser.toc-levels-option.truncated-to-range";
    public static final String OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE = "{0} option value {1} truncated to empty range []";
    public static final String KEY_OPTION_0_VALUE_1_TRUNCATED_TO_EMPTY_RANGE = "options.parser.toc-levels-option.truncated-to-empty";
    private final String myOptionName;

    final private static Map<Integer, String> TOC_LEVELS_MAP = new HashMap<Integer, String>();
    static {
        TOC_LEVELS_MAP.put(0x04, "2");
        TOC_LEVELS_MAP.put(0x0C, "3");
        TOC_LEVELS_MAP.put(0x1C, "4");
        TOC_LEVELS_MAP.put(0x3C, "5");
        TOC_LEVELS_MAP.put(0x7C, "6");
        TOC_LEVELS_MAP.put(1 << 1, "1");
        TOC_LEVELS_MAP.put(1 << 2, "2,2");
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

    @SuppressWarnings("unchecked")
    @Override
    public Pair<TocOptions, List<ParsedOption<TocOptions>>> parseOption(BasedSequence optionText, TocOptions options, MessageProvider provider) {
        // may have levels
        TocOptions result = options;
        List<BasedSequence> levelsOptionValue = optionText.split(',');
        final ParserParams parserParams = new ParserParams();

        if (provider == null) provider = MessageProvider.DEFAULT;

        int newLevels = 0;
        int i = 0;

        MessageProvider finalProvider = provider;
        Computable<Integer, BasedSequence> convertWithMessage = (BasedSequence option) -> {
            try {
                return option.isEmpty() ? null : Integer.parseInt(option.toString());
            } catch (Exception ignored) {
                parserParams.add(new ParserMessage(option, ParsedOptionStatus.ERROR, finalProvider.message(KEY_OPTION_0_VALUE_1_NOT_INTEGER, OPTION_0_VALUE_1_NOT_INTEGER, myOptionName, option)));
                parserParams.skip = true;
                return null;
            }
        };

        for (BasedSequence option : levelsOptionValue) {
            List<BasedSequence> optionRange = option.split('-', 2, BasedSequence.SPLIT_TRIM_PARTS);

            Integer rangeStart;
            Integer rangeEnd;
            parserParams.skip = false;

            if (optionRange.size() == 2) {
                rangeStart = convertWithMessage.compute(optionRange.get(0));
                rangeEnd = convertWithMessage.compute(optionRange.get(1));
                if (rangeStart == null) rangeStart = 2;
                if (rangeEnd == null) rangeEnd = 6;
            } else {
                rangeStart = convertWithMessage.compute(optionRange.get(0));
                rangeEnd = rangeStart;
            }

            if (!parserParams.skip) {
                if (rangeStart == null && rangeEnd == null) {
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

        return new Pair<>(result, Collections.<ParsedOption<TocOptions>>singletonList(new ParsedOption(optionText, this, parserParams.status, parserParams.messages)));
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
