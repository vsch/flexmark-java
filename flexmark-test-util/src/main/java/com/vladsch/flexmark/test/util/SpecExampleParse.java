package com.vladsch.flexmark.test.util;

import com.vladsch.flexmark.util.data.DataHolder;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.BasedSequenceImpl;

import static com.vladsch.flexmark.util.Utils.suffixWith;

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
        if (TestUtils.NO_FILE_EOL.getFrom(myOptions)) {
            mySource = TestUtils.trimTrailingEOL(source);
        }

        String sourcePrefix = TestUtils.SOURCE_PREFIX.getFrom(myExampleOptions);
        String sourceSuffix = TestUtils.SOURCE_SUFFIX.getFrom(myExampleOptions);
        String sourceIndent = TestUtils.SOURCE_INDENT.getFrom(myExampleOptions);

        BasedSequence input;

        if (!sourcePrefix.isEmpty() || !sourceSuffix.isEmpty()) {
            String combinedSource = sourcePrefix + suffixWith(mySource, "\n") + sourceSuffix;
            input = BasedSequenceImpl.of(combinedSource).subSequence(sourcePrefix.length(), combinedSource.length() - sourceSuffix.length());
        } else {
            input = BasedSequenceImpl.of(mySource);
        }

        input = TestUtils.stripIndent(input, sourceIndent);

        String includedText = TestUtils.INCLUDED_DOCUMENT.getFrom(myExampleOptions);

        myRenderer.includeDocument(includedText);

        myTimed = TestUtils.TIMED.getFrom(myExampleOptions);
        myIterations = myTimed ? TestUtils.TIMED_ITERATIONS.getFrom(myExampleOptions) : 1;

        myStartTime = System.nanoTime();

        myRenderer.parse(input);
        for (int i = 1; i < myIterations; i++) myRenderer.parse(input);
        myParseTime = System.nanoTime();

        myRenderer.finalizeDocument();
        return mySource;
    }

    public void finalizeRender() {
        myRenderer.finalizeRender();
    }
}
