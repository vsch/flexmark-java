package com.vladsch.flexmark.util.options;

import java.util.ArrayList;
import java.util.List;

public class ParserParams {
  public List<ParserMessage> messages = null;
  public boolean skip = false;
  public ParsedOptionStatus status = ParsedOptionStatus.VALID;

  public ParserParams add(ParserMessage message) {
    if (messages == null) messages = new ArrayList<>();
    messages.add(message);
    escalate(message.getStatus());
    return this;
  }

  public ParserParams escalate(ParsedOptionStatus other) {
    status = status.escalate(other);
    return this;
  }
}
