package com.vladsch.flexmark.util.options;

import com.vladsch.flexmark.util.sequence.BasedSequence;

public class ParserMessage {
    protected final BasedSequence source;
    protected final ParsedOptionStatus status;
    protected final String message;

    public ParserMessage(BasedSequence source, ParsedOptionStatus status, String message) {
        this.source = source;
        this.status = status;
        this.message = message;
    }

    public BasedSequence getSource() {
        return source;
    }

    public ParsedOptionStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
