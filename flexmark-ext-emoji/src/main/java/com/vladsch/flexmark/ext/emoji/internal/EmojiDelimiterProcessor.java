package com.vladsch.flexmark.ext.emoji.internal;

import com.vladsch.flexmark.ext.emoji.Emoji;
import com.vladsch.flexmark.internal.Delimiter;
import com.vladsch.flexmark.parser.delimiter.DelimiterProcessor;
import com.vladsch.flexmark.parser.delimiter.DelimiterRun;
import com.vladsch.flexmark.util.sequence.SubSequence;

public class EmojiDelimiterProcessor implements DelimiterProcessor {

    @Override
    public char getOpeningCharacter() {
        return ':';
    }

    @Override
    public char getClosingCharacter() {
        return ':';
    }

    @Override
    public int getMinLength() {
        return 1;
    }

    @Override
    public int getDelimiterUse(DelimiterRun opener, DelimiterRun closer) {
        if (opener.length() >= 1 && closer.length() >= 1) {
            return 1;
        } else {
            return 1;
        }
    }

    @Override
    public void process(Delimiter opener, Delimiter closer, int delimitersUsed) {
        // Normal case, wrap nodes between delimiters in emoji ast.
        Emoji emoji = new Emoji(opener.getTailChars(delimitersUsed), SubSequence.NULL, closer.getLeadChars(delimitersUsed));
        opener.moveNodesBetweenDelimitersTo(emoji, closer);
    }
}
