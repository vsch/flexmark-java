package com.vladsch.flexmark.ext.admonition;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.mappers.SpecialLeadInHandler;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

final public class AdmonitionParserTest {
    String escape(String input, Parser parser) {
        BasedSequence baseSeq = BasedSequence.of(input);
        List<SpecialLeadInHandler> handlers = Parser.SPECIAL_LEAD_IN_HANDLERS.get(parser.getOptions());
        StringBuilder sb = new StringBuilder();

        for (SpecialLeadInHandler handler : handlers) {
            if (handler.escape(baseSeq, null, sb::append)) return sb.toString();
        }
        return input;
    }

    String unEscape(String input, Parser parser) {
        BasedSequence baseSeq = BasedSequence.of(input);
        List<SpecialLeadInHandler> handlers = Parser.SPECIAL_LEAD_IN_HANDLERS.get(parser.getOptions());
        StringBuilder sb = new StringBuilder();

        for (SpecialLeadInHandler handler : handlers) {
            if (handler.unEscape(baseSeq, null, sb::append)) return sb.toString();
        }
        return input;
    }

    @Test
    public void test_escape() {
        Parser parser = Parser.builder().extensions(Collections.singleton(AdmonitionExtension.create())).build();

        assertEquals("abc", escape("abc", parser));
        assertEquals("!", escape("!", parser));
        assertEquals("!!", escape("!!", parser));
        assertEquals("!!!!", escape("!!!!", parser));
        assertEquals("!!!-", escape("!!!-", parser));
        assertEquals("?", escape("?", parser));
        assertEquals("??", escape("??", parser));
        assertEquals("????", escape("????", parser));
        assertEquals("???-", escape("???-", parser));

        assertEquals("\\!!!", escape("!!!", parser));
        assertEquals("\\!!!+", escape("!!!+", parser));
        assertEquals("\\???", escape("???", parser));
        assertEquals("\\???+", escape("???+", parser));

        assertEquals("\\abc", unEscape("\\abc", parser));
        assertEquals("\\!", unEscape("\\!", parser));
        assertEquals("\\!!", unEscape("\\!!", parser));
        assertEquals("\\!!!!", unEscape("\\!!!!", parser));
        assertEquals("\\!!!-", unEscape("\\!!!-", parser));
        assertEquals("\\?", unEscape("\\?", parser));
        assertEquals("\\??", unEscape("\\??", parser));
        assertEquals("\\????", unEscape("\\????", parser));
        assertEquals("\\???-", unEscape("\\???-", parser));

        assertEquals("!!!", unEscape("\\!!!", parser));
        assertEquals("!!!+", unEscape("\\!!!+", parser));
        assertEquals("???", unEscape("\\???", parser));
        assertEquals("???+", unEscape("\\???+", parser));
    }
}
