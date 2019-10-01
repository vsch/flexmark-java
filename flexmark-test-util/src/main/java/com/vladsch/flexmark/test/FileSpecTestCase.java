package com.vladsch.flexmark.test;

import com.vladsch.flexmark.spec.SpecExample;
import com.vladsch.flexmark.spec.SpecReader;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@RunWith(Parameterized.class)
public abstract class FileSpecTestCase extends RenderingTestCase {
    protected final SpecExample example;

    public FileSpecTestCase(SpecExample example) {
        this.example = example;
    }

    public SpecExample example() {
        return example;
    }

    protected abstract String dumpDir();
    protected abstract String dumpHeader();

    protected void specExample(String expected, String actual, String optionSet) {
        if (dumpDir() != null) {
            File file = new File(dumpDir() + example.getSection() + "_ast_spec.md");
            FileOutputStream outputStream = null;
            try {
                outputStream = new FileOutputStream(file);
                outputStream.write(dumpHeader().getBytes(Charset.defaultCharset()));
                outputStream.write(actual.getBytes(Charset.defaultCharset()));
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Parameterized.Parameters(name = "{0}")
    public static List<Object[]> data() {
        return dataFromSeparateFiles(new String[] { }, "", false, false);
    }

    public static List<Object[]> dataFromSeparateFiles(String[] fileNames, String prefix, boolean dumpHtml, boolean dumpAst) {
        ArrayList<SpecExample> examples = new ArrayList<>();

        for (String name : fileNames) {
            String source = readResource(prefix + name + ".md");
            String html = null;
            String ast = null;
            try {
                html = readResource(name + ".html");
            } catch (RuntimeException ignored) {
                html = dumpHtml ? "" : null;
            }

            try {
                ast = readResource(name + ".ast");
            } catch (RuntimeException ignored) {
                ast = dumpAst ? "" : null;
            }

            if (ast != null) {
                examples.add(new SpecExample("", name, 0, source, html, ast));
            } else {
                examples.add(new SpecExample("", name, 0, source, html));
            }
        }

        List<Object[]> data = new ArrayList<>();

        // NULL example runs full spec test
        data.add(new Object[] { SpecExample.getNull() });

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    public static List<Object[]> dataFromFiles(String[] fileNames, String prefix) {
        ArrayList<SpecExample> examples = new ArrayList<>();

        for (String name : fileNames) {
            List<SpecExample> fileExamples = SpecReader.readExamples(prefix + name + "_ast_spec.md");
            int i = 1;
            for (SpecExample example : fileExamples) {
                examples.add(example.withSection(name).withExampleNumber(i++));
            }
        }

        List<Object[]> data = new ArrayList<>();

        for (SpecExample example : examples) {
            data.add(new Object[] { example });
        }
        return data;
    }

    protected static String readResource(String resourcePath) {
        InputStream stream = FileSpecTestCase.class.getResourceAsStream(resourcePath);
        return readStream(stream);
    }

    protected static String readStream(InputStream stream) {
        try {
            String line;
            StringBuilder out = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-8")));
            while ((line = reader.readLine()) != null) {
                out.append(line).append('\n');
            }

            return out.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void testHtmlRendering() {
        if (!example.isSpecExample()) return;

        if (example.getAst() != null) {
            assertRenderingAst(example.getFileUrl(), example.getSource(), example.getHtml(), example.getAst(), example.getOptionsSet());
        } else {
            assertRendering(example.getFileUrl(), example.getSource(), example.getHtml(), example.getOptionsSet());
        }
    }
}
