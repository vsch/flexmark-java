package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.misc.CharPredicate;
import com.vladsch.flexmark.util.misc.Utils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Escaping {

    // pure chars not for pattern
    final public static String ESCAPABLE_CHARS = "\"#$%&'()*+,./:;<=>?@[]\\^_`{|}~-";

    final public static String ESCAPABLE = "[!" +
            ESCAPABLE_CHARS
                    .replace("\\", "\\\\")
                    .replace("[", "\\[")
                    .replace("]", "\\]") +
            "]";

    final private static String ENTITY = "&(?:#x[a-f0-9]{1,8}|#[0-9]{1,8}|[a-z][a-z0-9]{1,31});";

    final private static Pattern BACKSLASH_ONLY = Pattern.compile("[\\\\]");

    final private static Pattern ESCAPED_CHAR =
            Pattern.compile("\\\\" + ESCAPABLE, Pattern.CASE_INSENSITIVE);

    final private static Pattern BACKSLASH_OR_AMP = Pattern.compile("[\\\\&]");

    final private static Pattern AMP_ONLY = Pattern.compile("[\\&]");

    final private static Pattern ENTITY_OR_ESCAPED_CHAR =
            Pattern.compile("\\\\" + ESCAPABLE + '|' + ENTITY, Pattern.CASE_INSENSITIVE);

    final private static Pattern ENTITY_ONLY =
            Pattern.compile(ENTITY, Pattern.CASE_INSENSITIVE);

    final private static String XML_SPECIAL = "[&<>\"]";

    final private static Pattern XML_SPECIAL_RE = Pattern.compile(XML_SPECIAL);

    final private static Pattern XML_SPECIAL_OR_ENTITY =
            Pattern.compile(ENTITY + '|' + XML_SPECIAL, Pattern.CASE_INSENSITIVE);

    // From RFC 3986 (see "reserved", "unreserved") except don't escape '[' or ']' to be compatible with JS encodeURI
    final private static Pattern ESCAPE_IN_URI =
            Pattern.compile("(%[a-fA-F0-9]{0,2}|[^:/?#@!$&'()*+,;=a-zA-Z0-9\\-._~])");

    final private static Pattern ESCAPE_URI_DECODE =
            Pattern.compile("(%[a-fA-F0-9]{2})");

    static final char[] HEX_DIGITS =
            new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };

    final private static Pattern WHITESPACE = Pattern.compile("[ \t\r\n]+");

    final private static Pattern COLLAPSE_WHITESPACE = Pattern.compile("[ \t]{2,}");

    final private static Replacer UNSAFE_CHAR_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {
            if (s.equals("&")) {
                sb.append("&amp;");
            } else if (s.equals("<")) {
                sb.append("&lt;");
            } else if (s.equals(">")) {
                sb.append("&gt;");
            } else if (s.equals("\"")) {
                sb.append("&quot;");
            } else {
                sb.append(s);
            }
        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            String s1 = original.subSequence(startIndex, endIndex).toString();
            if (s1.equals("&")) {
                textMapper.addReplacedText(startIndex, endIndex, PrefixedSubSequence.prefixOf("&amp;", BasedSequence.NULL));
            } else if (s1.equals("<")) {
                textMapper.addReplacedText(startIndex, endIndex, PrefixedSubSequence.prefixOf("&lt;", BasedSequence.NULL));
            } else if (s1.equals(">")) {
                textMapper.addReplacedText(startIndex, endIndex, PrefixedSubSequence.prefixOf("&gt;", BasedSequence.NULL));
            } else if (s1.equals("\"")) {
                textMapper.addReplacedText(startIndex, endIndex, PrefixedSubSequence.prefixOf("&quot;", BasedSequence.NULL));
            } else {
                textMapper.addOriginalText(startIndex, endIndex);
            }
        }
    };

    final private static Replacer COLLAPSE_WHITESPACE_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {
            sb.append(" ");
        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            textMapper.addReplacedText(startIndex, endIndex, original.subSequence(startIndex, startIndex + 1));
        }
    };

    final private static Replacer UNESCAPE_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {
            if (s.charAt(0) == '\\') {
                sb.append(s, 1, s.length());
            } else {
                sb.append(Html5Entities.entityToString(s));
            }
        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            if (original.charAt(startIndex) == '\\') {
                textMapper.addReplacedText(startIndex, endIndex, original.subSequence(startIndex + 1, endIndex));
            } else {
                textMapper.addReplacedText(startIndex, endIndex, Html5Entities.entityToSequence(original.subSequence(startIndex, endIndex)));
            }
        }
    };

    final private static Replacer REMOVE_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {

        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            textMapper.addReplacedText(startIndex, endIndex, original.subSequence(endIndex, endIndex));
        }
    };

    final private static Replacer ENTITY_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {
            sb.append(Html5Entities.entityToString(s));
        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            textMapper.addReplacedText(startIndex, endIndex, Html5Entities.entityToSequence(original.subSequence(startIndex, endIndex)));
        }
    };

    final private static Replacer URL_ENCODE_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {
            if (s.startsWith("%")) {
                if (s.length() == 3) {
                    // Already percent-encoded, preserve
                    sb.append(s);
                } else {
                    // %25 is the percent-encoding for %
                    sb.append("%25");
                    sb.append(s, 1, s.length());
                }
            } else {
                byte[] bytes = s.getBytes(StandardCharsets.UTF_8);
                for (byte b : bytes) {
                    sb.append('%');
                    sb.append(HEX_DIGITS[(b >> 4) & 0xF]);
                    sb.append(HEX_DIGITS[b & 0xF]);
                }
            }
        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            BasedSequence s = original.subSequence(startIndex, endIndex);
            if (s.startsWith("%")) {
                if (s.length() == 3) {
                    // Already percent-encoded, preserve
                    textMapper.addOriginalText(startIndex, endIndex);
                } else {
                    // %25 is the percent-encoding for %
                    textMapper.addReplacedText(startIndex, startIndex + 1, PrefixedSubSequence.prefixOf("%25", BasedSequence.NULL));
                    textMapper.addOriginalText(startIndex + 1, endIndex);
                }
            } else {
                byte[] bytes = s.toString().getBytes(StandardCharsets.UTF_8);
                StringBuilder sbItem = new StringBuilder();

                for (byte b : bytes) {
                    sbItem.append('%');
                    sbItem.append(HEX_DIGITS[(b >> 4) & 0xF]);
                    sbItem.append(HEX_DIGITS[b & 0xF]);
                }
                textMapper.addReplacedText(startIndex, endIndex, PrefixedSubSequence.prefixOf(sbItem.toString(), BasedSequence.NULL));
            }
        }
    };

    final private static Replacer URL_DECODE_REPLACER = new Replacer() {
        @Override
        public void replace(@NotNull String s, @NotNull StringBuilder sb) {
            String urlDecoded = Utils.urlDecode(s, null);
            sb.append(urlDecoded);
        }

        @Override
        public void replace(@NotNull BasedSequence original, int startIndex, int endIndex, @NotNull ReplacedTextMapper textMapper) {
            BasedSequence s = original.subSequence(startIndex, endIndex);
            String decoded = Utils.urlDecode(s.toString(), null);
            textMapper.addReplacedText(startIndex, endIndex, PrefixedSubSequence.prefixOf(decoded, BasedSequence.NULL));
        }
    };

    final public static @NotNull CharPredicate AMP_BACKSLASH_SET = CharPredicate.anyOf('\\', '&');

    public static String escapeHtml(@NotNull CharSequence s, boolean preserveEntities) {
        Pattern p = preserveEntities ? XML_SPECIAL_OR_ENTITY : XML_SPECIAL_RE;
        return replaceAll(p, s, UNSAFE_CHAR_REPLACER);
    }

    @NotNull
    public static BasedSequence escapeHtml(@NotNull BasedSequence s, boolean preserveEntities, @NotNull ReplacedTextMapper textMapper) {
        Pattern p = preserveEntities ? XML_SPECIAL_OR_ENTITY : XML_SPECIAL_RE;
        return replaceAll(p, s, UNSAFE_CHAR_REPLACER, textMapper);
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s string to un-escape
     * @return un-escaped string
     */
    @NotNull
    public static String unescapeString(@NotNull CharSequence s) {
        if (BACKSLASH_OR_AMP.matcher(s).find()) {
            return replaceAll(ENTITY_OR_ESCAPED_CHAR, s, UNESCAPE_REPLACER);
        } else {
            return String.valueOf(s);
        }
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s                string to un-escape
     * @param unescapeEntities true if HTML entities are to be unescaped
     * @return un-escaped string
     */
    @NotNull
    public static String unescapeString(@NotNull CharSequence s, boolean unescapeEntities) {
        if (unescapeEntities) {
            if (BACKSLASH_OR_AMP.matcher(s).find()) {
                return replaceAll(ESCAPED_CHAR, s, UNESCAPE_REPLACER);
            } else {
                return String.valueOf(s);
            }
        } else {
            if (BACKSLASH_ONLY.matcher(s).find()) {
                return replaceAll(ENTITY_OR_ESCAPED_CHAR, s, UNESCAPE_REPLACER);
            } else {
                return String.valueOf(s);
            }
        }
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s          based sequence to un-escape
     * @param textMapper replaced text mapper to update for the changed text
     * @return un-escaped sequence
     */
    @NotNull
    public static BasedSequence unescape(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        int indexOfAny = s.indexOfAny(AMP_BACKSLASH_SET);
        if (indexOfAny != -1) {
            return replaceAll(ENTITY_OR_ESCAPED_CHAR, s, UNESCAPE_REPLACER, textMapper);
        } else {
            return s;
        }
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s          sequence being changed
     * @param remove     string to remove
     * @param textMapper replaced text mapper to update for the changed text
     * @return un-escaped sequence
     */
    @NotNull
    public static BasedSequence removeAll(@NotNull BasedSequence s, @NotNull CharSequence remove, @NotNull ReplacedTextMapper textMapper) {
        int indexOf = s.indexOf(remove);
        if (indexOf != -1) {
            return replaceAll(Pattern.compile("\\Q" + remove + "\\E"), s, REMOVE_REPLACER, textMapper);
        } else {
            return s;
        }
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s string to un-escape
     * @return un-escaped string
     */
    @NotNull
    public static String unescapeHtml(@NotNull CharSequence s) {
        if (AMP_ONLY.matcher(s).find()) {
            return replaceAll(ENTITY_ONLY, s, ENTITY_REPLACER);
        } else {
            return String.valueOf(s);
        }
    }

    /**
     * Replace entities and backslash escapes with literal characters.
     *
     * @param s          based sequence to un-escape
     * @param textMapper replaced text mapper to update for the changed text
     * @return un-escaped sequence
     */
    @NotNull
    public static BasedSequence unescapeHtml(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        int indexOfAny = s.indexOf('&');
        if (indexOfAny != -1) {
            return replaceAll(ENTITY_ONLY, s, ENTITY_REPLACER, textMapper);
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
    @NotNull
    public static BasedSequence unescapeHtml(@NotNull BasedSequence s, @NotNull List<Range> ranges, @NotNull ReplacedTextMapper textMapper) {
        int indexOfAny = s.indexOf('&');
        if (indexOfAny != -1) {
            return replaceAll(ENTITY_ONLY, s, ranges, ENTITY_REPLACER, textMapper);
        } else {
            return s;
        }
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     * <p>
     * Append EOL sequence if sequence does not already end in EOL
     *
     * @param s sequence to convert
     * @return converted sequence
     */
    @NotNull
    public static String normalizeEndWithEOL(@NotNull CharSequence s) {
        return normalizeEOL(s, true);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     *
     * @param s sequence to convert
     * @return converted sequence
     */
    @NotNull
    public static String normalizeEOL(@NotNull CharSequence s) {
        return normalizeEOL(s, false);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     *
     * @param s          sequence to convert
     * @param endWithEOL true if an EOL is to be appended to the end of the sequence if not already ending with one.
     * @return converted sequence
     */
    @NotNull
    public static String normalizeEOL(@NotNull CharSequence s, boolean endWithEOL) {
        StringBuilder sb = new StringBuilder(s.length());
        int iMax = s.length();
        boolean hadCR = false;
        boolean hadEOL = false;

        for (int i = 0; i < iMax; i++) {
            char c = s.charAt(i);
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
     * @param s          sequence to convert
     * @param textMapper text mapper to update for the replaced text
     * @return converted sequence
     */
    @NotNull
    public static BasedSequence normalizeEndWithEOL(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        return normalizeEOL(s, textMapper, true);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     *
     * @param s          sequence to convert
     * @param textMapper text mapper to update for the replaced text
     * @return converted sequence
     */
    @NotNull
    public static BasedSequence normalizeEOL(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        return normalizeEOL(s, textMapper, false);
    }

    /**
     * Normalize eol: embedded \r and \r\n are converted to \n
     * <p>
     * Append EOL sequence if sequence does not already end in EOL
     *
     * @param s          sequence to convert
     * @param textMapper text mapper to update for the replaced text
     * @param endWithEOL whether an EOL is to be appended to the end of the sequence if it does not already end with one.
     * @return converted sequence
     */
    @NotNull
    public static BasedSequence normalizeEOL(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper, boolean endWithEOL) {
        int iMax = s.length();
        int lastPos = 0;
        boolean hadCR = false;
        boolean hadEOL = false;

        if (textMapper.isModified()) textMapper.startNestedReplacement(s);

        for (int i = 0; i < iMax; i++) {
            char c = s.charAt(i);
            if (c == '\r') {
                hadCR = true;
            } else if (c == '\n') {
                if (hadCR) {
                    // previous was CR, need to take preceding chars
                    if (lastPos < i - 1) textMapper.addOriginalText(lastPos, i - 1);
                    lastPos = i;
                    hadCR = false;
                    hadEOL = true;
                }
            } else {
                if (hadCR) {
                    if (lastPos < i - 1) textMapper.addOriginalText(lastPos, i + 1);
                    textMapper.addReplacedText(i - 1, i, BasedSequence.EOL);
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
            if (lastPos < iMax) textMapper.addOriginalText(lastPos, iMax);
            if (!hadEOL && endWithEOL) textMapper.addReplacedText(iMax - 1, iMax, BasedSequence.EOL);
        }

        return textMapper.getReplacedSequence();
    }

    /**
     * @param s string to encode
     * @return encoded string
     */
    @NotNull
    public static String percentEncodeUrl(@NotNull CharSequence s) {
        return replaceAll(ESCAPE_IN_URI, s, URL_ENCODE_REPLACER);
    }

    /**
     * @param s          string to encode
     * @param textMapper text mapper to update for the replaced text
     * @return encoded string
     */
    @NotNull
    public static BasedSequence percentEncodeUrl(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        return replaceAll(ESCAPE_IN_URI, s, URL_ENCODE_REPLACER, textMapper);
    }

    /**
     * @param s string to encode
     * @return encoded string
     */
    @NotNull
    public static String percentDecodeUrl(@NotNull CharSequence s) {
        return replaceAll(ESCAPE_URI_DECODE, s, URL_DECODE_REPLACER);
    }

    /**
     * @param s          string to encode
     * @param textMapper text mapper to update for the replaced text
     * @return encoded string
     */
    @NotNull
    public static BasedSequence percentDecodeUrl(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        return replaceAll(ESCAPE_URI_DECODE, s, URL_DECODE_REPLACER, textMapper);
    }

    /**
     * Normalize the link reference id
     *
     * @param s          sequence containing the link reference id
     * @param changeCase if true then reference will be converted to lowercase
     * @return normalized link reference id
     */
    @NotNull
    public static String normalizeReference(@NotNull CharSequence s, boolean changeCase) {
        if (changeCase) return Escaping.collapseWhitespace(s.toString(), true).toLowerCase();
        else return Escaping.collapseWhitespace(s.toString(), true);
    }

    @Nullable
    private static String encode(char c) {
        switch (c) {
            case '&':
                return "&amp;";
            case '<':
                return "&lt;";
            case '>':
                return "&gt;";
            case '"':
                return "&quot;";
            case '\'':
                return "&#39;";
        }
        return null;
    }

    private static Random random = new Random(0x2626);

    /**
     * e-mail obfuscation from pegdown
     *
     * @param email     e-mail url
     * @param randomize true to randomize, false for testing
     * @return obfuscated e-mail url
     */
    @NotNull
    public static String obfuscate(@NotNull String email, boolean randomize) {
        if (!randomize) random = new Random(0);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < email.length(); i++) {
            char c = email.charAt(i);
            switch (random.nextInt(5)) {
                case 0:
                case 1:
                    sb.append("&#").append((int) c).append(';');
                    break;
                case 2:
                case 3:
                    sb.append("&#x").append(Integer.toHexString(c)).append(';');
                    break;
                case 4:
                    String encoded = encode(c);
                    if (encoded != null) sb.append(encoded);
                    else sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * Get a normalized the link reference id from reference characters
     * <p>
     * Will remove leading ![ or [ and trailing ], collapse multiple whitespaces to a space and optionally convert the id to lowercase.
     *
     * @param s          sequence containing the link reference id
     * @param changeCase if true then reference will be converted to lowercase
     * @return normalized link reference id
     */
    @NotNull
    public static String normalizeReferenceChars(@NotNull CharSequence s, boolean changeCase) {
        // Strip '[' and ']', then trim and convert to lowercase
        if (s.length() > 1) {
            int stripEnd = s.charAt(s.length() - 1) == ':' ? 2 : 1;
            int stripStart = s.charAt(0) == '!' ? 2 : 1;
            return normalizeReference(s.subSequence(stripStart, s.length() - stripEnd), changeCase);
        }
        return String.valueOf(s);
    }

    /**
     * Collapse regions of multiple white spaces to a single space
     *
     * @param s    sequence to process
     * @param trim true if the sequence should also be trimmed
     * @return processed sequence
     */
    @NotNull
    public static String collapseWhitespace(@NotNull CharSequence s, boolean trim) {
        StringBuilder sb = new StringBuilder(s.length());
        int iMax = s.length();
        boolean hadSpace = false;

        for (int i = 0; i < iMax; i++) {
            char c = s.charAt(i);
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

    @NotNull
    public static BasedSequence collapseWhitespace(@NotNull BasedSequence s, @NotNull ReplacedTextMapper textMapper) {
        return replaceAll(COLLAPSE_WHITESPACE, s, COLLAPSE_WHITESPACE_REPLACER, textMapper);
    }

    @NotNull
    private static String replaceAll(@NotNull Pattern p, @NotNull CharSequence s, @NotNull Replacer replacer) {
        Matcher matcher = p.matcher(s);

        if (!matcher.find()) {
            return String.valueOf(s);
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

    @NotNull
    private static BasedSequence replaceAll(@NotNull Pattern p, @NotNull BasedSequence s, @NotNull Replacer replacer, @NotNull ReplacedTextMapper textMapper) {
        return replaceAll(p, s, 0, s.length(), replacer, textMapper);
    }

    @NotNull
    private static BasedSequence replaceAll(@NotNull Pattern p, @NotNull BasedSequence s, int startOffset, int endOffset, @NotNull Replacer replacer, @NotNull ReplacedTextMapper textMapper) {
        Matcher matcher = p.matcher(s);
        matcher.region(startOffset, endOffset);
        matcher.useTransparentBounds(false);

        if (textMapper.isModified()) {
            textMapper.startNestedReplacement(s);
        }

        if (!matcher.find()) {
            textMapper.addOriginalText(0, s.length());
            return s;
        }

        int lastEnd = 0;
        do {
            textMapper.addOriginalText(lastEnd, matcher.start());
            replacer.replace(s, matcher.start(), matcher.end(), textMapper);
            lastEnd = matcher.end();
        } while (matcher.find());

        if (lastEnd < s.length()) {
            textMapper.addOriginalText(lastEnd, s.length());
        }

        return textMapper.getReplacedSequence();
    }

    @NotNull
    private static BasedSequence replaceAll(@NotNull Pattern p, @NotNull BasedSequence s, @NotNull List<Range> ranges, @NotNull Replacer replacer, @NotNull ReplacedTextMapper textMapper) {
        Matcher matcher = p.matcher(s);
        matcher.useTransparentBounds(false);

        if (textMapper.isModified()) {
            textMapper.startNestedReplacement(s);
        }

        int lastEnd = 0;

        for (Range range : ranges) {
            int start = Utils.rangeLimit(range.getStart(), lastEnd, s.length());
            int end = Utils.rangeLimit(range.getEnd(), start, s.length());
            matcher.region(start, end);

            while (matcher.find()) {
                textMapper.addOriginalText(lastEnd, matcher.start());
                replacer.replace(s, matcher.start(), matcher.end(), textMapper);
                lastEnd = matcher.end();
            }
        }

        if (lastEnd < s.length()) {
            textMapper.addOriginalText(lastEnd, s.length());
        }

        return textMapper.getReplacedSequence();
    }

    interface Replacer {
        void replace(@NotNull String s, @NotNull StringBuilder sb);

        void replace(@NotNull BasedSequence s, int startIndex, int endIndex, @NotNull ReplacedTextMapper replacedTextMapper);
    }
}
