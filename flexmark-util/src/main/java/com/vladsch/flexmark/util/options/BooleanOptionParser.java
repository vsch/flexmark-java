package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Collections;
import java.util.List;

public abstract class BooleanOptionParser<T> implements OptionParser<T> {
    public static final String OPTION_0_PARAMETERS_1_IGNORED = "Option {0} does not have any parameters. {1} was ignored";
    public static final String KEY_OPTION_0_PARAMETERS_1_IGNORED = "options.parser.boolean-option.ignored";
    private final String myOptionName;

    public BooleanOptionParser(String optionName) {
        myOptionName = optionName;
    }

    abstract protected T setOptions(T options);
    abstract protected boolean isOptionSet(T options);

    @Override
    public String getOptionName() {
        return myOptionName;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Pair<T, List<ParsedOption<T>>> parseOption(BasedSequence optionText, T options, MessageProvider provider) {
        if (optionText.isEmpty()) {
            return new Pair<T, List<ParsedOption<T>>>(setOptions(options), (List<ParsedOption<T>>)Collections.<ParsedOption<T>>singletonList(new ParsedOption(optionText, this, ParsedOptionStatus.VALID)));
        } else {
            if (provider == null) provider = MessageProvider.DEFAULT;
            String message = provider.message(KEY_OPTION_0_PARAMETERS_1_IGNORED, OPTION_0_PARAMETERS_1_IGNORED, myOptionName, optionText);
            return new Pair<T, List<ParsedOption<T>>>(setOptions(options), (List<ParsedOption<T>>)Collections.<ParsedOption<T>>singletonList(new ParsedOption(optionText, this, ParsedOptionStatus.IGNORED, Collections.singletonList(new ParserMessage(optionText, ParsedOptionStatus.IGNORED, message)))));
        }
    }

    @Override
    public String getOptionText(T options, T defaultOptions) {
        return isOptionSet(options) && (defaultOptions == null || !isOptionSet(defaultOptions)) ? myOptionName : "";
    }
}
