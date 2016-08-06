package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.ast.Document;
import com.vladsch.flexmark.ast.util.Parsing;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.util.options.DataHolder;

import java.util.BitSet;
import java.util.Map;

public class CommonmarkInlineParser extends InlineParserImpl {
    public CommonmarkInlineParser(DataHolder options, BitSet specialCharacters, BitSet delimiterCharacters,
            Map<Character, DelimiterProcessor> delimiterProcessors, LinkRefProcessorData referenceLinkProcessors) {
        super(options, specialCharacters, delimiterCharacters, delimiterProcessors, referenceLinkProcessors);
    }

    @Override
    public void initializeDocument(Parsing parsing, Document document) {
        super.initializeDocument(parsing, document);
    }
}
