package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;

import java.util.BitSet;

/*
 * //String EXCLUDED_0_TO_SPACE = options.intellijDummyIdentifier ? "\u0000-\u001e\u0020" : "\u0000-\u0020";
 * //String LINK_DESTINATION_MATCHED_PARENS_EXPANDED =
 * //        "^(?:" + (options.parseJekyllMacrosInUrls ? ("\\{\\{(?:[^{}\\\\" + EXCLUDED_0_TO_SPACE + "]| |\t)*\\}\\}") + "|" : "")
 * //                + (options.spaceInLinkUrls ? "(?:" + ("[^\\\\()" + EXCLUDED_0_TO_SPACE + "]| (?![\"'])") + ")|" : ("[^\\\\()" + EXCLUDED_0_TO_SPACE + "]") + "|") +
 * //                ("\\\\" + Escaping.ESCAPABLE) + "|\\\\|\\(|\\))*";
 *
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 221 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 170 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 65 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalTest) took 286 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 89 ms
longImageLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 738 ms
longLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 63 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalTest) took 342 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalTest) took 72 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalTest) took 6 ms
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 113 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 123 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 56 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 229 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 85 ms
longImageLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 684 ms
longLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 71 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 327 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 79 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 6 ms
 *
 * regex based parser:
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 205 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 162 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalTest) took 61 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalTest) took 277 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalTest) took 87 ms
StackOverflow longImageLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 136 ms
longLinkTest(com.vladsch.flexmark.test.PathologicalTest) took 77 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalTest) took 264 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalTest) took 85 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalTest) took 8 ms
emphasisClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 173 ms
emphasisOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 163 ms
linkClosersWithNoOpeners(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 55 ms
linkOpenersAndEmphasisClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 216 ms
linkOpenersWithNoClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 85 ms
StackOverflow longImageLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 157 ms
StackOverflow longLinkTest(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 60 ms
mismatchedOpenersAndClosers(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 214 ms
nestedBrackets(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 55 ms
nestedStrongEmphasis(com.vladsch.flexmark.test.PathologicalSpcUrlTest) took 5 ms
 * `
 */
public class LinkDestinationParser {
    final public BitSet EXCLUDED_0_TO_SPACE_CHARS;
    final public BitSet JEKYLL_EXCLUDED_CHARS;
    final public BitSet PAREN_EXCLUDED_CHARS;
    final public BitSet PAREN_ESCAPABLE_CHARS;
    final public BitSet PAREN_QUOTE_CHARS;
    final public boolean allowMatchedParentheses;
    final public boolean spaceInUrls;
    final public boolean parseJekyllMacrosInUrls;
    final public boolean intellijDummyIdentifier;

    /**
     * Parse Link Destination
     *
     * @param allowMatchedParentheses allow matched parentheses in link address. NOTE: if jekyll macros option is enabled, them matched parentheses will be enabled even if not selected.
     * @param spaceInUrls             allow space in address
     * @param parseJekyllMacrosInUrls allow jekyll macros, matched {{ and }}
     * @param intellijDummyIdentifier allow intellij dummy identifier character
     */
    public LinkDestinationParser(boolean allowMatchedParentheses, boolean spaceInUrls, boolean parseJekyllMacrosInUrls, boolean intellijDummyIdentifier) {
        this.allowMatchedParentheses = allowMatchedParentheses || parseJekyllMacrosInUrls;
        this.spaceInUrls = spaceInUrls;
        this.parseJekyllMacrosInUrls = parseJekyllMacrosInUrls;
        this.intellijDummyIdentifier = intellijDummyIdentifier;

        // needed for hand rolled link parser
        EXCLUDED_0_TO_SPACE_CHARS = getCharSet('\u0000', '\u0020');
        if (intellijDummyIdentifier) EXCLUDED_0_TO_SPACE_CHARS.clear(TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR);

        JEKYLL_EXCLUDED_CHARS = getCharSet("{}\\");
        JEKYLL_EXCLUDED_CHARS.or(EXCLUDED_0_TO_SPACE_CHARS);
        JEKYLL_EXCLUDED_CHARS.clear(' ');
        JEKYLL_EXCLUDED_CHARS.clear('\t');

        PAREN_EXCLUDED_CHARS = getCharSet("()\\");
        PAREN_EXCLUDED_CHARS.or(EXCLUDED_0_TO_SPACE_CHARS);

        PAREN_ESCAPABLE_CHARS = getCharSet(Escaping.ESCAPABLE_CHARS);
        PAREN_QUOTE_CHARS = getCharSet("\"'");
    }

    public BasedSequence parseLinkDestination(BasedSequence input, int startIndex) {
        int iMax = input.length();
        int lastMatch = startIndex;

        int openParenCount = 0;
        int openParenState = 0;
        int jekyllOpenParens = 0;

        int openJekyllState = parseJekyllMacrosInUrls ? 0 : -1;

        for (int i = startIndex; i < iMax; i++) {
            char c = input.charAt(i);

            int nextIndex = i + 1;

            if (openJekyllState >= 0) {
                switch (openJekyllState) {
                    case 0:
                        // looking for {{
                        if (openParenState == 1) {
                            // escaped char
                        } else {
                            if (c == '{' && input.safeCharAt(nextIndex) == '{') {
                                openJekyllState = 1;
                                break;
                            }
                        }
                        break;
                    case 1:
                        // looking for second {
                        if (openParenState == 1) {
                            // escaped char
                        } else {
                            if (c == '{') {
                                jekyllOpenParens = 0;
                                openJekyllState = 2;
                                break;
                            }
                        }
                        openJekyllState = 0; // start over
                        break;
                    case 2:
                        // accumulating or waiting for }}
                        if (openParenState == 1) {
                            // escaped char
                        } else {
                            if (c == '}') {
                                openJekyllState = 3;
                            } else if (c == '(') {
                                jekyllOpenParens++;
                            } else if (c == ')') {
                                if (jekyllOpenParens > 0) {
                                    // take ) if main parser did not terminate already
                                    if (openParenState != -1) lastMatch = nextIndex;

                                    jekyllOpenParens--;
                                    break;
                                }

                                openJekyllState = 0;
                            } else if (JEKYLL_EXCLUDED_CHARS.get(c)) {
                                openParenCount += jekyllOpenParens; // transfer open parens to normal parser
                                openJekyllState = 0; // start over
                            }
                        }
                        break;
                    case 3:
                        // accumulating or waiting for second }
                        if (openParenState == 1) {
                            // escaped char
                        } else {
                            if (c == '}') {
                                lastMatch = nextIndex; // found it
                                openParenState = 0;   // reset in case it was -1
                                openJekyllState = 0; // start over
                                break;
                            } else if (JEKYLL_EXCLUDED_CHARS.get(c)) {
                                openParenCount += jekyllOpenParens; // transfer open parens to normal parser
                                openJekyllState = 0; // start over
                                break;
                            }
                        }

                        openJekyllState = 2; // continue accumulating
                        break;
                    default:
                        throw new IllegalStateException("Illegal Jekyll Macro Parsing State");
                }
            }

            // parens matching
            if (openParenState >= 0) {
                switch (openParenState) {
                    case 0: // starting
                        if (c == '\\') {
                            if (PAREN_ESCAPABLE_CHARS.get(input.safeCharAt(nextIndex))) {
                                // escaped, take the next one if available
                                openParenState = 1;
                            }

                            lastMatch = nextIndex;
                            break;
                        } else if (c == '(') {
                            if (openJekyllState != 2) {
                                if (allowMatchedParentheses) {
                                    openParenCount++;
                                } else {
                                    if (openParenCount == 0) {
                                        openParenCount++;
                                    } else {
                                        // invalid, parentheses need escaping beyond 1
                                        lastMatch = startIndex;
                                        openParenState = -1;
                                        break;
                                    }
                                }
                            }
                            lastMatch = nextIndex;
                            break;
                        } else if (c == ')') {
                            if (openJekyllState == 2) {
                                break; // jekyll parsing handles it for now
                            }

                            if (openParenCount > 0) {
                                openParenCount--;
                                lastMatch = nextIndex;
                                break;
                            } else if (!allowMatchedParentheses) {
                                openParenState = -1;
                                break;
                            }
                        } else {
                            if (c == ' ') {
                                if (spaceInUrls && !PAREN_QUOTE_CHARS.get(input.safeCharAt(nextIndex))) {
                                    // space will be included by next char, ie. trailing spaces not included
                                    // lastMatch = nextIndex;
                                    break;
                                }
                            } else if (!PAREN_EXCLUDED_CHARS.get(c)) {
                                lastMatch = nextIndex;
                                break;
                            }
                        }

                        // we are done, no more matches here
                        openParenState = -1;
                        break;

                    case 1:
                        // escaped
                        lastMatch = nextIndex;
                        openParenState = 0;
                        break;

                    default:
                        throw new IllegalStateException("Illegal Jekyll Macro Parsing State");
                }
            }

            if (openJekyllState <= 0 && openParenState == -1) {
                break;
            }
        }

        // always have something even if it is empty
        return input.subSequence(startIndex, lastMatch);
    }

    public static BitSet getCharSet(CharSequence chars) {
        BitSet charSet = new BitSet(chars.length());
        int iMax = chars.length();
        for (int i = 0; i < iMax; i++) {
            charSet.set(chars.charAt(i));
        }
        return charSet;
    }

    public static BitSet getCharSet(char charFrom, char charTo) {
        BitSet charSet = new BitSet();
        for (int i = charFrom; i <= charTo; i++) {
            charSet.set(i);
        }
        return charSet;
    }
}
