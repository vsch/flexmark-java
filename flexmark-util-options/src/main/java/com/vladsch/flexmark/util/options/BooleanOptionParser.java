package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.Collections;
import java.util.List;

public abstract class BooleanOptionParser<T> implements OptionParser<T> {
    final public static String OPTION_0_PARAMETERS_1_IGNORED = "Option {0} does not have any parameters. {1} was ignored";
    final public static String KEY_OPTION_0_PARAMETERS_1_IGNORED = "options.parser.boolean-option.ignored";
    final private String optionName;

    public BooleanOptionParser(String optionName) {
        this.optionName = optionName;
    }

    abstract protected T setOptions(T options);
    abstract protected boolean isOptionSet(T options);

    @Override
    public String getOptionName() {
        return optionName;
    }

    @Override
    public Pair<T, List<ParsedOption<T>>> parseOption(BasedSequence optionText, T options, MessageProvider provider) {
        if (optionText.isEmpty()) {
            return new Pair<>(setOptions(options), Collections.singletonList(new ParsedOption<>(optionText, this, ParsedOptionStatus.VALID)));
        } else {
            if (provider == null) provider = MessageProvider.DEFAULT;
            String message = provider.message(KEY_OPTION_0_PARAMETERS_1_IGNORED, OPTION_0_PARAMETERS_1_IGNORED, optionName, optionText);
            return new Pair<>(setOptions(options), Collections.singletonList(new ParsedOption<>(optionText, this, ParsedOptionStatus.IGNORED, Collections.singletonList(new ParserMessage(optionText, ParsedOptionStatus.IGNORED, message)))));
        }
    }

    @Override
    public String getOptionText(T options, T defaultOptions) {
        return isOptionSet(options) && (defaultOptions == null || !isOptionSet(defaultOptions)) ? optionName : "";
    }
}
