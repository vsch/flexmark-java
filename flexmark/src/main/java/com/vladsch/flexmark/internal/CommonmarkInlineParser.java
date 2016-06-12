package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.DataHolder;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.parser.DelimiterProcessor;

import java.util.BitSet;
import java.util.Map;

public class CommonmarkInlineParser extends InlineParserImpl {
    public CommonmarkInlineParser(DataHolder options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors) {
        super(options, specialCharacters, delimiterCharacters, delimiterProcessors);
    }

    @Override
    public void initializeDocument(Document document) {
        super.initializeDocument(document);
    }
}
