package com.vladsch.flexmark.spec;

import com.vladsch.flexmark.IParse;
import com.vladsch.flexmark.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;
import com.vladsch.flexmark.util.sequence.BasedSequence;
import com.vladsch.flexmark.util.sequence.CharSubSequence;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public abstract class IParseBase implements IParse {
    private final MutableDataSet myOptions;

    public IParseBase() {
        this(null);
    }

    public IParseBase(DataHolder options) {
        myOptions = options != null ? new MutableDataSet(options) : new MutableDataSet();
    }

    @Override
    public Node parse(String input) {
        return parse(CharSubSequence.of(input));
    }

    @Override
    public Node parseReader(Reader input) throws IOException {
        BufferedReader bufferedReader;
        if (input instanceof BufferedReader) {
            bufferedReader = (BufferedReader) input;
        } else {
            bufferedReader = new BufferedReader(input);
        }

        StringBuilder file = new StringBuilder();
        char[] buffer = new char[16384];

        while (true) {
            int charsRead = bufferedReader.read(buffer);
            file.append(buffer, 0, charsRead);
            if (charsRead < buffer.length) break;
        }

        BasedSequence source = CharSubSequence.of(file.toString());
        return parse(source);
    }

    public MutableDataSet getOptions() {
        return myOptions;
    }
}
