package com.vladsch.flexmark.test;

import static org.junit.Assert.assertEquals;

public abstract class AstRenderingTestCase extends RenderingTestCase {

    protected abstract String ast(String source);

    protected void assertAst(String source, String expectedAst) {
        String ast = ast(source);

        // include source for better assertion errors
        String expected = expectedAst + "\n\n" + source;
        String actual = ast + "\n\n" + source;
        assertEquals(expected, actual);
    }
}
