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
import com.vladsch.flexmark.ext.gfm.tasklist.TaskListExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.toc.internal.TocOptions;
import com.vladsch.flexmark.ext.typographic.TypographicExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.ListOptions;
import com.vladsch.flexmark.parser.MutableListOptions;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationFamily;
import com.vladsch.flexmark.util.KeepType;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.util.ArrayList;

import static com.vladsch.flexmark.profiles.pegdown.Extensions.*;

public class PegdownOptionsAdapter {
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

    public static DataHolder flexmarkOptions(int pegdownExtensions) {
        PegdownOptionsAdapter optionsAdapter = new PegdownOptionsAdapter();
        return optionsAdapter.getFlexmarkOptions();
    }

    public boolean haveExtensions(int mask) {
        return (myPegdownExtensions & mask) != 0;
    }

    public boolean allExtensions(int mask) {
        return (myPegdownExtensions & mask) == mask;
    }

    public DataHolder getFlexmarkOptions() {
        if (myIsUpdateNeeded) {
            myIsUpdateNeeded = false;
            MutableDataSet options = myOptions;
            ArrayList<Extension> extensions = new ArrayList<>();

            //DUMMY_REFERENCE_KEY | MULTI_LINE_IMAGE_URLS | INTELLIJ_DUMMY_IDENTIFIER | RELAXED_STRONG_EMPHASIS_RULES
            options.clear();

            // Setup List Options for Fixed List Indent profile
            options.setFrom(ParserEmulationFamily.FIXED_INDENT.getOptions());

            //options.set(Parser.PARSE_INLINE_ANCHOR_LINKS, true);
            options.set(Parser.PARSE_INNER_HTML_COMMENTS, true);
            options.set(Parser.INDENTED_CODE_NO_TRAILING_BLANK_LINES, true);

            //options.set(Parser.PARSE_GITHUB_ISSUE_MARKER, true);
            options.set(HtmlRenderer.SUPPRESS_HTML_BLOCKS, haveExtensions(SUPPRESS_HTML_BLOCKS));
            options.set(HtmlRenderer.SUPPRESS_INLINE_HTML, haveExtensions(SUPPRESS_INLINE_HTML));

            // add default extensions in pegdown
            extensions.add(EscapedCharacterExtension.create());

            // Setup Block Quote Options
            options.set(Parser.BLOCK_QUOTE_TO_BLANK_LINE, true);
            options.set(Parser.BLOCK_QUOTE_IGNORE_BLANK_LINE, true);

            // setup list options: Fixed, CommonMark or GitHub, with GitHub docs and GitHub comments
            options.set(Parser.LISTS_AUTO_LOOSE, false);
            options.set(Parser.LISTS_AUTO_LOOSE, false);
            options.set(Parser.LISTS_DELIMITER_MISMATCH_TO_NEW_LIST, false);
            options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_NEW_LIST, false);
            options.set(Parser.LISTS_ITEM_TYPE_MISMATCH_TO_SUB_LIST, false);
            options.set(Parser.LISTS_END_ON_DOUBLE_BLANK, false);
            options.set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_PARAGRAPH, false);
            options.set(Parser.LISTS_BULLET_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true);
            options.set(Parser.LISTS_ORDERED_ITEM_DOT_ONLY, true);
            options.set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_PARAGRAPH, false);
            options.set(Parser.LISTS_ORDERED_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true);
            options.set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_PARAGRAPH, false);
            options.set(Parser.LISTS_ORDERED_NON_ONE_ITEM_INTERRUPTS_ITEM_PARAGRAPH, true);
            options.set(Parser.LISTS_ORDERED_LIST_MANUAL_START, false);

            // set which types of items can interrupt (ie. don't need a blank line) which types of paragraphs
            MutableListOptions listOptions = new MutableListOptions(options);
            ((ListOptions.MutableItemInterrupt) listOptions.getItemInterrupt())
                    .setBulletItemInterruptsParagraph(false)
                    .setOrderedItemInterruptsParagraph(false)
                    .setOrderedNonOneItemInterruptsParagraph(false)
                    .setEmptyBulletItemInterruptsParagraph(false)
                    .setEmptyOrderedItemInterruptsParagraph(false)
                    .setEmptyOrderedNonOneItemInterruptsParagraph(false)

                    .setBulletItemInterruptsItemParagraph(true)
                    .setOrderedItemInterruptsItemParagraph(true)
                    .setOrderedNonOneItemInterruptsItemParagraph(true)
                    .setEmptyBulletItemInterruptsItemParagraph(true)
                    .setEmptyOrderedItemInterruptsItemParagraph(true)
                    .setEmptyOrderedNonOneItemInterruptsItemParagraph(true)
                    .setEmptyBulletSubItemInterruptsItemParagraph(true)
                    .setEmptyOrderedSubItemInterruptsItemParagraph(true)
                    .setEmptyOrderedNonOneSubItemInterruptsItemParagraph(true)
            ;

            listOptions
                    .setItemMarkerSpace(true)
            ;

            options.setFrom(listOptions);

            if (haveExtensions(MULTI_LINE_IMAGE_URLS)) {
                options.set(Parser.PARSE_MULTI_LINE_IMAGE_URLS, true);
            }

            if (haveExtensions(RELAXED_STRONG_EMPHASIS_RULES)) {
                // already using the only inline parsing available for this
            }

            if (haveExtensions(INTELLIJ_DUMMY_IDENTIFIER)) {
                options.set(Parser.INTELLIJ_DUMMY_IDENTIFIER, true);
            }

            if (haveExtensions(ABBREVIATIONS)) {
                extensions.add(AbbreviationExtension.create());
                options.set(AbbreviationExtension.ABBREVIATIONS_KEEP, KeepType.LAST);
            }

            if (haveExtensions(ANCHORLINKS | EXTANCHORLINKS | EXTANCHORLINKS_WRAP)) {
                extensions.add(AnchorLinkExtension.create());
                if (haveExtensions(EXTANCHORLINKS)) {
                    options.set(AnchorLinkExtension.ANCHORLINKS_WRAP_TEXT, haveExtensions(EXTANCHORLINKS_WRAP));
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

            if (!haveExtensions(FORCELISTITEMPARA)) {
                // first item is loose if second item is loose
            }

            if (haveExtensions(HARDWRAPS)) {
                options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");
                options.set(HtmlRenderer.HARD_BREAK, "<br />\n<br />\n");
            }

            if (!haveExtensions(ATXHEADERSPACE)) {
                options.set(Parser.HEADING_NO_ATX_SPACE, true);
            }
            options.set(Parser.HEADING_NO_LEAD_SPACE, true);

            // 3 for pegdown compatibility, 1 for commonmark, something else for GFM which will take 1 without trailing spaces if in a list, outside a list 1 or 2+ with spaces even if in a list
            options.set(Parser.HEADING_SETEXT_MARKER_LENGTH, 3);

            if (haveExtensions(QUOTES | SMARTS)) {
                // not implemented yet, have placeholder
                extensions.add(TypographicExtension.create());
                options.set(TypographicExtension.TYPOGRAPHIC_SMARTS, haveExtensions(SMARTS));
                options.set(TypographicExtension.TYPOGRAPHIC_QUOTES, haveExtensions(QUOTES));
            }

            if (!haveExtensions(RELAXEDHRULES)) {
                options.set(Parser.THEMATIC_BREAK_RELAXED_START, false);
            }

            if (haveExtensions(STRIKETHROUGH)) {
                extensions.add(StrikethroughExtension.create());
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

            if (haveExtensions(FOOTNOTES)) {
                extensions.add(FootnoteExtension.create());
                options.set(FootnoteExtension.FOOTNOTES_KEEP, KeepType.LAST);
            }

            // References compatibility
            options.set(Parser.REFERENCES_KEEP, KeepType.LAST);

            if (haveExtensions(TOC)) {
                extensions.add(SimTocExtension.create());
                options.set(SimTocExtension.BLANK_LINE_SPACER, true);

                extensions.add(TocExtension.create());
                options.set(TocExtension.LEVELS, TocOptions.getLevels(1, 2, 3));
            }

            // pegdown does not have emoji shortcuts
            if (false) {
                extensions.add(EmojiExtension.create());
                // need to set the location of the emoji icons from emoji-cheat-sheet on github or http://www.emoji-cheat-sheet.com/
                //options.set(EmojiExtension.ROOT_IMAGE_PATH, emojiInstallDirectory());

                // this will use GitHub URLs for emoji icons, not recommended
                options.set(EmojiExtension.USE_IMAGE_URLS, true);
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
