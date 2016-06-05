package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.parser.DelimiterProcessor;

import java.util.BitSet;
import java.util.Map;

public class CommonmarkInlineParser extends InlineParserImpl {
    public CommonmarkInlineParser(BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors) {
        super(specialCharacters, delimiterCharacters, delimiterProcessors);
    }
}
