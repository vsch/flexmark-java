package com.vladsch.flexmark.profiles.pegdown;

public interface Extensions {

    /**
     * The default, standard markup mode without any extensions.
     */
    static final int NONE = 0x00;

    /**
     * Pretty ellipses, dashes and apostrophes.
     */
    static final int SMARTS = 0x01;

    /**
     * Pretty single and double quotes.
     */
    static final int QUOTES = 0x02;

    /**
     * All of the smartypants prettyfications. Equivalent to SMARTS + QUOTES.
     *
     * @see <a href="http://daringfireball.net/projects/smartypants/">Smartypants</a>
     */
    static final int SMARTYPANTS = SMARTS + QUOTES;

    /**
     * PHP Markdown Extra style abbreviations.
     *
     * @see <a href="http://michelf.com/projects/php-markdown/extra/#abbr">PHP Markdown Extra</a>
     */
    static final int ABBREVIATIONS = 0x04;

    /**
     * Enables the parsing of hard wraps as HTML linebreaks. Similar to what github does.
     *
     * @see <a href="http://github.github.com/github-flavored-markdown">Github-flavored-Markdown</a>
     */
    static final int HARDWRAPS = 0x08;

    /**
     * Enables plain autolinks the way github flavoured markdown implements them.
     * With this extension enabled pegdown will intelligently recognize URLs and email addresses
     * without any further delimiters and mark them as the respective link type.
     *
     * @see <a href="http://github.github.com/github-flavored-markdown">Github-flavored-Markdown</a>
     */
    static final int AUTOLINKS = 0x10;

    /**
     * Table support similar to what Multimarkdown offers.
     *
     * @see <a href="http://fletcherpenney.net/multimarkdown/users_guide/">MultiMarkdown</a>
     */
    static final int TABLES = 0x20;

    /**
     * PHP Markdown Extra style definition lists.
     * Additionally supports the small extension proposed in the article referenced below.
     *
     * @see <a href="http://michelf.com/projects/php-markdown/extra/#def-list">PHP Markdown Extra</a>
     * @see <a href="http://www.justatheory.com/computers/markup/modest-markdown-proposal.html">Extension proposal</a>
     */
    static final int DEFINITIONS = 0x40;

    /**
     * PHP Markdown Extra style fenced code blocks.
     *
     * @see <a href="http://michelf.com/projects/php-markdown/extra/#fenced-code-blocks">PHP Markdown Extra</a>
     */
    static final int FENCED_CODE_BLOCKS = 0x80;

    /**
     * Support [[Wiki-style links]]. URL rendering is performed by the active {@link LinkRenderer}.
     *
     * @see <a href="http://github.github.com/github-flavored-markdown">Github-flavored-Markdown</a>
     */
    static final int WIKILINKS = 0x100;

    /**
     * Support ~~strikethroughs~~ as supported in Pandoc and Github.
     */
    static final int STRIKETHROUGH = 0x200;

    /**
     * Enables anchor links in headers.
     */
    static final int ANCHORLINKS = 0x400;

    /**
     * All available extensions excluding the SUPPRESS_... options, ATXHEADERSPACE. FORCELISTITEMPARA
     */
    static final int ALL = 0x0000FFFF;

    /**
     * Suppresses HTML blocks. They will be accepted in the input but not be contained in the output.
     */
    static final int SUPPRESS_HTML_BLOCKS = 0x00010000;

    /**
     * Suppresses inline HTML tags. They will be accepted in the input but not be contained in the output.
     */
    static final int SUPPRESS_INLINE_HTML = 0x00020000;

    /**
     * Suppresses HTML blocks as well as inline HTML tags.
     * Both will be accepted in the input but not be contained in the output.
     */
    static final int SUPPRESS_ALL_HTML = 0x00030000;

    /**
     * Requires a space char after Atx # header prefixes, so that #dasdsdaf is not a header.
     */
    static final int ATXHEADERSPACE = 0x00040000;

    /**
     * Force List and Definition Paragraph wrapping if it includes more than just a single paragraph
     */
    static final int FORCELISTITEMPARA = 0x00080000;

    /**
     * Allow horizontal rules without a blank line following them.
     */
    static final int RELAXEDHRULES = 0x00100000;

    /**
     * GitHub style task list items: - [ ] and - [x]
     */
    static final int TASKLISTITEMS = 0x00200000;

    /**
     * Generate anchor links for headers using complete contents of the header.
     * Spaces and non-alphanumerics replaced by `-`, multiple dashes trimmed to one.
     * Anchor link is added as first element inside the header with empty content: `<h1><a name="header-a"></a>header a</h1>`
     */
    static final int EXTANCHORLINKS = 0x00400000;

    /**
     * EXTANCHORLINKS should wrap header content instead of creating an empty anchor: `<h1><a name="header-a">header a</a></h1>`
     */
    static final int EXTANCHORLINKS_WRAP = 0x00800000;

    /**
     * Enables footnote processing [^1]: Text Paragraph with continuations
     * and footnote reference [^1]
     */
    static final int FOOTNOTES = 0x01000000;

    /**
     * Enables TOC extension
     */
    static final int TOC = 0x02000000;

    /**
     * ![alt](.....?
     *
     * )
     *
     * ![alt](.....?
     *
     * "title")
     *
     * Enables MULTI_LINE_IMAGE_URLS extension which allows image urls of the form above.
     * any text at all until ) or "title") at the begining of a line.
     * Used for displaying UML diagrams with gravizo.com
     */
    static final int MULTI_LINE_IMAGE_URLS = 0x04000000;

    /**
     * trace parsing elements to console
     */
    static final int TRACE_PARSER = 0x08000000;

    /**
     * allow Strong/Emphasis marks to start when not preceded by alphanumeric for _ and
     * as long as not surrounded by spaces for * instead of only when preceded by spaces.
     */
    static final int RELAXED_STRONG_EMPHASIS_RULES = 0x10000000;

    /**
     * Include \u001F in Letters() so that IntelliJ Code Completion Marker will be parsed as part of Identifier
     * and all elements of markdown that allow Alphanumerics. Only needed by idea-multimarkdown.
     *
     * Also allows zerowidth labels for [^] for the same reason, so completions can work without
     * typing any characters between the brackets
     *
     */
    static final int INTELLIJ_DUMMY_IDENTIFIER = 0x20000000;

    /**
     * Enables adding a dummy reference key node to RefLink and RefImage so that the AST differs
     * between [ ][] and plain [ ] for refLink and ![ ][] and plain ![ ] for RefImage
     */
    static final int DUMMY_REFERENCE_KEY = 0x40000000;


    static final int UNUSABLE = 0x80000000;

    /**
     * All Optionals other than Suppress and FORCELISTITEMPARA which is a backwards compatibility extension
     */

    static final int ALL_OPTIONALS = (ATXHEADERSPACE | RELAXEDHRULES | TASKLISTITEMS | EXTANCHORLINKS | FOOTNOTES);
    static final int ALL_WITH_OPTIONALS = ALL | (ATXHEADERSPACE | RELAXEDHRULES | TASKLISTITEMS | FOOTNOTES);

    /**
     * These are GitHub main repo document processing compatibility flags
     */
    static final int GITHUB_DOCUMENT_COMPATIBLE = (FENCED_CODE_BLOCKS | TABLES | AUTOLINKS | ANCHORLINKS | TASKLISTITEMS | STRIKETHROUGH | ATXHEADERSPACE | RELAXEDHRULES | RELAXED_STRONG_EMPHASIS_RULES);

    /**
     * These are GitHub wiki page processing compatibility flags
     */
    static final int GITHUB_WIKI_COMPATIBLE = (GITHUB_DOCUMENT_COMPATIBLE | WIKILINKS);

    /**
     * These are GitHub comment (issues, pull requests and comments) processing compatibility flags
     */
    static final int GITHUB_COMMENT_COMPATIBLE = (GITHUB_DOCUMENT_COMPATIBLE | HARDWRAPS);
}
