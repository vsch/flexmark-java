package com.vladsch.flexmark.core.test.util.parser;

import com.vladsch.flexmark.parser.internal.LinkDestinationParser;
import com.vladsch.flexmark.util.sequence.CharSubSequence;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LinkDestinationParserTest {
    LinkDestinationParser noParenParser;
    LinkDestinationParser normalParser;
    LinkDestinationParser spacesParser;
    LinkDestinationParser jekyllParser;
    LinkDestinationParser jekyllSpacesParser;

    @Before
    public void setUp() throws Exception {
        normalParser = new LinkDestinationParser(true, false, false, false);
        noParenParser = new LinkDestinationParser(false, false, false, false);
        spacesParser = new LinkDestinationParser(true, true, false, false);
        jekyllParser = new LinkDestinationParser(true, false, true, false);
        jekyllSpacesParser = new LinkDestinationParser(true, true, true, false);
    }

    // @formatter:off
    @Test public void test_Normal1() { assertEquals("", normalParser.parseLinkDestination(CharSubSequence.of(""), 0).toString()); }
    @Test public void test_Normal2() { assertEquals("abc", normalParser.parseLinkDestination(CharSubSequence.of("abc"), 0).toString()); }
    @Test public void test_Normal3() { assertEquals("bc", normalParser.parseLinkDestination(CharSubSequence.of("abc"), 1).toString()); }
    @Test public void test_Normal4() { assertEquals("bc", normalParser.parseLinkDestination(CharSubSequence.of("abc \""), 1).toString()); }
    @Test public void test_Normal5() { assertEquals("bc", normalParser.parseLinkDestination(CharSubSequence.of("abc '"), 1).toString()); }
    @Test public void test_Normal6() { assertEquals("abc", normalParser.parseLinkDestination(CharSubSequence.of("abc)"), 0).toString()); }
    @Test public void test_Normal7() { assertEquals("(abc)", normalParser.parseLinkDestination(CharSubSequence.of("(abc)"), 0).toString()); }
    @Test public void test_Normal8() { assertEquals("\\(abc", normalParser.parseLinkDestination(CharSubSequence.of("\\(abc)"), 0).toString()); }
    @Test public void test_Normal9() { assertEquals("abc\\", normalParser.parseLinkDestination(CharSubSequence.of("abc\\ "), 0).toString()); }
    @Test public void test_Normal10() { assertEquals("((abc))", normalParser.parseLinkDestination(CharSubSequence.of("((abc))"), 0).toString()); }

    @Test public void test_NoParen1() { assertEquals("", noParenParser.parseLinkDestination(CharSubSequence.of(""), 0).toString()); }
    @Test public void test_NoParen2() { assertEquals("abc", noParenParser.parseLinkDestination(CharSubSequence.of("abc"), 0).toString()); }
    @Test public void test_NoParen3() { assertEquals("bc", noParenParser.parseLinkDestination(CharSubSequence.of("abc"), 1).toString()); }
    @Test public void test_NoParen4() { assertEquals("bc", noParenParser.parseLinkDestination(CharSubSequence.of("abc \""), 1).toString()); }
    @Test public void test_NoParen5() { assertEquals("bc", noParenParser.parseLinkDestination(CharSubSequence.of("abc '"), 1).toString()); }
    @Test public void test_NoParen6() { assertEquals("abc", noParenParser.parseLinkDestination(CharSubSequence.of("abc)"), 0).toString()); }
    @Test public void test_NoParen7() { assertEquals("(abc)", noParenParser.parseLinkDestination(CharSubSequence.of("(abc)"), 0).toString()); }
    @Test public void test_NoParen8() { assertEquals("\\(abc", noParenParser.parseLinkDestination(CharSubSequence.of("\\(abc)"), 0).toString()); }
    @Test public void test_NoParen9() { assertEquals("abc\\", noParenParser.parseLinkDestination(CharSubSequence.of("abc\\ "), 0).toString()); }
    @Test public void test_NoParen10() {assertEquals("", noParenParser.parseLinkDestination(CharSubSequence.of("((abc))"), 0).toString()); }
    @Test public void test_NoParen11() { assertEquals("", noParenParser.parseLinkDestination(CharSubSequence.of("(abc()"), 0).toString()); }
    @Test public void test_NoParen12() { assertEquals("", noParenParser.parseLinkDestination(CharSubSequence.of("(abc(())"), 0).toString()); }

    @Test public void test_Spaces1() { assertEquals("ab c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c"), 0).toString()); }
    @Test public void test_Spaces2() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c"), 1).toString()); }
    @Test public void test_Spaces3() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c "), 1).toString()); }
    @Test public void test_Spaces4() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c  "), 1).toString()); }
    @Test public void test_Spaces5() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c \""), 1).toString()); }
    @Test public void test_Spaces6() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c '"), 1).toString()); }
    @Test public void test_Spaces7() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c  \""), 1).toString()); }
    @Test public void test_Spaces8() { assertEquals("b c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c  '"), 1).toString()); }
    @Test public void test_Spaces9() { assertEquals("ab c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c)"), 0).toString()); }
    @Test public void test_Spaces10() { assertEquals("ab c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c) \""), 0).toString()); }
    @Test public void test_Spaces11() { assertEquals("ab c", spacesParser.parseLinkDestination(CharSubSequence.of("ab c) '"), 0).toString()); }
    @Test public void test_Spaces12() { assertEquals("(ab c)", spacesParser.parseLinkDestination(CharSubSequence.of("(ab c)"), 0).toString()); }
    @Test public void test_Spaces13() { assertEquals("\\(ab c", spacesParser.parseLinkDestination(CharSubSequence.of("\\(ab c)"), 0).toString()); }
    @Test public void test_Spaces14() { assertEquals("(ab c)", spacesParser.parseLinkDestination(CharSubSequence.of("(ab c) \""), 0).toString()); }
    @Test public void test_Spaces15() { assertEquals("\\(ab c", spacesParser.parseLinkDestination(CharSubSequence.of("\\(ab c) '"), 0).toString()); }

    @Test public void test_Jekyll1() { assertEquals("{{macro}}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{macro}}abc"), 0).toString()); }
    @Test public void test_Jekyll2() { assertEquals("{{ macro }}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ macro }}abc"), 0).toString()); }
    @Test public void test_Jekyll3() { assertEquals("{{macro1}}ab{{macro2}}c", jekyllParser.parseLinkDestination(CharSubSequence.of("{{macro1}}ab{{macro2}}c"), 0).toString()); }
    @Test public void test_Jekyll4() { assertEquals("{{ macro1 }}ab{{ macro2 }}c", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ macro1 }}ab{{ macro2 }}c"), 0).toString()); }
    @Test public void test_Jekyll5() { assertEquals("\\{{macro}}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("\\{{macro}}abc"), 0).toString()); }
    @Test public void test_Jekyll6() { assertEquals("\\{{", jekyllParser.parseLinkDestination(CharSubSequence.of("\\{{ macro }}abc"), 0).toString()); }
    @Test public void test_Jekyll7() { assertEquals("{{ma(cro}}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ma(cro}}abc)"), 0).toString()); }
    @Test public void test_Jekyll8() { assertEquals("{{ ma(cro }}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ ma(cro }}abc)"), 0).toString()); }
    @Test public void test_Jekyll9() { assertEquals("{{ma(cro)}}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ma(cro)}}abc)"), 0).toString()); }
    @Test public void test_Jekyll10() { assertEquals("{{ ma(cro) }}abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ ma(cro) }}abc)"), 0).toString()); }
    @Test public void test_Jekyll11() { assertEquals("{{ma(croabc)", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ma(croabc)"), 0).toString()); }
    @Test public void test_Jekyll12() { assertEquals("{{ma(croabc)", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ma(croabc))"), 0).toString()); }
    @Test public void test_Jekyll13() { assertEquals("{{ma(cro)abc", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ma(cro)abc)"), 0).toString()); }
    @Test public void test_Jekyll14() { assertEquals("{{", jekyllParser.parseLinkDestination(CharSubSequence.of("{{ ma(cro) abc)"), 0).toString()); }
    @Test public void test_Jekyll15() { assertEquals("({{ma(cro)abc)", jekyllParser.parseLinkDestination(CharSubSequence.of("({{ma(cro)abc)"), 0).toString()); }
    @Test public void test_Jekyll16() { assertEquals("({{ma(cro)", jekyllParser.parseLinkDestination(CharSubSequence.of("({{ma(cro) abc)"), 0).toString()); }
    @Test public void test_Jekyll17() { assertEquals("({{ma(cro)abc)", jekyllParser.parseLinkDestination(CharSubSequence.of("({{ma(cro)abc))"), 0).toString()); }
    @Test public void test_Jekyll18() { assertEquals("({{ma(cro)", jekyllParser.parseLinkDestination(CharSubSequence.of("({{ma(cro) abc))"), 0).toString()); }
    @Test public void test_Jekyll19() { assertEquals("({{ma(cro)abc)", jekyllParser.parseLinkDestination(CharSubSequence.of("({{ma(cro)abc))"), 0).toString()); }
    @Test public void test_Jekyll20() { assertEquals("({{ma(cro)", jekyllParser.parseLinkDestination(CharSubSequence.of("({{ma(cro) abc))"), 0).toString()); }
    @Test public void test_Jekyll21() { assertEquals("(({{ma(cro)", jekyllParser.parseLinkDestination(CharSubSequence.of("(({{ma(cro) abc))"), 0).toString()); }

    @Test public void test_JekyllSpaces1() { assertEquals("{{macro}}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{macro}}abc"), 0).toString()); }
    @Test public void test_JekyllSpaces2() { assertEquals("{{ macro }}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ macro }}abc"), 0).toString()); }
    @Test public void test_JekyllSpaces3() { assertEquals("{{macro1}}ab{{macro2}}c", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{macro1}}ab{{macro2}}c"), 0).toString()); }
    @Test public void test_JekyllSpaces4() { assertEquals("{{ macro1 }}ab{{ macro2 }}c", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ macro1 }}ab{{ macro2 }}c"), 0).toString()); }
    @Test public void test_JekyllSpaces5() { assertEquals("\\{{macro}}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("\\{{macro}}abc"), 0).toString()); }
    @Test public void test_JekyllSpaces6() { assertEquals("\\{{ macro }}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("\\{{ macro }}abc"), 0).toString()); }
    @Test public void test_JekyllSpaces7() { assertEquals("{{ma(cro}}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ma(cro}}abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces8() { assertEquals("{{ ma(cro }}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ ma(cro }}abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces9() { assertEquals("{{ma(cro)}}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ma(cro)}}abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces10() { assertEquals("{{ ma(cro) }}abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ ma(cro) }}abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces11() { assertEquals("{{ma(croabc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ma(croabc)"), 0).toString()); }
    @Test public void test_JekyllSpaces12() { assertEquals("{{ma(croabc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ma(croabc))"), 0).toString()); }
    @Test public void test_JekyllSpaces13() { assertEquals("{{ma(cro)abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ma(cro)abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces14() { assertEquals("{{ ma(cro) abc", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("{{ ma(cro) abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces15() { assertEquals("({{ma(cro)abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ma(cro)abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces16a() { assertEquals("({{ma(cro) abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ma(cro) abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces16b() { assertEquals("({{ ma(cro) abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ ma(cro) abc)"), 0).toString()); }
    @Test public void test_JekyllSpaces17() { assertEquals("({{ma(cro)abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ma(cro)abc))"), 0).toString()); }
    @Test public void test_JekyllSpaces18() { assertEquals("({{ma(cro) abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ma(cro) abc))"), 0).toString()); }
    @Test public void test_JekyllSpaces19() { assertEquals("({{ma(cro)abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ma(cro)abc))"), 0).toString()); }
    @Test public void test_JekyllSpaces20() { assertEquals("({{ma(cro) abc)", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("({{ma(cro) abc))"), 0).toString()); }
    @Test public void test_JekyllSpaces21() { assertEquals("(({{ma(cro) abc))", jekyllSpacesParser.parseLinkDestination(CharSubSequence.of("(({{ma(cro) abc))"), 0).toString()); }

    // @formatter:on
}
