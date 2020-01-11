package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;

import static com.vladsch.flexmark.util.misc.Utils.suffixWith;

public class SpecExampleParse {
    private DataHolder myOptions;
    private SpecExampleRenderer myRenderer;
    private DataHolder myExampleOptions;
    private String mySource;
    private boolean myTimed;
    private int myIterations;
    private long myStartTime;
    private long myParseTime;

    public SpecExampleParse(DataHolder options, SpecExampleRenderer exampleRenderer, DataHolder exampleOptions, String parseSource) {
        myOptions = options;
        myRenderer = exampleRenderer;
        myExampleOptions = exampleOptions;
        mySource = parseSource;
        parse(parseSource);
    }

    public DataHolder getOptions() {
        return myOptions;
    }

    public SpecExampleRenderer getRenderer() {
        return myRenderer;
    }

    public DataHolder getExampleOptions() {
        return myExampleOptions;
    }

    public String getSource() {
        return mySource;
    }

    public boolean isTimed() {
        return myTimed;
    }

    public int getIterations() {
        return myIterations;
    }

    public long getStartTime() {
        return myStartTime;
    }

    public long getParseTime() {
        return myParseTime;
    }

    public String parse(String source) {
        if (TestUtils.NO_FILE_EOL.get(myOptions)) {
            mySource = TestUtils.trimTrailingEOL(source);
        }

        String sourcePrefix = TestUtils.SOURCE_PREFIX.get(myExampleOptions);
        String sourceSuffix = TestUtils.SOURCE_SUFFIX.get(myExampleOptions);
        String sourceIndent = TestUtils.SOURCE_INDENT.get(myExampleOptions);

        BasedSequence input;

        if (!sourcePrefix.isEmpty() || !sourceSuffix.isEmpty()) {
            String combinedSource = sourcePrefix + suffixWith(mySource, "\n") + sourceSuffix;
            input = BasedSequence.of(combinedSource).subSequence(0, ((CharSequence) combinedSource).length()).subSequence(sourcePrefix.length(), combinedSource.length() - sourceSuffix.length());
        } else {
            input = BasedSequence.of(mySource);
        }

        input = TestUtils.stripIndent(input, sourceIndent);

        String includedText = TestUtils.INCLUDED_DOCUMENT.get(myExampleOptions);

        myRenderer.includeDocument(includedText);

        myTimed = TestUtils.TIMED.get(myExampleOptions);
        myIterations = myTimed ? TestUtils.TIMED_ITERATIONS.get(myExampleOptions) : 1;

        myStartTime = System.nanoTime();

        myRenderer.parse(input.toString());
        for (int i = 1; i < myIterations; i++) myRenderer.parse(input);
        myParseTime = System.nanoTime();

        myRenderer.finalizeDocument();
        return mySource;
    }

    public void finalizeRender() {
        myRenderer.finalizeRender();
    }
}
