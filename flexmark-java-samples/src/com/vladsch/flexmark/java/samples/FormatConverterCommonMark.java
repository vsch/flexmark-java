package com.vladsch.flexmark.java.samples;

import com.vladsch.flexmark.ext.definition.DefinitionExtension;
import com.vladsch.flexmark.ext.emoji.EmojiExtension;
import com.vladsch.flexmark.ext.footnotes.FootnoteExtension;
import com.vladsch.flexmark.ext.gfm.strikethrough.StrikethroughSubscriptExtension;
import com.vladsch.flexmark.ext.ins.InsExtension;
import com.vladsch.flexmark.ext.superscript.SuperscriptExtension;
import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.ext.toc.SimTocExtension;
import com.vladsch.flexmark.ext.toc.TocExtension;
import com.vladsch.flexmark.ext.wikilink.WikiLinkExtension;
import com.vladsch.flexmark.formatter.Formatter;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.test.util.AstCollectingVisitor;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.MutableDataSet;

import java.util.Arrays;

public class FormatConverterCommonMark {
    final private static DataHolder OPTIONS = ParserEmulationProfile.FIXED_INDENT.getProfileOptions()
            .set(Parser.BLANK_LINES_IN_AST, true)
            .set(Parser.LISTS_ITEM_INDENT, 1)
            .set(Parser.EXTENSIONS, Arrays.asList(
                    DefinitionExtension.create(),
                    EmojiExtension.create(),
                    FootnoteExtension.create(),
                    StrikethroughSubscriptExtension.create(),
                    InsExtension.create(),
                    SuperscriptExtension.create(),
                    TablesExtension.create(),
                    TocExtension.create(),
                    SimTocExtension.create(),
                    WikiLinkExtension.create()
            ));
    ;

    final private static DataHolder FORMATTER_OPTIONS = new MutableDataSet(OPTIONS)
            .set(Parser.LISTS_ITEM_INDENT, 4);

    public static void main(String[] args) {
        String markdown = "Text\n" +
                "\n" +
                "1. numbered list one\n" +
                " - unnumbered list\n" +
                " unnumbered list cont. same line\n" +
                " - unnumbered list  \n" +
                " unnumbered list cont. next line\n" +
                "\n" +
                " numbered list one cont. after unnumbered list" +
                "";
        System.out.println("\nMarkdown: --------------------------------------------------------------------------------\n");
        System.out.println(markdown);
        System.out.println("\n--------------------------------------------------------------------------------\n");

        Parser PARSER = Parser.builder(OPTIONS).build();
        Formatter RENDERER = Formatter.builder(OPTIONS).build();
        Formatter RENDERER_FIXED4 = Formatter.builder(FORMATTER_OPTIONS).build();

        Node document = PARSER.parse(markdown);

        System.out.println(new AstCollectingVisitor().collectAndGetAstText(document));
        System.out.println("\n--------------------------------------------------------------------------------\n");

        String formatted = RENDERER.render(document);

        // or to control the package
        System.out.println("\nFormatted as is: --------------------------------------------------------------------------------\n");
        System.out.println(formatted);
        System.out.println("\n--------------------------------------------------------------------------------\n");

        String formattedFixed4 = RENDERER_FIXED4.render(document);

        // or to control the package
        System.out.println("\nFormatted fixed 4: --------------------------------------------------------------------------------\n");
        System.out.println(formattedFixed4);
        System.out.println("\n--------------------------------------------------------------------------------\n");
    }
}
