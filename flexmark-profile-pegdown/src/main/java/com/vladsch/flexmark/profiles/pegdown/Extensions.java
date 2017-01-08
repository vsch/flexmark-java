package com.vladsch.flexmark.profiles.pegdown;

public interface Extensions {

    /**
     * The default, standard markup mode without any extensions.
     */
    static final int NONE = 0x00;
    //int NONE = 0;

    /**
     * Pretty ellipses, dashes and apostrophes.
     */
    static final int SMARTS = 0x01;
    //int SMARTS = 1;

    /**
     * Pretty single and double quotes.
     */
    static final int QUOTES = 0x02;
    //int QUOTES = 2;

    /**
     * All of the smartypants prettyfications. Equivalent to SMARTS + QUOTES.
     *
     * @see <a href="http://daringfireball.net/projects/smartypants/">Smartypants</a>
     */
    static final int SMARTYPANTS = SMARTS + QUOTES;
    //int SMARTYPANTS = 3;

    /**
     * PHP Markdown Extra style abbreviations.
     *
     * @see <a href="http://michelf.com/projects/php-markdown/extra/#abbr">PHP Markdown Extra</a>
     */
    static final int ABBREVIATIONS = 0x04;
    //int ABBREVIATIONS = 4;

    /**
     * Enables the parsing of hard wraps as HTML linebreaks. Similar to what github does.
     *
     * @see <a href="http://github.github.com/github-flavored-markdown">Github-flavored-Markdown</a>
     */
    static final int HARDWRAPS = 0x08;
    //int HARDWRAPS = 8;

    /**
     * Enables plain autolinks the way github flavoured markdown implements them.
     * With this extension enabled pegdown will intelligently recognize URLs and email addresses
     * without any further delimiters and mark them as the respective link type.
     *
     * @see <a href="http://github.github.com/github-flavored-markdown">Github-flavored-Markdown</a>
     */
    static final int AUTOLINKS = 0x10;
    //int AUTOLINKS = 16;

    /**
     * Table support similar to what Multimarkdown offers.
     *
     * @see <a href="http://fletcherpenney.net/multimarkdown/users_guide/">MultiMarkdown</a>
     */
    static final int TABLES = 0x20;
    //int TABLES = 32;

    /**
     * PHP Markdown Extra style definition lists.
     * Additionally supports the small extension proposed in the article referenced below.
     *
     * @see <a href="http://michelf.com/projects/php-markdown/extra/#def-list">PHP Markdown Extra</a>
     * @see <a href="http://www.justatheory.com/computers/markup/modest-markdown-proposal.html">Extension proposal</a>
     */
    static final int DEFINITIONS = 0x40;
    //int DEFINITIONS = 64;

    /**
     * PHP Markdown Extra style fenced code blocks.
     *
     * @see <a href="http://michelf.com/projects/php-markdown/extra/#fenced-code-blocks">PHP Markdown Extra</a>
     */
    static final int FENCED_CODE_BLOCKS = 0x80;
    //int FENCED_CODE_BLOCKS = 128;

    /**
     * Support [[Wiki-style links]]. URL rendering is performed by the active LinkRenderer.
     *
     * @see <a href="http://github.github.com/github-flavored-markdown">Github-flavored-Markdown</a>
     */
    static final int WIKILINKS = 0x100;
    //int WIKILINKS = 256;

    /**
     * Support ~~strikethroughs~~ as supported in Pandoc and Github.
     */
    static final int STRIKETHROUGH = 0x200;
    //int STRIKETHROUGH = 512;

    /**
     * Enables anchor links in headers.
     */
    static final int ANCHORLINKS = 0x400;
    //int ANCHORLINKS = 1024;

    /**
     * All available extensions excluding the SUPPRESS_... options, ATXHEADERSPACE. FORCELISTITEMPARA
     */
    static final int ALL = 0x0000FFFF;
    //int ALL = 65535;

    /**
     * Suppresses HTML blocks. They will be accepted in the input but not be contained in the output.
     */
    static final int SUPPRESS_HTML_BLOCKS = 0x00010000;
    //int SUPPRESS_HTML_BLOCKS = 65536;

    /**
     * Suppresses inline HTML tags. They will be accepted in the input but not be contained in the output.
     */
    static final int SUPPRESS_INLINE_HTML = 0x00020000;
    //int SUPPRESS_INLINE_HTML = 131072;

    /**
     * Suppresses HTML blocks as well as inline HTML tags.
     * Both will be accepted in the input but not be contained in the output.
     */
    static final int SUPPRESS_ALL_HTML = 0x00030000;
    //int SUPPRESS_ALL_HTML = 196608;

    /**
     * Requires a space char after Atx # header prefixes, so that #dasdsdaf is not a header.
     */
    static final int ATXHEADERSPACE = 0x00040000;
    //int ATXHEADERSPACE = 262144;

    /**
     * Force List and Definition Paragraph wrapping if it includes more than just a single paragraph
     */
    static final int FORCELISTITEMPARA = 0x00080000;
    //int FORCELISTITEMPARA = 524288;

    /**
     * Allow horizontal rules without a blank line following them.
     */
    static final int RELAXEDHRULES = 0x00100000;
    //int RELAXEDHRULES = 1048576;

    /**
     * GitHub style task list items: - [ ], - [x] and - [X]
     */
    static final int TASKLISTITEMS = 0x00200000;
    //int TASKLISTITEMS = 2097152;

    /**
     * Generate anchor links for headers using complete contents of the header.
     * Spaces and non-alphanumerics replaced by `-`, multiple dashes trimmed to one.
     * Anchor link is added as first element inside the header with empty content: `<h1><a name="header-a"></a>header a</h1>`
     */
    static final int EXTANCHORLINKS = 0x00400000;
    //int EXTANCHORLINKS = 4194304;

    static final int NOT_USED0 = 0x00800000;
    static final int NOT_USED1 = 0x01000000;
    static final int NOT_USED2 = 0x02000000;
    static final int NOT_USED3 = 0x04000000;
    static final int NOT_USED4 = 0x08000000;
    static final int NOT_USED5 = 0x10000000;
    static final int NOT_USED6 = 0x20000000;
    static final int NOT_USED7 = 0x40000000;
    static final int UNUSABLE = 0x80000000;

    /**
     * All Optionals other than Suppress and FORCELISTITEMPARA which is a backwards compatibility extension
     */

    static final int ALL_OPTIONALS = (ATXHEADERSPACE | RELAXEDHRULES | TASKLISTITEMS | EXTANCHORLINKS);
    //int ALL_OPTIONALS = 7602176;
    static final int ALL_WITH_OPTIONALS = ALL | (ATXHEADERSPACE | RELAXEDHRULES | TASKLISTITEMS);
    //int ALL_WITH_OPTIONALS = 3473407;
}
