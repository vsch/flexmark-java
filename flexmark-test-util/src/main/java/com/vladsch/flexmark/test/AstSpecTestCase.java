package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.AstSpecExample;
import com.vladsch.flexmark.spec.AstSpecReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public abstract class AstSpecTestCase extends RenderingTestCase {
    protected final AstSpecExample example;

    public AstSpecTestCase(AstSpecExample example) {
        this.example = example;
    }

    @Parameters(name = "{0}")
    public static List<Object[]> data() {
        List<AstSpecExample> examples = AstSpecReader.readExamples();
        List<Object[]> data = new ArrayList<>();
        for (AstSpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    @Test
    public void testHtmlRendering() {
        if (!example.getHtml().isEmpty()) {
            assertRendering(example.getSource(), example.getHtml());
        }
    }

    @Test
    public void testAstRendering() {
        if (!example.getAst().isEmpty()) {
            assertRendering(example.getSource(), example.getAst());
        }
    }
}
