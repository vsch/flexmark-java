package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParsedOption<T> {
    protected final BasedSequence source;
    protected final OptionParser<T> optionParser;
    protected final ParsedOptionStatus optionResult;
    protected final List<ParserMessage> messages;

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult) {
        this(source, optionParser, optionResult, (List<ParserMessage>) null);
    }

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult, ParserMessage message) {
        this(source, optionParser, optionResult, Collections.singletonList(message));
    }

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult, List<ParserMessage> messages) {
        this(source, optionParser, optionResult, messages, null);
    }

    public ParsedOption(BasedSequence source, OptionParser<T> optionParser, ParsedOptionStatus optionResult, List<ParserMessage> messages, List<ParsedOption<T>> parsedOptions) {
        this.source = source;

        if (parsedOptions != null) {
            ArrayList<ParserMessage> mergedMessages = messages != null ? new ArrayList<>(messages) : null;

            for (ParsedOption<T> parsedOption : parsedOptions) {
                optionResult = optionResult.escalate(parsedOption.getOptionResult());
                if (parsedOption.getMessages() != null) {
                    if (mergedMessages == null) mergedMessages = new ArrayList<>();
                    mergedMessages.addAll(parsedOption.getMessages());
                }
            }
            messages = mergedMessages;
        }

        this.optionParser = optionParser;
        this.optionResult = optionResult;
        this.messages = messages;
    }

    public BasedSequence getSource() {
        return source;
    }

    public OptionParser<T> getOptionParser() {
        return optionParser;
    }

    public ParsedOptionStatus getOptionResult() {
        return optionResult;
    }

    public List<ParserMessage> getMessages() {
        return messages;
    }
}
