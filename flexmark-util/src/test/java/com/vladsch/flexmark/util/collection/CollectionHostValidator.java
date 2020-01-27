package com.vladsch.flexmark.util.collection;

import com.vladsch.flexmark.util.misc.Pair;
import com.vladsch.flexmark.util.misc.Paired;
import org.jetbrains.annotations.Nullable;
import org.junit.Assert;

import java.util.ArrayList;

public class CollectionHostValidator<T> {
    final private ArrayList<Paired<String, Object[]>> expectedCallBacks = new ArrayList<>();
    private int nextCallbackIndex;
    private int modificationCount;

    private boolean trace;

    private String id;

    private boolean conditional;
    private int repeat;

    public CollectionHostValidator() {
        reset();
        trace = false;
    }

    public CollectionHostValidator<T> start() {
        nextCallbackIndex = 0;
        modificationCount = 0;
        this.id = "";
        hadExpect();
        return this;
    }

    public CollectionHostValidator<T> repeat(int repeat) {
        this.repeat = repeat;
        return this;
    }

    public CollectionHostValidator<T> notrace() {
        return trace(false);
    }

    public CollectionHostValidator<T> trace() {
        return trace(true);
    }

    public CollectionHostValidator<T> trace(boolean trace) {
        this.trace = trace;
        return this;
    }

    public CollectionHostValidator<T> reset() {
        start();
        expectedCallBacks.clear();
        return this;
    }

    private void hadExpect() {
        conditional = true;
        repeat = 1;
    }

    public CollectionHostValidator<T> id(String id) {
        this.id = id;
        return this;
    }

    public CollectionHostValidator<T> id(int id) {
        this.id = String.valueOf(id);
        return this;
    }

    public CollectionHostValidator<T> id(Object id) {
        this.id = String.valueOf(id);
        return this;
    }

    public CollectionHostValidator<T> onCond(boolean conditional) {
        this.conditional = conditional;
        return this;
    }

    private void expect(String callBack, Object... params) {
        if (conditional) {
            int i = 0;
            while (i++ < repeat) {
                expectedCallBacks.add(new Pair<>(callBack, params));
            }
        }
        hadExpect();
    }

    public CollectionHostValidator<T> test(Runnable test) {
        if (trace) {
            System.out.println(expectations());
        }

        test.run();
        validate();
        start();
        return this;
    }

    private String hostName(String host) {
        return host.trim().isEmpty() ? "" : "" + host + ".";
    }

    private String id() {
        return id.trim().isEmpty() ? "" : "[" + id + "] ";
    }

    public CollectionHostValidator<T> expectAdding(int index, T s, Object v) {
        return expectAddingFrom("", index, s, v);
    }

    public CollectionHostValidator<T> expectAddingNull(int index) {
        return expectAddingNullFrom("", index);
    }

    public CollectionHostValidator<T> expectRemoving(int index, T s) {
        return expectRemovingFrom("", index, s);
    }

    public CollectionHostValidator<T> expectClearing() {
        return expectClearingFrom("");
    }

    public CollectionHostValidator<T> expectAddingFrom(String host, int index, T s, Object v) {
        expect(hostName(host) + "adding", index, s, v);
        return this;
    }

    public CollectionHostValidator<T> expectAddingNullFrom(String host, int index) {
        expect(hostName(host) + "addingNull", index);
        return this;
    }

    public CollectionHostValidator<T> expectRemovingFrom(String host, int index, T s) {
        expect(hostName(host) + "removing", index, s);
        return this;
    }

    public CollectionHostValidator<T> expectClearingFrom(String host) {
        expect(hostName(host) + "clearing");
        return this;
    }

    public String expectations() {
        StringBuilder out = new StringBuilder();

        out.append("\n").append(id()).append("Expected callbacks").append(":\n");
        for (int i = 0; i < expectedCallBacks.size(); i++) {
            Paired<String, Object[]> pair = expectedCallBacks.get(i);
            String expected = prepareMessage(pair.getFirst(), pair.getSecond());
            out.append("    [").append(i).append("]:").append(expected).append("\n");
        }
        return out.toString();
    }

    public CollectionHostValidator<T> validate() {
        if (nextCallbackIndex < expectedCallBacks.size()) {
            StringBuilder out = new StringBuilder();

            out.append("\n").append(id()).append("Missing callbacks").append(":\n");
            for (int i = nextCallbackIndex; i < expectedCallBacks.size(); i++) {
                Paired<String, Object[]> pair = expectedCallBacks.get(i);
                String expected = prepareMessage(pair.getFirst(), pair.getSecond());
                out.append("    [").append(i).append("]:").append(expected).append("\n");
            }

            Assert.fail(out.toString());
        }
        start();
        return this;
    }

    public CollectionHost<T> getHost() {
        return getHost("");
    }

    public CollectionHost<T> getHost(String host) {
        return new CollectionHost<T>() {
            @Override
            public void adding(int index, @Nullable T s, @Nullable Object v) {
                validate(hostName(host) + "adding", index, s, v);
                modificationCount++;
            }

            @Override
            public Object removing(int index, @Nullable T s) {
                validate(hostName(host) + "removing", index, s);
                modificationCount++;
                return null;
            }

            @Override
            public void clearing() {
                validate(hostName(host) + "clearing");
                modificationCount++;
            }

            @Override
            public void addingNulls(int index) {
                validate(hostName(host) + "addingNull", index);
                modificationCount++;
            }

            @Override
            public boolean skipHostUpdate() {
                return false;
            }

            @Override
            public int getIteratorModificationCount() {
                return modificationCount;
            }
        };
    }

    private String prepareMessage(String callBack, Object... params) {
        StringBuilder out = new StringBuilder();
        out.append(' ').append(callBack).append('(');
        boolean first = true;
        for (Object param : params) {
            if (first) first = false;
            else out.append(", ");
            if (param == null) out.append("null");
            else
                out.append(param.getClass().getName().substring(param.getClass().getPackage().getName().length() + 1)).append(' ').append(param);
        }
        out.append(')');
        return out.toString();
    }

    private void validate(String callBack, Object... params) {
        String actual = prepareMessage(callBack, params);
        int index = nextCallbackIndex;

        if (trace) {
            System.out.println(id() + "actual callback[" + index + "] " + actual);
        }

        if (nextCallbackIndex >= expectedCallBacks.size()) {
            throw new IllegalStateException(id() + "un-expected callback[" + nextCallbackIndex++ + "]" + actual);
        }

        Paired<String, Object[]> pair = expectedCallBacks.get(nextCallbackIndex++);
        String expected = prepareMessage(pair.getFirst(), pair.getSecond());

        Assert.assertEquals(id() + "callback[" + index + "] mismatch", expected, actual);
    }
}
