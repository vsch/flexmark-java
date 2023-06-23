package com.vladsch.flexmark.ext.attributes;

import com.vladsch.flexmark.ast.Heading;
import com.vladsch.flexmark.ast.Text;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.junit.Assert.*;

public class HtmlRendererHeadingIdsTest {

    @Test
    public void parserShouldReadHeadingIdsFromMarkdown() throws IOException {
        String title = "Heading with ID";
        String id = "heading-id-1";
        String markdownSource = "# " + title + " {#" + id + "}";
        Node parsed = parse(markdownSource);

        assertTrue(parsed.hasChildren());
        Node heading = parsed.getFirstChild();
        assertEquals(Heading.class, heading.getClass());
        assertEquals(id, ((Heading) heading).getAnchorRefId());
        assertTrue(((Heading) heading).isExplicitAnchorRefId());
        assertTrue(heading.hasChildren());

        Node headingText = heading.getFirstChild();
        assertEquals(Text.class, headingText.getClass());
        assertEquals(title, headingText.getChars().toString());

        Node headingAttributes = headingText.getNext();
        assertEquals(AttributesNode.class, headingAttributes.getClass());
        assertEquals("#" + id, ((AttributesNode) headingAttributes).getText().toString());
        assertTrue(headingAttributes.hasChildren());

        Node headingId = headingAttributes.getFirstChild();
        assertEquals(AttributeNode.class, headingId.getClass());
        assertEquals(id, ((AttributeNode) headingId).getValue().toString());
        assertFalse(headingId.hasChildren());
    }

    @Test
    public void headingWithoutIdShouldNotGetGeneratedIdInHtml() {
        String markdownSource = "# Heading without ID";
        Node parsed = parse(markdownSource);
        String rendered = createRendererNotGeneratingButRenderingIds().render(parsed);
        assertEquals("<h1>Heading without ID</h1>\n", rendered);
    }
    @Test
    public void headingWithExplicitlySetIdShouldGetThatIdInHtml() {
        String markdownSource = "# Heading with ID {#heading-id-1}";
        Node parsed = parse(markdownSource);
        String rendered = createRendererNotGeneratingButRenderingIds().render(parsed);
        assertEquals("<h1 id=\"heading-id-1\">Heading with ID</h1>\n", rendered);
    }

    private static HtmlRenderer createRendererNotGeneratingButRenderingIds() {
        return HtmlRenderer.builder(getSettings()).build();
    }

    private static Node parse(String source) {
        return Parser.builder(getSettings()).build().parse(source);
    }

    private static MutableDataSet getSettings() {
        MutableDataSet options = new MutableDataSet();
        options.set(Parser.BLANK_LINES_IN_AST, false);
        options.set(HtmlRenderer.RENDER_HEADER_ID, true);
        options.set(HtmlRenderer.GENERATE_HEADER_ID, false);
        options.set(Parser.EXTENSIONS, Arrays.asList(
                AttributesExtension.create()));
        return options;
    }

}
