package com.vladsch.flexmark.test.util.spec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class TemplateReader {
    final public static String ENTRY_START = "```````````````````````````````` template";
    final public static String ENTRY_BREAK = "````````````````````````````````";

    protected final InputStream inputStream;

    protected State state = State.BEFORE;
    protected StringBuilder source;
    protected int entryNumber = 0;

    protected List<TemplateEntry> examples = new ArrayList<>();

    protected TemplateReader(InputStream stream) {
        this.inputStream = stream;
    }

    public static List<TemplateEntry> readEntries() {
        return readEntries(null, null);
    }

    public static List<TemplateEntry> readEntries(String resource) {
        return readEntries(resource, null);
    }

    public static List<TemplateEntry> readEntries(String resource, TemplateReaderFactory readerFactory) {
        try {
            TemplateReader reader;
            InputStream stream = getSpecInputStream(resource);
            if (readerFactory == null) reader = new TemplateReader(stream);
            else reader = readerFactory.create(stream);
            return reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static List<String> readExamplesAsString() {
        return readExamplesAsString(null, null);
    }

    public static List<String> readExamplesAsString(String resource) {
        return readExamplesAsString(resource, null);
    }

    public static List<String> readExamplesAsString(String resource, TemplateReaderFactory readerFactory) {
        List<TemplateEntry> examples = TemplateReader.readEntries(resource, readerFactory);
        List<String> result = new ArrayList<>();
        for (TemplateEntry example : examples) {
            result.add(example.getSource());
        }
        return result;
    }

    public static String readSpec() {
        return readSpec(null);
    }

    public static String readSpec(String resource) {
        StringBuilder sb = new StringBuilder();
        try {
            String line;
            BufferedReader reader = new BufferedReader(new InputStreamReader(getSpecInputStream(resource), Charset.forName("UTF-8")));
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }
            return sb.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static InputStream getSpecInputStream() {
        return getSpecInputStream(null);
    }

    public static InputStream getSpecInputStream(String resource) {
        String specPath = resource != null ? resource : "/template.txt";
        InputStream stream = TemplateReader.class.getResourceAsStream(specPath);
        if (stream == null) {
            throw new IllegalStateException("Could not load " + resource + " classpath resource");
        }

        return stream;
    }

    protected List<TemplateEntry> read() throws IOException {
        resetContents();

        String line;
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
        while ((line = reader.readLine()) != null) {
            processLine(line);
        }

        return examples;
    }

    // can use these to generate spec from source
    protected void addSpecLine(String line) {

    }

    protected void addTemplateEntry(TemplateEntry example) {
        examples.add(example);
    }

    protected void processLine(String line) {
        boolean lineAbsorbed = false;

        switch (state) {
            case BEFORE:
                if (line.endsWith(ENTRY_START)) {
                    state = State.SOURCE;
                    entryNumber++;
                    lineAbsorbed = true;
                }
                break;
            case SOURCE:
                if (line.endsWith(ENTRY_BREAK)) {
                    state = State.BEFORE;
                    addTemplateEntry(new TemplateEntry(entryNumber, source.toString()));
                    resetContents();
                    lineAbsorbed = true;
                } else {
                    // examples use "rightwards arrow" to show tab
                    source.append(line).append('\n');
                    lineAbsorbed = true;
                }
                break;
        }

        if (!lineAbsorbed) {
            addSpecLine(line);
        }
    }

    protected void resetContents() {
        source = new StringBuilder();
    }

    protected enum State {
        BEFORE, SOURCE
    }
}
