package com.vladsch.flexmark.internal;

import com.vladsch.flexmark.internal.util.ModificationBehavior;
import com.vladsch.flexmark.internal.util.Options;
import com.vladsch.flexmark.node.Document;
import com.vladsch.flexmark.parser.DelimiterProcessor;

import java.util.BitSet;
import java.util.Map;

public class CommonmarkInlineParser extends InlineParserImpl {
    public CommonmarkInlineParser(Options options, BitSet specialCharacters, BitSet delimiterCharacters, Map<Character, DelimiterProcessor> delimiterProcessors) {
        super(options, specialCharacters, delimiterCharacters, delimiterProcessors);
    }

    @Override
    public void setDocument(Document document) {
        super.setDocument(document);
        referenceRepository.setModifyBehavior(ModificationBehavior.KEEP_FIRST);
    }
}
