package com.vladsch.flexmark.spec;

import com.vladsch.flexmark.util.IRender;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.options.DataHolder;
import com.vladsch.flexmark.util.options.MutableDataSet;

import java.io.IOException;

public abstract class IRenderBase implements IRender {
    public static final IRender NullRenderer = new IRenderBase() {
        @Override
        public void render(Node node, Appendable output) {

        }

        @Override
        public IRender withOptions(DataHolder options) {
            return this;
        }
    };

    public static final IRender TextRenderer = new IRenderBase() {
        @Override
        public void render(Node node, Appendable output) {
            try {
                output.append(node.getChars());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        public IRender withOptions(DataHolder options) {
            return this;
        }
    };

    private final MutableDataSet myOptions;

    public IRenderBase() {
        this(null);
    }

    public IRenderBase(DataHolder options) {
        myOptions = options != null ? new MutableDataSet(options) : new MutableDataSet();
    }

    @Override
    public String render(Node node) {
        StringBuilder out = new StringBuilder();
        render(node, out);
        return out.toString();
    }

    public MutableDataSet getOptions() {
        return myOptions;
    }
}
