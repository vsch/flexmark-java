package com.vladsch.flexmark.ext.yaml.front.matter;

import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.util.FlexmarkSpecExampleRenderer;
import com.vladsch.flexmark.test.util.RenderingTestCase;
import com.vladsch.flexmark.test.util.SpecExampleRenderer;
import com.vladsch.flexmark.test.util.TestUtils;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.data.DataSet;
import com.vladsch.flexmark.util.data.MutableDataSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.Test;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class YamlFrontMatterTest extends RenderingTestCase {
    final private static DataHolder OPTIONS = new MutableDataSet()
            .set(TestUtils.NO_FILE_EOL, false)
            .set(Parser.EXTENSIONS, Collections.singleton(YamlFrontMatterExtension.create()))
            .toImmutable();
    final private static @NotNull Parser PARSER = Parser.builder(OPTIONS).build();
    final private static @NotNull HtmlRenderer RENDERER = HtmlRenderer.builder(OPTIONS).build();

    @Override
    public @NotNull SpecExampleRenderer getSpecExampleRenderer(@NotNull SpecExample example, @Nullable DataHolder exampleOptions) {
        DataHolder combinedOptions = DataSet.aggregate(OPTIONS, exampleOptions);
        return new FlexmarkSpecExampleRenderer(example, combinedOptions, PARSER, RENDERER, true);
    }

    @Test
    public void simpleValue() {
        final String input = "---" +
                "\nhello: world" +
                "\n..." +
                "\n" +
                "\ngreat";
        final String rendered = "<p>great</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertEquals(1, data.size());
        assertEquals("hello", data.keySet().iterator().next());
        assertEquals(1, data.get("hello").size());
        assertEquals("world", data.get("hello").get(0));

        assertRendering(input, rendered);
    }

    @Test
    public void emptyValue() {
        final String input = "---" +
                "\nkey:" +
                "\n---" +
                "\n" +
                "\ngreat";
        final String rendered = "<p>great</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertEquals(1, data.size());
        assertEquals("key", data.keySet().iterator().next());
        assertEquals(0, data.get("key").size());

        assertRendering(input, rendered);
    }

    @Test
    public void listValues() {
        final String input = "---" +
                "\nlist:" +
                "\n  - value1" +
                "\n  - value2" +
                "\n..." +
                "\n" +
                "\ngreat";
        final String rendered = "<p>great</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertEquals(1, data.size());
        assertTrue(data.containsKey("list"));
        assertEquals(2, data.get("list").size());
        assertEquals("value1", data.get("list").get(0));
        assertEquals("value2", data.get("list").get(1));

        assertRendering(input, rendered);
    }

    @Test
    public void literalValue1() {
        final String input = "---" +
                "\nliteral: |" +
                "\n  hello markdown!" +
                "\n  literal thing..." +
                "\n---" +
                "\n" +
                "\ngreat";
        final String rendered = "<p>great</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertEquals(1, data.size());
        assertTrue(data.containsKey("literal"));
        assertEquals(1, data.get("literal").size());
        assertEquals("hello markdown!\nliteral thing...", data.get("literal").get(0));

        assertRendering(input, rendered);
    }

    @Test
    public void literalValue2() {
        final String input = "---" +
                "\nliteral: |" +
                "\n  - hello markdown!" +
                "\n---" +
                "\n" +
                "\ngreat";
        final String rendered = "<p>great</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertEquals(1, data.size());
        assertTrue(data.containsKey("literal"));
        assertEquals(1, data.get("literal").size());
        assertEquals("- hello markdown!", data.get("literal").get(0));

        assertRendering(input, rendered);
    }

    @Test
    public void complexValues() {
        final String input = "---" +
                "\nsimple: value" +
                "\nliteral: |" +
                "\n  hello markdown!" +
                "\n" +
                "\n  literal literal" +
                "\nlist:" +
                "\n    - value1" +
                "\n    - value2" +
                "\n---" +
                "\ngreat";
        final String rendered = "<p>great</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertEquals(3, data.size());

        assertTrue(data.containsKey("simple"));
        assertEquals(1, data.get("simple").size());
        assertEquals("value", data.get("simple").get(0));

        assertTrue(data.containsKey("literal"));
        assertEquals(1, data.get("literal").size());
        assertEquals("hello markdown!\n\nliteral literal", data.get("literal").get(0));

        assertTrue(data.containsKey("list"));
        assertEquals(2, data.get("list").size());
        assertEquals("value1", data.get("list").get(0));
        assertEquals("value2", data.get("list").get(1));

        assertRendering(input, rendered);
    }

    @Test
    public void yamlInParagraph() {
        final String input = "# hello\n" +
                "\nhello markdown world!" +
                "\n---" +
                "\nhello: world" +
                "\n---";
        final String rendered = "<h1>hello</h1>\n<h2>hello markdown world!</h2>\n<h2>hello: world</h2>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertTrue(data.isEmpty());

        assertRendering(input, rendered);
    }

    @Test
    public void nonMatchedStartTag() {
        final String input = "----\n" +
                "test";
        final String rendered = "<hr />\n<p>test</p>\n";

        AbstractYamlFrontMatterVisitor visitor = new AbstractYamlFrontMatterVisitor();
        Node document = PARSER.parse(input);
        visitor.visit(document);

        Map<String, List<String>> data = visitor.getData();

        assertTrue(data.isEmpty());

        assertRendering(input, rendered);
    }

    @Override
    public @Nullable DataHolder options(@NotNull String option) {
        return null;
    }
}
