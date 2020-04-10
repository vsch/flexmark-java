package com.vladsch.flexmark.profile.pegdown;

import com.vladsch.flexmark.ext.abbreviation.AbbreviationExtension;
import com.vladsch.flexmark.ext.anchorlink.AnchorLinkExtension;
import com.vladsch.flexmark.ext.autolink.AutolinkExtension;
import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.escaped.character.EscapedCharacterExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.SubscriptExtension;
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.KeepType;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataKey;
import com.vladsch.flexmark.util.data.MutableDataSet;
import com.vladsch.flexmark.util.misc.Extension;

import java.util.ArrayList;
import java.util.Arrays;

import static com.vladsch.flexmark.profile.pegdown.Extensions.*;

public class PegdownOptionsAdapter {
    final public static DataKey<Integer> PEGDOWN_EXTENSIONS = ParserEmulationProfile.PEGDOWN_EXTENSIONS;

    final private MutableDataSet myOptions;
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

    /**
     * Test if any of the given extensions are in the mask
     *
     * @param mask extension flag mask
     * @return true if any of the extensions given by the mask
     */
    public boolean haveAnyExtensions(int mask) {
        return ParserEmulationProfile.haveAny(myPegdownExtensions, mask);
    }

    /**
     * Test if all given extensions are in the mask
     *
     * @param mask extension flag mask
     * @return true if any of the extensions given by the mask
     */
    public boolean haveAllExtensions(int mask) {
        return ParserEmulationProfile.haveAll(myPegdownExtensions, mask);
    }

    public DataHolder getFlexmarkOptions(Extension... additionalExtensions) {
        return getFlexmarkOptions(false, additionalExtensions);
    }

    public DataHolder getFlexmarkOptions(boolean strictHtml, Extension... additionalExtensions) {
        if (myIsUpdateNeeded) {
            myIsUpdateNeeded = false;
            MutableDataSet options = myOptions;
            ArrayList<Extension> extensions = new ArrayList<>(Arrays.asList(additionalExtensions));

            options.clear();

            // add this for extension use if needed
            options.set(ParserEmulationProfile.PEGDOWN_EXTENSIONS, myPegdownExtensions);

            // Setup List Options for Fixed List Indent profile
            options.setFrom(strictHtml ? ParserEmulationProfile.PEGDOWN_STRICT : ParserEmulationProfile.PEGDOWN);

            options.set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, haveAnyExtensions(SUPPRESS_HTML_BLOCKS));
            options.set(HtmlRenderer.SUPPRESS_INLINE_HTML, haveAnyExtensions(SUPPRESS_INLINE_HTML));

            // add default extensions in pegdown
            extensions.add(EscapedCharacterExtension.create());

            if (haveAnyExtensions(ABBREVIATIONS)) {
                extensions.add(AbbreviationExtension.create());
                options.set(AbbreviationExtension.ABBREVIATIONS_KEEP, KeepType.LAST);
            }

            if (haveAnyExtensions(ANCHORLINKS | EXTANCHORLINKS)) {
                options.set(HtmlRenderer.RENDER_HEADER_ID, false);
                extensions.add(AnchorLinkExtension.create());
                if (haveAnyExtensions(EXTANCHORLINKS)) {
                    options.set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, false);
                } else if (haveAnyExtensions(ANCHORLINKS)) {
                    options.set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, true);
                }
            }

            if (haveAnyExtensions(AUTOLINKS)) {
                extensions.add(AutolinkExtension.create());
            }

            if (haveAnyExtensions(DEFINITIONS)) {
                // not implemented yet, but have placeholder
                extensions.add(DefinitionExtension.create());
            }

            if (!haveAnyExtensions(FENCED_CODE_BLOCKS)) {
                // disable fenced code blocks
                options.set(Parser.FENCED_CODE_BLOCK_PARSER, false);
            } else {
                options.set(Parser.MATCH_CLOSING_FENCE_CHARACTERS, false);
            }

            if (haveAnyExtensions(FORCELISTITEMPARA)) {
                // first item is loose if second item is loose
                options.set(Parser.LISTS_LOOSE_WHEN_HAS_NON_LIST_CHILDREN, true);
            } else {
                // should already be set
            }

            if (haveAnyExtensions(HARDWRAPS)) {
                options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
                options.set(HtmlRenderer.HARD_BREAK, "<br />\n");
            }

            if (!haveAnyExtensions(ATXHEADERSPACE)) {
                options.set(Parser.HEADING_NO_ATX_SPACE, true);
            }

            if (haveAnyExtensions(QUOTES | SMARTS)) {
                // not implemented yet, have placeholder
                extensions.add(TypographicExtension.create());
                options.set(TypographicExtension.ENABLE_SMARTS, haveAnyExtensions(SMARTS));
                options.set(TypographicExtension.ENABLE_QUOTES, haveAnyExtensions(QUOTES));
            }

            if (!haveAnyExtensions(RELAXEDHRULES)) {
                options.set(Parser.THEMATIC_BREAK_RELAXED_START, false);
            }

            if (haveAnyExtensions(TABLES)) {
                extensions.add(TablesExtension.create());
                options.set(TablesExtension.TRIM_CELL_WHITESPACE, false);
                options.set(TablesExtension.HEADER_SEPARATOR_COLUMN_MATCH, false);
            }

            if (haveAnyExtensions(TASKLISTITEMS)) {
                extensions.add(TaskListExtension.create());
            }

            if (haveAnyExtensions(WIKILINKS)) {
                extensions.add(WikiLinkExtension.create());
                // pegdown does not have an option for selecting Creole or GitHub wiki link syntax: Creole puts page ref first, link text second, GitHub the other way around
                options.set(WikiLinkExtension.LINK_FIRST_SYNTAX, false);
                options.set(WikiLinkExtension.ALLOW_ANCHORS, true);
            }

            if (haveAnyExtensions(SUBSCRIPT) && haveAnyExtensions(STRIKETHROUGH)) {
                // first item is loose if second item is loose
                extensions.add(StrikethroughSubscriptExtension.create());
            } else if (haveAnyExtensions(STRIKETHROUGH)) {
                extensions.add(StrikethroughExtension.create());
            } else if (haveAnyExtensions(SUBSCRIPT)) {
                extensions.add(SubscriptExtension.create());
            }

            if (haveAnyExtensions(SUPERSCRIPT)) {
                extensions.add(SuperscriptExtension.create());
            }

            if (haveAnyExtensions(INSERTED)) {
                extensions.add(InsExtension.create());
            }

            if (haveAnyExtensions(TOC)) {
                extensions.add(SimTocExtension.create());
                options.set(TocExtension.BLANK_LINE_SPACER, true);

                extensions.add(TocExtension.create());
                options.set(TocExtension.LEVELS, TocOptions.getLevels(2, 3));
            }

            //// pegdown does not have emoji shortcuts
            //extensions.add(EmojiExtension.create());
            //// need to set the location of the emoji icons from emoji-cheat-sheet on github or http://www.emoji-cheat-sheet.com/
            ////options.set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory());

            if (haveAnyExtensions(MULTI_LINE_IMAGE_URLS)) {
                options.set(Parser.PARSE_MULTI_LINE_IMAGE_URLS, true);
            }

            if (haveAnyExtensions(FOOTNOTES)) {
                extensions.add(FootnoteExtension.create());
                options.set(FootnoteExtension.FOOTNOTES_KEEP, KeepType.LAST);
            }

            myOptions.set(Parser.EXTENSIONS, extensions);
        }

        return myOptions.toImmutable();
    }

    public PegdownOptionsAdapter setPegdownExtensions(int pegdownExtensions) {
        myPegdownExtensions = pegdownExtensions;
        myIsUpdateNeeded = true;
        return this;
    }

    public PegdownOptionsAdapter addPegdownExtensions(int pegdownExtensions) {
        myPegdownExtensions |= pegdownExtensions;
        myIsUpdateNeeded = true;
        return this;
    }

    public PegdownOptionsAdapter removePegdownExtensions(int pegdownExtensions) {
        myPegdownExtensions &= ~pegdownExtensions;
        myIsUpdateNeeded = true;
        return this;
    }
}
