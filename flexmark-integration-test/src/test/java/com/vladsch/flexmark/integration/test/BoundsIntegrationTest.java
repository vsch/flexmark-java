package com.vladsch.flexmark.integration.test;

import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.test.specs.TestSpecLocator;
import com.vladsch.flexmark.test.util.spec.SpecExample;
import com.vladsch.flexmark.test.util.spec.SpecReader;
import com.vladsch.flexmark.util.ast.Node;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Tests various substrings of the spec examples to check for out of bounds exceptions.
 */
@RunWith(Parameterized.class)
public class BoundsIntegrationTest {

    final private static Parser PARSER = Parser.builder().build();

    protected final String input;

    public BoundsIntegrationTest(String input) {
        this.input = input;
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        SpecReader specReader = SpecReader.createAndReadExamples(TestSpecLocator.DEFAULT_RESOURCE_LOCATION, false);
        List<SpecExample> examples = specReader.getExamples();
        List<Object[]> data = new ArrayList<>();
        for (SpecExample example : examples) {
            data.add(new Object[] { example.getSource() });
        }
        return data;
    }

    @Test
    public void testSubstrings() {
        // Check possibly truncated block/inline starts
        for (int i = 1; i < input.length() - 1; i++) {
            parse(input.substring(i));
        }
        // Check possibly truncated block/inline ends
        for (int i = input.length() - 1; i > 1; i--) {
            parse(input.substring(0, i));
        }
    }

    private void parse(String input) {
        try {
            Node parsed = PARSER.parse(input);
            // Parsing should always return a node
            assertNotNull(parsed);
        } catch (Exception e) {
            throw new AssertionError("Parsing failed, input: " + input, e);
        }
    }
}
