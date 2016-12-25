package com.vladsch.flexmark.util.html;

import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.PrefixedSubSequence;
import com.vladsch.flexmark.util.sequence.ReplacedTextMapper;

import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Escaping {

    public static final String ESCAPABLE = "[!\"#$%&\'()*+,./:;<=>?@\\[\\\\\\]^_`{|}~-]";

    private static final String ENTITY = "&(?:#x[a-f0-9]{1,8}|#[0-9]{1,8}|[a-z][a-z0-9]{1,31});";

    private static final Pattern BACKSLASH_OR_AMP = Pattern.compile("[\\\\&]");

    private static final Pattern ENTITY_OR_ESCAPED_CHAR =
            Pattern.compile("\\\\" + ESCAPABLE + '|' + ENTITY, Pattern.CASE_INSENSITIVE);

    private static final String XML_SPECIAL = "[&<>\"]";

    private static final Pattern XML_SPECIAL_RE = Pattern.compile(XML_SPECIAL);

    private static final Pattern XML_SPECIAL_OR_ENTITY =
            Pattern.compile(ENTITY + '|' + XML_SPECIAL, Pattern.CASE_INSENSITIVE);

    // From RFC 3986 (see "reserved", "unreserved") except don't escape '[' or ']' to be compatible with JS encodeURI
    private static final Pattern ESCAPE_IN_URI =
            Pattern.compile("(%[a-fA-F0-9]{0,2}|[^:/?#@!$&'()*+,;=a-zA-Z0-9\\-._~])");

    static final char[] HEX_DIGITS =
            new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    private static final Pattern WHITESPACE = Pattern.compile("[ \t\r\n]+");

    private static final Replacer UNSAFE_CHAR_REPLACER = new Replacer() {
        @Override
        public void replace(String input, StringBuilder sb) {
            switch (input) {
                case "&":
                    sb.append("&amp;");
                    break;
                case "<":
                    sb.append("&lt;");
                    break;
                case ">":
                    sb.append("&gt;");
                    break;
                case "\"":
                    sb.append("&quot;");
                    break;
                default:
                    sb.append(input);
            }
        }

        @Override
        public void replace(BasedSequence input, ReplacedTextMapper textMapper) {
            switch (input.toString()) {
                case "&":
                    textMapper.addReplacedText(input, PrefixedSubSequence.of("&amp;", BasedSequence.NULL));
                    break;
                case "<":
                    textMapper.addReplacedText(input, PrefixedSubSequence.of("&lt;", BasedSequence.NULL));
                    break;
                case ">":
                    textMapper.addReplacedText(input, PrefixedSubSequence.of("&gt;", BasedSequence.NULL));
                    break;
                case "\"":
                    textMapper.addReplacedText(input, PrefixedSubSequence.of("&quot;", BasedSequence.NULL));
                    break;
                default:
                    textMapper.addOriginalText(input);
            }
        }
    };

    private static final Replacer UNESCAPE_REPLACER = new Replacer() {
        @Override
        public void replace(String input, StringBuilder sb) {
            if (input.charAt(0) == '\\') {
                sb.append(input, 1, input.length());
            } else {
                sb.append(Html5Entities.entityToString(input));
            }
        }

        @Override
        public void replace(BasedSequence input, ReplacedTextMapper textMapper) {
            if (input.charAt(0) == '\\') {
                textMapper.addReplacedText(input, input.subSequence(1, input.length()));
            } else {
                textMapper.addReplacedText(input, Html5Entities.entityToSequence(input));
            }
        }
    };

    private static final Replacer URI_REPLACER = new Replacer() {
        @Override
        public void replace(String input, StringBuilder sb) {
            if (input.startsWith("%")) {
                if (input.length() == 3) {
                    // Already percent-encoded, preserve
                    sb.append(input);
                } else {
                    // %25 is the percent-encoding for %
                    sb.append("%25");
                    sb.append(input, 1, input.length());
                }
            } else {
                byte[] bytes = input.getBytes(Charset.forName("UTF-8"));
                for (byte b : bytes) {
                    sb.append('%');
                    sb.append(HEX_DIGITS[(b >> 4) & 0xF]);
                    sb.append(HEX_DIGITS[b & 0xF]);
                }
            }
        }

        @Override
        public void replace(BasedSequence input, ReplacedTextMapper textMapper) {
            if (input.startsWith("%")) {
                if (input.length() == 3) {
                    // Already percent-encoded, preserve
                    textMapper.addOriginalText(input);
                } else {
                    // %25 is the percent-encoding for %
                    textMapper.addReplacedText(input.subSequence(0, 1), PrefixedSubSequence.of("%25", BasedSequence.NULL));
                    textMapper.addOriginalText(input.subSequence(1, input.length()));
                }
            } else {
                byte[] bytes = input.toString().getBytes(Charset.forName("UTF-8"));
                StringBuilder sbItem = new StringBuilder();

                for (byte b : bytes) {
                    sbItem.append('%');
                    sbItem.append(HEX_DIGITS[(b >> 4) & 0xF]);
                    sbItem.append(HEX_DIGITS[b & 0xF]);
                }
                textMapper.addReplacedText(input, PrefixedSubSequence.of(sbItem.toString(), BasedSequence.NULL));
            }
        }
    };

    public static String escapeHtml(String input, boolean preserveEntities) {
        Pattern p = preserveEntities ? XML_SPECIAL_OR_ENTITY : XML_SPECIAL_RE;
        return replaceAll(p, input, UNSAFE_CHAR_REPLACER);
    }

    public static BasedSequence escapeHtml(BasedSequence input, boolean preserveEntities, ReplacedTextMapper textMapper) {
        Pattern p = preserveEntities ? XML_SPECIAL_OR_ENTITY : XML_SPECIAL_RE;
        return replaceAll(p, input, UNSAFE_CHAR_REPLACER, textMapper);
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s string to un-escape
     * @return un-escaped string
     */
    public static String unescapeString(String s) {
        if (BACKSLASH_OR_AMP.matcher(s).find()) {
            return replaceAll(ENTITY_OR_ESCAPED_CHAR, s, UNESCAPE_REPLACER);
        } else {
            return s;
        }
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s          based sequence to un-escape
     * @param textMapper replaced text mapper to update for the changed text
     * @return un-escaped sequence
     */
    public static BasedSequence unescape(BasedSequence s, ReplacedTextMapper textMapper) {
        int indexOfAny = s.indexOfAny('\\', '&');
        if (indexOfAny != -1) {
            // all before are not part of it so we can skip it
            textMapper.addOriginalText(s.subSequence(0, indexOfAny));
            return replaceAll(ENTITY_OR_ESCAPED_CHAR, s.subSequence(indexOfAny), UNESCAPE_REPLACER, textMapper);
        } else {
            return s;
        }
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     * <p>
     * Append EOL sequence if sequence does not already end in EOL
     *
     * @param input sequence to convert
     * @return converted sequence
     */
    public static String normalizeEndWithEOL(CharSequence input) {
        return normalizeEOL(input, true);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     *
     * @param input sequence to convert
     * @return converted sequence
     */
    public static String normalizeEOL(CharSequence input) {
        return normalizeEOL(input, false);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     *
     * @param input      sequence to convert
     * @param endWithEOL true if an EOL is to be appended to the end of the sequence if not already ending with one.
     * @return converted sequence
     */
    public static String normalizeEOL(CharSequence input, boolean endWithEOL) {
        StringBuilder sb = new StringBuilder(input.length());
        int iMax = input.length();
        boolean hadCR = false;
        boolean hadEOL = false;

        for (int i = 0; i < iMax; i++) {
            char c = input.charAt(i);
            if (c == '\r') {
                hadCR = true;
            } else if (c == '\n') {
                sb.append("\n");
                hadCR = false;
                hadEOL = true;
            } else {
                if (hadCR) sb.append('\n');
                sb.append(c);
                hadCR = false;
                hadEOL = false;
            }
        }
        if (endWithEOL && !hadEOL) sb.append('\n');
        return sb.toString();
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     * <p>
     * Append EOL sequence if sequence does not already end in EOL
     *
     * @param input      sequence to convert
     * @param textMapper text mapper to update for the replaced text
     * @return converted sequence
     */
    public static BasedSequence normalizeEndWithEOL(BasedSequence input, ReplacedTextMapper textMapper) {
        return normalizeEOL(input, textMapper, true);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     *
     * @param input      sequence to convert
     * @param textMapper text mapper to update for the replaced text
     * @return converted sequence
     */
    public static BasedSequence normalizeEOL(BasedSequence input, ReplacedTextMapper textMapper) {
        return normalizeEOL(input, textMapper, false);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     * <p>
     * Append EOL sequence if sequence does not already end in EOL
     *
     * @param input      sequence to convert
     * @param textMapper text mapper to update for the replaced text
     * @param endWithEOL whether an EOL is to be appended to the end of the sequence if it does not already end with one.
     * @return converted sequence
     */
    public static BasedSequence normalizeEOL(BasedSequence input, ReplacedTextMapper textMapper, boolean endWithEOL) {
        int iMax = input.length();
        int lastPos = 0;
        boolean hadCR = false;
        boolean hadEOL = false;

        for (int i = 0; i < iMax; i++) {
            char c = input.charAt(i);
            if (c == '\r') {
                hadCR = true;
            } else if (c == '\n') {
                if (hadCR) {
                    // previous was CR, need to take preceding chars
                    if (lastPos < i - 1) textMapper.addOriginalText(input.subSequence(lastPos, i - 1));
                    lastPos = i;
                    hadCR = false;
                    hadEOL = true;
                }
            } else {
                if (hadCR) {
                    if (lastPos < i - 1) textMapper.addOriginalText(input.subSequence(lastPos, i + 1));
                    textMapper.addReplacedText(input.subSequence(i - 1, i), BasedSequence.EOL);
                    lastPos = i;
                    hadCR = false;
                    hadEOL = false;
                }
            }
        }

        if (!hadCR) {
            //    // previous was CR, need to take preceding chars
            //    if (lastPos < iMax - 1) textMapper.addOriginalText(input.subSequence(lastPos, iMax - 1));
            //    textMapper.addReplacedText(input.subSequence(iMax - 1, iMax), SubSequence.EOL);
            //} else {
            if (lastPos < iMax) textMapper.addOriginalText(input.subSequence(lastPos, iMax));
            if (!hadEOL && endWithEOL) textMapper.addReplacedText(input.subSequence(iMax - 1, iMax), BasedSequence.EOL);
        }

        return textMapper.getReplacedSequence();
    }

    /**
     * @param s string to encode
     * @return encoded string
     */
    public static String percentEncodeUrl(String s) {
        return replaceAll(ESCAPE_IN_URI, s, URI_REPLACER);
    }

    /**
     * Normalize the link reference id
     *
     * @param input      sequence containing the link reference id
     * @param changeCase if true then reference will be converted to lowercase
     * @return normalized link reference id
     */
    public static String normalizeReference(CharSequence input, boolean changeCase) {
        if (changeCase) return Escaping.collapseWhitespace(input.toString(), true).toLowerCase();
        else return Escaping.collapseWhitespace(input.toString(), true);
    }

    /**
     * Get a normalized the link reference id from reference characters
     * <p>
     * Will remove leading ![ or [ and trailing ], collapse multiple whitespaces to a space and optionally convert the id to lowercase.
     *
     * @param input      sequence containing the link reference id
     * @param changeCase if true then reference will be converted to lowercase
     * @return normalized link reference id
     */
    public static String normalizeReferenceChars(CharSequence input, boolean changeCase) {
        // Strip '[' and ']', then trim and convert to lowercase
        if (input.length() > 1) {
            int stripEnd = input.charAt(input.length() - 1) == ':' ? 2 : 1;
            int stripStart = input.charAt(0) == '!' ? 2 : 1;
            return normalizeReference(input.subSequence(stripStart, input.length() - stripEnd), changeCase);
        }
        return input.toString();
    }

    /**
     * Collapse regions of multiple white spaces to a single space
     *
     * @param input sequence to process
     * @param trim  true if the sequence should also be trimmed
     * @return processed sequence
     */
    public static String collapseWhitespace(CharSequence input, boolean trim) {
        StringBuilder sb = new StringBuilder(input.length());
        int iMax = input.length();
        boolean hadSpace = false;

        for (int i = 0; i < iMax; i++) {
            char c = input.charAt(i);
            if (c == ' ' || c == '\t' || c == '\n' || c == '\r') {
                hadSpace = true;
            } else {
                if (hadSpace && (!trim || sb.length() > 0)) sb.append(' ');
                sb.append(c);
                hadSpace = false;
            }
        }
        if (hadSpace && !trim) sb.append(' ');
        return sb.toString();
    }

    private static String replaceAll(Pattern p, String s, Replacer replacer) {
        Matcher matcher = p.matcher(s);

        if (!matcher.find()) {
            return s;
        }

        StringBuilder sb = new StringBuilder(s.length() + 16);
        int lastEnd = 0;
        do {
            sb.append(s, lastEnd, matcher.start());
            replacer.replace(matcher.group(), sb);
            lastEnd = matcher.end();
        } while (matcher.find());

        if (lastEnd != s.length()) {
            sb.append(s, lastEnd, s.length());
        }
        return sb.toString();
    }

    private static BasedSequence replaceAll(Pattern p, BasedSequence s, Replacer replacer, ReplacedTextMapper textMapper) {
        Matcher matcher = p.matcher(s);

        if (!matcher.find()) {
            textMapper.addOriginalText(s);
            return s;
        }

        int lastEnd = 0;
        do {
            textMapper.addOriginalText(s.subSequence(lastEnd, matcher.start()));
            replacer.replace(s.subSequence(matcher.start(), matcher.end()), textMapper);
            lastEnd = matcher.end();
        } while (matcher.find());

        if (lastEnd != s.length()) {
            textMapper.addOriginalText(s.subSequence(lastEnd, s.length()));
        }

        return textMapper.getReplacedSequence();
    }

    interface Replacer {
        void replace(String input, StringBuilder sb);
        void replace(BasedSequence input, ReplacedTextMapper replacedTextMapper);
    }
}
