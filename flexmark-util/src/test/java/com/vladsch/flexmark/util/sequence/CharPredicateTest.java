package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.misc.CharPredicate;
import org.junit.Test;

import static com.vladsch.flexmark.util.misc.CharPredicate.*;
import static org.junit.Assert.*;

public class CharPredicateTest {
    @Test
    public void and() {
        CharPredicate test1 = value -> value == 'a';
        CharPredicate test2 = value -> value == 'b';

        assertSame(NONE, NONE.and(ALL));
        assertSame(NONE, ALL.and(NONE));
        assertSame(NONE, test1.and(NONE));
        assertSame(NONE, NONE.and(test1));

        assertSame(ALL, ALL.and(ALL));
        assertSame(test1, test1.and(ALL));
        assertSame(test1, ALL.and(test1));

        assertSame(test2, ALL.and(test2));
        assertSame(test2, test2.and(ALL));

        assertFalse(test1.and(test2).test('a'));
        assertFalse(test1.and(test2).test('b'));
        assertFalse(test2.and(test1).test('a'));
        assertFalse(test2.and(test1).test('b'));
        assertFalse(test2.and(test2).test('a'));
        assertFalse(test1.and(test1).test('b'));

        assertTrue(test1.and(test1).test('a'));
        assertTrue(test2.and(test2).test('b'));
    }

    @Test
    public void negate() {
        CharPredicate test1 = value -> value == 'a';
        CharPredicate test2 = value -> value == 'b';

        assertSame(ALL, NONE.negate());
        assertSame(NONE, ALL.negate());

        assertFalse(test1.negate().test('a'));
        assertFalse(test2.negate().test('b'));

        assertTrue(test1.negate().test('b'));
        assertTrue(test2.negate().test('a'));
    }

    @Test
    public void or() {
        CharPredicate test1 = value -> value == 'a';
        CharPredicate test2 = value -> value == '0';

        assertSame(ALL, NONE.or(ALL));
        assertSame(ALL, ALL.or(NONE));
        assertSame(ALL, test1.or(ALL));
        assertSame(ALL, ALL.or(test1));

        assertSame(NONE, NONE.or(NONE));
        assertSame(test1, test1.or(NONE));
        assertSame(test1, NONE.or(test1));

        assertSame(test2, NONE.or(test2));
        assertSame(test2, test2.or(NONE));

        assertFalse(test1.or(test2).test('c'));
        assertFalse(test1.or(test2).test('c'));
        assertFalse(test1.or(test2).test('c'));
        assertFalse(test2.or(test1).test('c'));
        assertFalse(test2.or(test1).test('c'));
        assertFalse(test2.or(test2).test('c'));
        assertFalse(test1.or(test1).test('c'));

        assertTrue(test1.or(test2).test('a'));
        assertTrue(test1.or(test2).test('0'));
        assertTrue(test2.or(test1).test('a'));
        assertTrue(test2.or(test1).test('0'));

        assertFalse(test2.or(test2).test('a'));
        assertFalse(test1.or(test1).test('0'));
    }

    @Test
    public void testStandardOrAnyOf1() {
        assertSame(SPACE, standardOrAnyOf(' '));
        assertSame(TAB, standardOrAnyOf('\t'));
        assertSame(EOL, standardOrAnyOf('\n'));

        CharPredicate test1 = standardOrAnyOf('a');
        CharPredicate test2 = standardOrAnyOf('0');

        assertTrue(test1.test('a'));
        assertFalse(test1.test('0'));

        assertTrue(test2.test('0'));
        assertFalse(test2.test('a'));
    }

    @Test
    public void testStandardOrAnyOf2() {
        assertSame(SPACE, standardOrAnyOf(' ', ' '));
        assertSame(TAB, standardOrAnyOf('\t', '\t'));
        assertSame(SPACE_TAB, standardOrAnyOf(' ', '\t'));
        assertSame(SPACE_TAB, standardOrAnyOf('\t', ' '));
        assertSame(EOL, standardOrAnyOf('\n', '\n'));
        assertSame(ANY_EOL, standardOrAnyOf('\n', '\r'));
        assertSame(ANY_EOL, standardOrAnyOf('\r', '\n'));

        CharPredicate test1 = standardOrAnyOf('a', 'b');
        CharPredicate test2 = standardOrAnyOf('0', '1');

        assertTrue(test1.test('a'));
        assertTrue(test1.test('b'));
        assertFalse(test1.test('0'));
        assertFalse(test1.test('1'));

        assertTrue(test2.test('0'));
        assertTrue(test2.test('1'));
        assertFalse(test2.test('a'));
        assertFalse(test2.test('b'));
    }

    @Test
    public void testStandardOrAnyOf3() {
        assertSame(SPACE, standardOrAnyOf(' ', ' ', ' '));
        assertSame(TAB, standardOrAnyOf('\t', '\t', '\t'));
        assertSame(EOL, standardOrAnyOf('\n', '\n', '\n'));
        assertSame(ANY_EOL, standardOrAnyOf('\n', '\n', '\r'));
        assertSame(ANY_EOL, standardOrAnyOf('\n', '\r', '\n'));
        assertSame(ANY_EOL, standardOrAnyOf('\r', '\n', '\n'));
        assertSame(ANY_EOL, standardOrAnyOf('\n', '\r', '\r'));
        assertSame(ANY_EOL, standardOrAnyOf('\r', '\n', '\r'));
        assertSame(ANY_EOL, standardOrAnyOf('\r', '\r', '\n'));

        CharPredicate test1 = standardOrAnyOf('a', 'b', 'c');
        CharPredicate test2 = standardOrAnyOf('0', '1', '2');

        assertTrue(test1.test('a'));
        assertTrue(test1.test('b'));
        assertTrue(test1.test('c'));
        assertFalse(test1.test('0'));
        assertFalse(test1.test('1'));
        assertFalse(test1.test('2'));

        assertTrue(test2.test('0'));
        assertTrue(test2.test('1'));
        assertTrue(test2.test('2'));
        assertFalse(test2.test('a'));
        assertFalse(test2.test('b'));
        assertFalse(test2.test('c'));
    }

    @Test
    public void testStandardOrAnyOf4() {
        assertSame(SPACE, standardOrAnyOf(' ', ' ', ' ', ' '));
        assertSame(TAB, standardOrAnyOf('\t', '\t', '\t', '\t'));
        assertSame(EOL, standardOrAnyOf('\n', '\n', '\n', '\n'));

        for (int i = 0; i < 16; i++) {
            char c0 = (i & 1) != 0 ? ' ' : '\t';
            char c1 = (i & 2) != 0 ? ' ' : '\t';
            char c2 = (i & 4) != 0 ? ' ' : '\t';
            char c3 = (i & 8) != 0 ? ' ' : '\t';
            if (i == 0) assertSame(TAB, standardOrAnyOf(c0, c1, c2, c3));
            else if (i == 15) assertSame(SPACE, standardOrAnyOf(c0, c1, c2, c3));
            else assertSame(SPACE_TAB, standardOrAnyOf(c0, c1, c2, c3));
        }

        for (int i = 0; i < 16; i++) {
            if (i == 0) continue;

            char c0 = (i & 1) != 0 ? '\n' : '\r';
            char c1 = (i & 2) != 0 ? '\n' : '\r';
            char c2 = (i & 4) != 0 ? '\n' : '\r';
            char c3 = (i & 8) != 0 ? '\n' : '\r';

            if (i == 15) assertSame(EOL, standardOrAnyOf(c0, c1, c2, c3));
            else assertSame(ANY_EOL, standardOrAnyOf(c0, c1, c2, c3));
        }

        CharPredicate test1 = standardOrAnyOf('a', 'b', 'c', 'd');
        CharPredicate test2 = standardOrAnyOf('0', '1', '2', '3');

        CharPredicate test3 = standardOrAnyOf('a', 'b', 'd', 'd');

        assertTrue(test1.test('a'));
        assertTrue(test1.test('b'));
        assertTrue(test1.test('c'));
        assertTrue(test1.test('d'));
        assertFalse(test1.test('0'));
        assertFalse(test1.test('1'));
        assertFalse(test1.test('2'));
        assertFalse(test1.test('3'));

        assertTrue(test2.test('0'));
        assertTrue(test2.test('1'));
        assertTrue(test2.test('2'));
        assertTrue(test2.test('3'));
        assertFalse(test2.test('a'));
        assertFalse(test2.test('b'));
        assertFalse(test2.test('c'));
        assertFalse(test2.test('d'));

        int i = 4;
        char[] chars = new char[i];

        for (int j = 0; j < i; j++) {
            chars[j] = randomAscii();
        }

        String text = String.valueOf(chars);
        CharPredicate charTest = anyOf(chars);
        CharPredicate seqTest = anyOf(text);

        for (int k = 0; k < 1000; k++) {
            char c = randomChar();
            if (text.indexOf(c) != -1) {
                assertTrue(charTest.test(c));
                assertTrue(seqTest.test(c));
            } else {
                assertFalse(charTest.test(c));
                assertFalse(seqTest.test(c));
            }
        }
    }

    char randomChar() {
        return (char) (Character.MAX_CODE_POINT * Math.random());
    }

    char randomAscii() {
        return (char) (128 * Math.random());
    }

    @Test
    public void anyOfChars() {
        assertSame(NONE, anyOf());
        assertSame(NONE, anyOf(""));
        assertSame(SPACE, anyOf(' '));
        assertSame(SPACE, anyOf(' ', ' '));
        assertSame(SPACE, anyOf(' ', ' ', ' ', ' '));
        assertNotSame(SPACE, anyOf(' ', ' ', ' ', ' ', ' '));
        assertSame(TAB, anyOf('\t'));
        assertSame(TAB, anyOf('\t', '\t'));
        assertSame(TAB, anyOf('\t', '\t', '\t', '\t'));
        assertNotSame(TAB, anyOf('\t', '\t', '\t', '\t', '\t'));
        assertSame(EOL, anyOf('\n'));
        assertSame(EOL, anyOf('\n', '\n'));
        assertSame(EOL, anyOf('\n', '\n', '\n', '\n'));
        assertNotSame(EOL, anyOf('\n', '\n', '\n', '\n', '\n'));

        for (int i = 0; i < 16; i++) {
            char c0 = (i & 1) != 0 ? ' ' : '\t';
            char c1 = (i & 2) != 0 ? ' ' : '\t';
            char c2 = (i & 4) != 0 ? ' ' : '\t';
            char c3 = (i & 8) != 0 ? ' ' : '\t';
            if (i == 0) assertSame(TAB, anyOf(c0, c1, c2, c3));
            else if (i == 15) assertSame(SPACE, anyOf(c0, c1, c2, c3));
            else assertSame(SPACE_TAB, anyOf(c0, c1, c2, c3));
        }

        for (int i = 0; i < 16; i++) {
            if (i == 0) continue;

            char c0 = (i & 1) != 0 ? '\n' : '\r';
            char c1 = (i & 2) != 0 ? '\n' : '\r';
            char c2 = (i & 4) != 0 ? '\n' : '\r';
            char c3 = (i & 8) != 0 ? '\n' : '\r';

            if (i == 15) assertSame(EOL, anyOf(c0, c1, c2, c3));
            else assertSame(ANY_EOL, anyOf(c0, c1, c2, c3));
        }

        for (int i = 0; i < 500; i++) {
            char[] chars = new char[i];

            for (int j = 0; j < i; j++) {
                chars[j] = randomAscii();
            }

            String text = String.valueOf(chars);
            CharPredicate charTest = anyOf(chars);
            CharPredicate seqTest = anyOf(text);

            for (int k = 0; k < 1000; k++) {
                char c = randomChar();
                if (text.indexOf(c) != -1) {
                    assertTrue(charTest.test(c));
                    assertTrue(seqTest.test(c));
                } else {
                    assertFalse(charTest.test(c));
                    assertFalse(seqTest.test(c));
                }
            }
        }

        for (int i = 0; i < 1000; i++) {
            char[] chars = new char[i];

            for (int j = 0; j < i; j++) {
                chars[j] = randomChar();
            }

            String text = String.valueOf(chars);
            CharPredicate charTest = anyOf(chars);
            CharPredicate seqTest = anyOf(text);

            for (int k = 0; k < 1000; k++) {
                char c = randomChar();
                if (text.indexOf(c) != -1) {
                    assertTrue(charTest.test(c));
                    assertTrue(seqTest.test(c));
                } else {
                    assertFalse(charTest.test(c));
                    assertFalse(seqTest.test(c));
                }
            }
        }
    }

    boolean allIn(String text, CharPredicate predicate) {
        int iMax = text.length();
        for (int i = 0; i < iMax; i++) {
            if (!predicate.test(text.charAt(i))) return false;
        }
        return true;
    }

    @Test
    public void testMisc() {
        assertTrue(allIn(" \t\r\n\u00A0", WHITESPACE_NBSP));
        assertTrue(allIn(SequenceUtils.WHITESPACE_NBSP, WHITESPACE_NBSP));
        assertTrue(allIn(SequenceUtils.WHITESPACE, WHITESPACE_NBSP));
    }
}
