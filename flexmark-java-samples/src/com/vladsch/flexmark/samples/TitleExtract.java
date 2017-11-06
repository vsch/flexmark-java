package com.vladsch.flexmark.samples;

import com.vladsch.flexmark.ast.Block;
import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.ast.util.TextCollectingVisitor;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.profiles.pegdown.Extensions;
import com.vladsch.flexmark.profiles.pegdown.PegdownOptionsAdapter;
import com.vladsch.flexmark.util.options.DataHolder;

public class TitleExtract {
    static final DataHolder OPTIONS = PegdownOptionsAdapter.flexmarkOptions(
            Extensions.ALL & ~(Extensions.HARDWRAPS)
    ).toMutable()
            .set(HtmlRenderer.INDENT_SIZE, 2)
            ;

    private static String findTitle(Node root) {
        if (root instanceof Heading) {
            Heading h = (Heading)root;
            if (h.getLevel() == 1 && h.hasChildren()) {
                TextCollectingVisitor collectingVisitor = new TextCollectingVisitor();
                return collectingVisitor.collectAndGetText(h);
            }
        }

        if (root instanceof Block && root.hasChildren()) {
            Node child = root.getFirstChild();
            while (child != null) {
                String title = findTitle(child);
                if (title != null) {
                    return title;
                }
                child = child.getNext();
            }
        }

        return null;
    }

    public static void main(String[] args) {
        Parser parser = Parser.builder(OPTIONS).build();
        HtmlRenderer renderer = HtmlRenderer.builder(OPTIONS).build();

        // You can re-use parser and renderer instances
        Node document = parser.parse("\n" +
                "# Plugin commit-message-length-validator\n" +
                "\n" +
                "Vendor\n" +
                ":  Gerrit Code Review\n" +
                "\n" +
                "Version\n" +
                ":  v2.15-rc2\n" +
                "\n" +
                "commitmessage.maxSubjectLength\n" +
                ":       Maximum length of the commit message's subject line.  Defaults\n" +
                "        to 50 if not specified or less than 0.\n" +
                "\n" +
                "## About\n" +
                "\n" +
                "This plugin checks the length of a commit's commit message subject and body, and reports warnings and errors to the git client if the lentghts are exceeded.\n" +
                "\n" +
                "## Documentation\n" +
                "\n" +
                "* [Commit Message Length Configuration](config.md)\n" +
                "* More Items\n" +
                "\n" +
                "");

        String title = findTitle(document);
        System.out.println("Title: " + title + "\n");
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

        System.out.println(html);
    }
}
