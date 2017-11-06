package com.vladsch.flexmark.profiles.pegdown;

import com.vladsch.flexmark.Extension;
import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.SubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.superscript.SuperscriptExtension;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.DataKey;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.ArrayList;
import java.util.Arrays;

import static com.vladsch.flexmark.profiles.pegdown.Extensions.*;

public class PegdownOptionsAdapter {
    public static final DataKey<Integer> PEGDOWN_EXTENSIONS = new DataKey<Integer>("PEGDOWN_EXTENSIONS", ALL);

    private final MutableDataSet myOptions;
    private int myPegdownExtensions = 0;
    private boolean myIsUpdateNeeded = false;

    public PegdownOptionsAdapter() {
        myOptions = new MutableDataSet();
    }

    public PegdownOptionsAdapter(DataHolder dataSet) {
        myOptions = new MutableDataSet(dataSet);
    }

    public PegdownOptionsAdapter(int pegdownExtensions) {
        myOptions = new MutableDataSet();
        myPegdownExtensions = pegdownExtensions;
        myIsUpdateNeeded = true;
    }

    public static DataHolder flexmarkOptions(int pegdownExtensions, Extension... extensions) {
        return flexmarkOptions(false, pegdownExtensions, extensions);
    }

    public static DataHolder flexmarkOptions(boolean strictHtml, int pegdownExtensions, Extension... extensions) {
        PegdownOptionsAdapter optionsAdapter = new PegdownOptionsAdapter(pegdownExtensions);
        return optionsAdapter.getFlexmarkOptions(strictHtml, extensions);
    }

    public boolean haveExtensions(int mask) {
        return (myPegdownExtensions & mask) != 0;
    }

    public boolean allExtensions(int mask) {
        return (myPegdownExtensions & mask) == mask;
    }

    public DataHolder getFlexmarkOptions(Extension... additionalExtensions) {
        return getFlexmarkOptions(false,additionalExtensions);
    }

    public DataHolder getFlexmarkOptions(boolean strictHtml, Extension... additionalExtensions) {
        if (myIsUpdateNeeded) {
            myIsUpdateNeeded = false;
            MutableDataSet options = myOptions;
            ArrayList<Extension> extensions = new ArrayList<Extension>();

            options.clear();

            // add this for extension use if needed
            options.set(PEGDOWN_EXTENSIONS, myPegdownExtensions);

            extensions.addAll(Arrays.asList(additionalExtensions));

            // Setup List Options for Fixed List Indent profile
            options.setFrom(strictHtml ? ParserEmulationProfile.PEGDOWN_STRICT : ParserEmulationProfile.PEGDOWN);

            options.set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, haveExtensions(SUPPRESS_HTML_BLOCKS));
            options.set(HtmlRenderer.SUPPRESS_INLINE_HTML, haveExtensions(SUPPRESS_INLINE_HTML));

            // add default extensions in pegdown
            extensions.add(EscapedCharacterExtension.create());

            if (haveExtensions(ABBREVIATIONS)) {
                extensions.add(AbbreviationExtension.create());
                options.set(AbbreviationExtension.ABBREVIATIONS_KEEP, KeepType.LAST);
            }

            if (haveExtensions(ANCHORLINKS | EXTANCHORLINKS)) {
                options.set(HtmlRenderer.RENDER_HEADER_ID, false);
                extensions.add(AnchorLinkExtension.create());
                if (haveExtensions(EXTANCHORLINKS)) {
                    options.set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false);
                } else if (haveExtensions(ANCHORLINKS)) {
                    options.set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, true);
                }
            }

            if (haveExtensions(AUTOLINKS)) {
                extensions.add(AutolinkExtension.create());
            }

            if (haveExtensions(DEFINITIONS)) {
                // not implemented yet, but have placeholder
                extensions.add(DefinitionExtension.create());
            }

            if (!haveExtensions(FENCED_CODE_BLOCKS)) {
                // disable fenced code blocks
                options.set(Parser.FENCED_CODE_BLOCK_PARSER, false);
            } else {
                options.set(Parser.MATCH_CLOSING_FENCE_CHARACTERS, false);
            }

            if (haveExtensions(FORCELISTITEMPARA)) {
                // first item is loose if second item is loose
                options.set(Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN, true);
            } else {
                // should already be set
            }

            if (haveExtensions(HARDWRAPS)) {
                options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
                options.set(HtmlRenderer.HARD_BREAK, "<br />\n");
            }

            if (!haveExtensions(ATXHEADERSPACE)) {
                options.set(Parser.HEADING_NO_ATX_SPACE, true);
            }

            if (haveExtensions(QUOTES | SMARTS)) {
                // not implemented yet, have placeholder
                extensions.add(TypographicExtension.create());
                options.set(TypographicExtension.ENABLE_SMARTS, haveExtensions(SMARTS));
                options.set(TypographicExtension.ENABLE_QUOTES, haveExtensions(QUOTES));
            }

            if (!haveExtensions(RELAXEDHRULES)) {
                options.set(Parser.THEMATIC_BREAK_RELAXED_START, false);
            }

            if (haveExtensions(TABLES)) {
                extensions.add(TablesExtension.create());
                options.set(TablesExtension.TRIM_CELL_WHITESPACE, false);
                options.set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, false);
            }

            if (haveExtensions(TASKLISTITEMS)) {
                extensions.add(TaskListExtension.create());
            }

            if (haveExtensions(WIKILINKS)) {
                extensions.add(WikiLinkExtension.create());
                // pegdown does not have an option for selecting Creole or GitHub wiki link syntax: Creole puts page ref first, link text second, GitHub the other way around
                options.set(WikiLinkExtension.LINK_FIRST_SYNTAX, false);
            }

            if (haveExtensions(SUBSCRIPT) && haveExtensions(STRIKETHROUGH)) {
                // first item is loose if second item is loose
                extensions.add(StrikethroughSubscriptExtension.create());
            } else if (haveExtensions(STRIKETHROUGH)) {
                extensions.add(StrikethroughExtension.create());
            } else if (haveExtensions(SUBSCRIPT)) {
                extensions.add(SubscriptExtension.create());
            }

            if (haveExtensions(SUPERSCRIPT)) {
                extensions.add(SuperscriptExtension.create());
            }

            if (haveExtensions(INSERTED)) {
                extensions.add(InsExtension.create());
            }

            if (haveExtensions(TOC)) {
                extensions.add(SimTocExtension.create());
                options.set(TocExtension.BLANK_LINE_SPACER, true);

                extensions.add(TocExtension.create());
                options.set(TocExtension.LEVELS, TocOptions.getLevels(2, 3));
            }

            // pegdown does not have emoji shortcuts
            if (false) {
                extensions.add(EmojiExtension.create());
                // need to set the location of the emoji icons from emoji-cheat-sheet on github or http://www.emoji-cheat-sheet.com/
                //options.set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory());

                // this will use GitHub URLs for emoji icons, not recommended
                options.set(EmojiExtension.USE_IMAGE_URLS, true);
            }

            if (haveExtensions(MULTI_LINE_IMAGE_URLS)) {
                options.set(Parser.PARSE_MULTI_LINE_IMAGE_URLS, true);
            }

            if (haveExtensions(FOOTNOTES)) {
                extensions.add(FootnoteExtension.create());
                options.set(FootnoteExtension.FOOTNOTES_KEEP, KeepType.LAST);
            }

            myOptions.set(Parser.EXTENSIONS, extensions);
        }

        return myOptions.toImmutable();
    }

    public PegdownOptionsAdapter setPegdownExtensions(int pegdownExtensions) {
        //noinspection PointlessBitwiseExpression
        myPegdownExtensions = pegdownExtensions;
        myIsUpdateNeeded = true;
        return this;
    }

    public PegdownOptionsAdapter addPegdownExtensions(int pegdownExtensions) {
        //noinspection PointlessBitwiseExpression
        myPegdownExtensions |= pegdownExtensions;
        myIsUpdateNeeded = true;
        return this;
    }

    public PegdownOptionsAdapter removePegdownExtensions(int pegdownExtensions) {
        //noinspection PointlessBitwiseExpression
        myPegdownExtensions &= ~pegdownExtensions;
        myIsUpdateNeeded = true;
        return this;
    }
}
