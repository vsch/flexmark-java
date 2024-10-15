package com.vladsch.flexmark.parser.internal;

import com.vladsch.flexmark.util.format.TableFormatOptions;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.Escaping;
import java.util.BitSet;

public class LinkDestinationParser {
  private final BitSet excluded0ToSpaceChars;
  private final BitSet jekyllExcludedChars;
  private final BitSet parenExcludedChars;
  private final BitSet parenEscapableChars;
  private final BitSet parenQuoteChars;
  private final boolean allowMatchedParentheses;
  private final boolean spaceInUrls;
  private final boolean parseJekyllMacrosInUrls;

  /**
   * Parse Link Destination
   *
   * @param allowMatchedParentheses allow matched parentheses in link address. NOTE: if jekyll
   *     macros option is enabled, them matched parentheses will be enabled even if not selected.
   * @param spaceInUrls allow space in address
   * @param parseJekyllMacrosInUrls allow jekyll macros, matched {{ and }}
   * @param intellijDummyIdentifier allow intellij dummy identifier character
   */
  public LinkDestinationParser(
      boolean allowMatchedParentheses,
      boolean spaceInUrls,
      boolean parseJekyllMacrosInUrls,
      boolean intellijDummyIdentifier) {
    this.allowMatchedParentheses = allowMatchedParentheses || parseJekyllMacrosInUrls;
    this.spaceInUrls = spaceInUrls;
    this.parseJekyllMacrosInUrls = parseJekyllMacrosInUrls;

    // needed for hand rolled link parser
    excluded0ToSpaceChars = getCharSet('\u0000', '\u0020');
    if (intellijDummyIdentifier)
      excluded0ToSpaceChars.clear(TableFormatOptions.INTELLIJ_DUMMY_IDENTIFIER_CHAR);

    jekyllExcludedChars = getCharSet("{}\\");
    jekyllExcludedChars.or(excluded0ToSpaceChars);
    jekyllExcludedChars.clear(' ');
    jekyllExcludedChars.clear('\t');

    parenExcludedChars = getCharSet("()\\");
    parenExcludedChars.or(excluded0ToSpaceChars);

    parenEscapableChars = getCharSet(Escaping.ESCAPABLE_CHARS);
    parenQuoteChars = getCharSet("\"'");
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
              } else if (jekyllExcludedChars.get(c)) {
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
                openParenState = 0; // reset in case it was -1
                openJekyllState = 0; // start over
                break;
              } else if (jekyllExcludedChars.get(c)) {
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
              if (parenEscapableChars.get(input.safeCharAt(nextIndex))) {
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
                if (spaceInUrls && !parenQuoteChars.get(input.safeCharAt(nextIndex))) {
                  // space will be included by next char, ie. trailing spaces not included
                  break;
                }
              } else if (!parenExcludedChars.get(c)) {
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

  private static BitSet getCharSet(CharSequence chars) {
    BitSet charSet = new BitSet(chars.length());
    int iMax = chars.length();
    for (int i = 0; i < iMax; i++) {
      charSet.set(chars.charAt(i));
    }
    return charSet;
  }

  private static BitSet getCharSet(char charFrom, char charTo) {
    BitSet charSet = new BitSet();
    for (int i = charFrom; i <= charTo; i++) {
      charSet.set(i);
    }
    return charSet;
  }
}
