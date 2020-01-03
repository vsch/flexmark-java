package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.List;

public interface OptionParser<T> {
    String getOptionName();
    Pair<T, List<ParsedOption<T>>> parseOption(BasedSequence optionText, T options, MessageProvider provider);
    String getOptionText(T options, T defaultOptions);
}
