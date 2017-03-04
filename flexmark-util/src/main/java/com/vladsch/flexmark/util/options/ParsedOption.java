package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.sequence.BasedSequence;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParsedOption<T> {
    protected final BasedSequence mySource;
    protected final OptionParser<T> myOptionParser;
    protected final ParsedOptionStatus myOptionResult;
    protected final List<ParserMessage> myMessages;

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
        mySource = source;

        if (parsedOptions != null) {
            ArrayList<ParserMessage> mergedMessages = messages != null ? new ArrayList<ParserMessage>(messages) : null;

            for (ParsedOption<T> parsedOption : parsedOptions) {
                optionResult = optionResult.escalate(parsedOption.getOptionResult());
                if (parsedOption.getMessages() != null) {
                    if (mergedMessages == null) mergedMessages = new ArrayList<ParserMessage>();
                    mergedMessages.addAll(parsedOption.getMessages());
                }
            }
            messages = mergedMessages;
        }

        myOptionParser = optionParser;
        myOptionResult = optionResult;
        myMessages = messages;
    }

    public BasedSequence getSource() {
        return mySource;
    }

    public OptionParser<T> getOptionParser() {
        return myOptionParser;
    }

    public ParsedOptionStatus getOptionResult() {
        return myOptionResult;
    }

    public List<ParserMessage> getMessages() {
        return myMessages;
    }
}
