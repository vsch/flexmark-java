package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.DelimitedBuilder;
import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.List;

public class OptionsParser<T> implements OptionParser<T> {
    public static final String OPTION_0_IS_AMBIGUOUS = "Option {0} matches: ";
    public static final String KEY_OPTION_0_IS_AMBIGUOUS = "options.parser.option.ambiguous";
    public static final String OPTION_0_DOES_NOT_MATCH = "Option {0} does not match any of: ";
    public static final String KEY_OPTION_0_DOES_NOT_MATCH = "options.parser.option.unknown";

    private final String myOptionName;
    private final OptionParser<T>[] myParsableOptions;
    private final char myOptionDelimiter;
    private final char myOptionValueDelimiter;

    public OptionsParser(String optionName, OptionParser<T>[] parsableOptions, char optionDelimiter, char optionValueDelimiter) {
        myOptionName = optionName;
        myParsableOptions = parsableOptions;
        myOptionDelimiter = optionDelimiter;
        myOptionValueDelimiter = optionValueDelimiter;
    }

    @Override
    public String getOptionName() {
        return myOptionName;
    }

    @Override
    public Pair<T, List<ParsedOption<T>>> parseOption(BasedSequence optionsText, T options, MessageProvider provider) {
        ArrayList<ParserMessage> messages = null;
        BasedSequence[] optionsList = optionsText.split(myOptionDelimiter, 0, BasedSequence.SPLIT_TRIM_SKIP_EMPTY);
        T result = options;
        if (provider == null) provider = MessageProvider.DEFAULT;
        List<ParsedOption<T>> parsedOptions = new ArrayList<ParsedOption<T>>(optionsList.length);

        for (BasedSequence optionText : optionsList) {
            OptionParser<T> matched = null;
            DelimitedBuilder message = null;

            BasedSequence[] optionList = optionText.split(myOptionValueDelimiter, 2, BasedSequence.SPLIT_SKIP_EMPTY);
            if (optionList.length == 0) continue;
            BasedSequence optionName = optionList[0];
            BasedSequence optionValue = optionList.length > 1 ? optionList[1] : optionName.subSequence(optionName.length(), optionName.length());

            for (OptionParser<T> optionParser : myParsableOptions) {
                if (optionParser.getOptionName().equals(optionName.toString())) {
                    matched = optionParser;
                    message = null;
                    break;
                }
                if (optionParser.getOptionName().startsWith(optionName.toString())) {
                    if (matched == null) {
                        matched = optionParser;
                    } else {
                        if (message == null) {
                            message = new DelimitedBuilder(", ");
                            message.append(provider.message(KEY_OPTION_0_IS_AMBIGUOUS, OPTION_0_IS_AMBIGUOUS, optionName));
                            message.append(matched.getOptionName()).mark();
                        }
                        message.append(optionParser.getOptionName()).mark();
                    }
                }
            }

            // have our match
            if (matched != null) {
                if (message == null) {
                    Pair<T, List<ParsedOption<T>>> pair = matched.parseOption(optionValue, result, provider);
                    result = pair.getFirst();
                    parsedOptions.add(new ParsedOption<T>(optionText, this, ParsedOptionStatus.VALID, null, pair.getSecond()));
                } else {
                    parsedOptions.add(new ParsedOption<T>(optionText, this, ParsedOptionStatus.ERROR, new ParserMessage(optionName, ParsedOptionStatus.ERROR, message.toString())));
                }
            } else {
                message = new DelimitedBuilder(", ");
                message.append(provider.message(KEY_OPTION_0_DOES_NOT_MATCH, OPTION_0_DOES_NOT_MATCH, optionName));
                appendOptionNames(message);
                parsedOptions.add(new ParsedOption<T>(optionText, this, ParsedOptionStatus.ERROR, new ParserMessage(optionName, ParsedOptionStatus.ERROR, message.toString())));
            }
        }
        return new Pair<T, List<ParsedOption<T>>>(result, parsedOptions);
    }

    public void appendOptionNames(DelimitedBuilder out) {
        for (OptionParser<T> parsableOption : myParsableOptions) {
            out.append(parsableOption.getOptionName()).mark();
        }
    }

    @Override
    public String getOptionText(T options, T defaultOptions) {
        DelimitedBuilder out = new DelimitedBuilder(String.valueOf(myOptionDelimiter));
        for (OptionParser<T> parsableOption : myParsableOptions) {
            String text = parsableOption.getOptionText(options, defaultOptions).trim();
            if (!text.isEmpty()) out.append(text).mark();
        }
        return out.toString();
    }
}
