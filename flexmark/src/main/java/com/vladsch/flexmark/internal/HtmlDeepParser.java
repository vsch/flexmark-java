package com.vladsch.flexmark.internal;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.vladsch.flexmark.internal.HtmlDeepParser.HtmlMatch.OPEN_TAG;

class HtmlDeepParser {
    public enum HtmlMatch {
        NONE(null, null, false),
        SCRIPT("<(script)(?:\\s|>|$)", "</script>", true),
        STYLE("<(style)(?:\\s|>|$)", "</style>", true),
        OPEN_TAG("<([A-Za-z][A-Za-z0-9-]*)", "<|/>|\\s/>|>", true),
        CLOSE_TAG("</([A-Za-z][A-Za-z0-9-]*)>", null, true),
        NON_TAG("<(![A-Z])", ">", false),
        TEMPLATE("<([?])", "\\?>", false),
        COMMENT("<(!--)", "-->", false),
        CDATA("<!\\[(CDATA)\\[", "\\]\\]>", false),
        ;

        public final Pattern open;
        public final Pattern close;
        public final boolean caseInsentive;

        HtmlMatch(final String open, final String close, final boolean caseInsentive) {
            this.open = open == null ? null : Pattern.compile(open, caseInsentive ? Pattern.CASE_INSENSITIVE : 0);
            this.close = close == null ? null : Pattern.compile(close, caseInsentive ? Pattern.CASE_INSENSITIVE : 0);
            this.caseInsentive = caseInsentive;
        }
    }

    public static final Set<String> BLOCK_TAGS;
    public static final Set<String> VOID_TAGS;
    public static final Pattern START_PATTERN;
    private static final HtmlMatch[] PATTERN_MAP;
    static {
        BLOCK_TAGS = new HashSet<String>();
        VOID_TAGS = new HashSet<String>();

        String[] blockTags = ("address|article|aside|" +
                "base|basefont|blockquote|body|" +
                "caption|center|col|colgroup|" +
                "dd|details|dialog|dir|div|dl|dt|" +
                "fieldset|figcaption|figure|footer|form|frame|frameset|" +
                "h1|h2|h3|h4|h5|h6|head|header|hr|html|" +
                "iframe|" +
                "legend|li|link|" +
                "main|menu|menuitem|meta|" +
                "nav|noframes|" +
                "ol|optgroup|option|" +
                "p|param|pre|" +
                "section|source|summary|" +
                "table|tbody|td|tfoot|th|thead|title|tr|track|" +
                "ul").split("\\|");
        BLOCK_TAGS.addAll(Arrays.asList(blockTags));

        String[] voidTags = ("area|base|br|col|embed|hr|img|input|keygen|link|menuitem|meta|param|source|track|wbr").split("\\|");
        VOID_TAGS.addAll(Arrays.asList(voidTags));

        // combine all patterns and create map by pattern number
        PATTERN_MAP = new HtmlMatch[HtmlMatch.values().length];
        StringBuilder startPattern = new StringBuilder();
        int index = 0;
        for (HtmlMatch state : HtmlMatch.values()) {
            if (state != HtmlMatch.NONE) {
                if (startPattern.length() != 0) startPattern.append("|");
                if (state.caseInsentive) {
                    startPattern.append("(?i:");
                    startPattern.append(state.open.pattern());
                    startPattern.append(")");
                } else {
                    startPattern.append(state.open.pattern());
                }
                PATTERN_MAP[index] = state;
            }
            index++;
        }

        START_PATTERN = Pattern.compile(startPattern.toString());
    }

    private ArrayList<String> myOpenTags;
    private Pattern myClosingPattern;
    private HtmlMatch myHtmlMatch;
    private int myHtmlCount;

    public HtmlDeepParser() {
        myOpenTags = new ArrayList<String>();
        myClosingPattern = null;
        myHtmlMatch = null;
        myHtmlCount = 0;
    }

    public ArrayList<String> getOpenTags() {
        return myOpenTags;
    }

    public Pattern getClosingPattern() {
        return myClosingPattern;
    }

    public HtmlMatch getHtmlMatch() {
        return myHtmlMatch;
    }

    public int getHtmlCount() {
        return myHtmlCount;
    }

    public boolean isHtmlClosed() {
        return myClosingPattern == null && myOpenTags.isEmpty();
    }

    public boolean isBlankLineIterruptible() {
        return (myOpenTags.isEmpty() && myClosingPattern == null || myHtmlMatch == OPEN_TAG && myClosingPattern != null && myOpenTags.size() == 1);
    }

    public boolean haveOpenRawTag() {
        return myClosingPattern != null && myHtmlMatch != OPEN_TAG;
    }

    public boolean hadHtml() {
        return myHtmlCount > 0 || !isHtmlClosed();
    }

    public void parseHtmlChunk(CharSequence html, boolean blockTagsOnly, final boolean parseNonBlock, final boolean firstOpenTagOnOneLine) {
        if (myHtmlCount == 0 && myHtmlMatch != null) {
            myHtmlCount++;
        }

        String pendingOpen = null;
        boolean useFirstOpenTagOnOneLine = firstOpenTagOnOneLine;

        while (html.length() != 0) {
            if (myClosingPattern != null) {
                // see if we find HTML pattern
                Matcher matcher = myClosingPattern.matcher(html);
                if (!matcher.find()) break;

                if (myHtmlMatch == OPEN_TAG) {
                    if (matcher.group().equals("<")) {
                        // previous open tag not closed, drop it and re-parse from <
                        if (pendingOpen == null) {
                            myOpenTags.remove(myOpenTags.size() - 1);
                        } else {
                            if (useFirstOpenTagOnOneLine) {
                                // not recognized as html, skip the line
                                pendingOpen = null;
                                myClosingPattern = null;
                                break;
                            }
                        }
                    } else {
                        useFirstOpenTagOnOneLine = false;
                        if (matcher.group().endsWith("/>")) {
                            // drop the tag, it is self closed
                            if (pendingOpen == null) {
                                myOpenTags.remove(myOpenTags.size() - 1);
                            }
                            if (myHtmlCount == 0) myHtmlCount++;
                        } else {
                            if (pendingOpen != null) {
                                // now we have it
                                if (!VOID_TAGS.contains(pendingOpen)) {
                                    myOpenTags.add(pendingOpen);
                                }
                                myHtmlCount++;
                            }
                        }
                        html = html.subSequence(matcher.end(), html.length());
                    }
                } else {
                    html = html.subSequence(matcher.end(), html.length());
                }

                pendingOpen = null;
                myClosingPattern = null;
            } else {
                // start pattern
                Matcher matcher = START_PATTERN.matcher(html);
                if (!matcher.find()) break;

                final CharSequence nextHtml = html.subSequence(matcher.end(), html.length());
                final String match = matcher.group();
                int iMax = PATTERN_MAP.length;
                myClosingPattern = null;

                for (int i = 1; i < iMax; i++) {
                    if (matcher.group(i) == null) continue;

                    final String group = matcher.group(i).toLowerCase();
                    HtmlMatch htmlMatch = PATTERN_MAP[i];
                    final boolean isBlockTag = BLOCK_TAGS.contains(group);

                    if ((blockTagsOnly || !parseNonBlock) && matcher.start() > 0) {
                        // nothing but blanks allowed before first pattern match when block tags only
                        String leading = html.subSequence(0, matcher.start()).toString();
                        if (!leading.trim().isEmpty()) break;
                    }

                    // see if self closed and if void or block
                    if (htmlMatch != OPEN_TAG && htmlMatch != htmlMatch.CLOSE_TAG) {
                        // block and has closing tag sequence
                        myClosingPattern = htmlMatch.close;
                        myHtmlMatch = htmlMatch;
                        myHtmlCount++;
                        useFirstOpenTagOnOneLine = false;
                        break;
                    }

                    if ((blockTagsOnly || !parseNonBlock) && !isBlockTag) {
                        // we ignore this one, not block
                        break;
                    }

                    // now anything goes
                    blockTagsOnly = false;

                    // if not void or self-closed then add it to the stack
                    if (htmlMatch == OPEN_TAG && VOID_TAGS.contains(group)) {
                        // no closing pattern and we don't push tag
                        if (useFirstOpenTagOnOneLine) {
                            pendingOpen = group;
                        } else {
                            myHtmlMatch = htmlMatch;
                            myHtmlCount++;
                        }
                        break;
                    }

                    if (htmlMatch == OPEN_TAG) {
                        // open tag, push to the stack
                        myHtmlMatch = htmlMatch;
                        myClosingPattern = htmlMatch.close;
                        if (useFirstOpenTagOnOneLine) {
                           pendingOpen = group;
                        } else {
                            myOpenTags.add(group);
                            if (myHtmlCount != 0) myHtmlCount++;
                        }
                    } else {
                        // closing tag, pop it if in the stack, or pop intervening ones if have match higher up
                        int jMax = myOpenTags.size();
                        myHtmlMatch = htmlMatch;
                        myHtmlCount++;
                        for (int j = jMax; j-- > 0; ) {
                            final String openTag = myOpenTags.get(j);
                            if (openTag.equals(group)) {
                                // drop all to end of stack
                                for (int k = jMax; k-- > j; ) {
                                    myOpenTags.remove(j);
                                }
                                break;
                            }

                            if (!isBlockTag) {
                                // don't close unmatched block tag by closing non-block tag.
                                if (BLOCK_TAGS.contains(openTag)) break;
                            }
                        }
                    }

                    break;
                }

                html = nextHtml;
            }
        }

        if (pendingOpen != null && myHtmlMatch == OPEN_TAG) {
            // didn't close, forget it
            myClosingPattern = null;
        }
    }
}
