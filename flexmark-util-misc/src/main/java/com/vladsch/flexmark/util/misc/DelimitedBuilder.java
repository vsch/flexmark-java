/*
 * Copyright (c) 2016-2018 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */

package com.vladsch.flexmark.util.misc;

import java.util.List;
import java.util.Stack;

public class DelimitedBuilder {
    private String delimiter;
    private StringBuilder out;
    private boolean pending = false;
    private int lastLen = 0;
    private Stack<String> delimiterStack = null;

    public DelimitedBuilder() {
        this(",", 0);
    }

    public DelimitedBuilder(String delimiter) {
        this(delimiter, 0);
    }

    public DelimitedBuilder(String delimiter, int capacity) {
        this.delimiter = delimiter;
        this.out = capacity == 0 ? null : new StringBuilder(capacity);
    }

    @Override
    public String toString() {
        if (delimiterStack != null && !delimiterStack.isEmpty()) throw new IllegalStateException("Delimiter stack is not empty");
        return out == null ? "" : out.toString();
    }

    public boolean isEmpty() {
        return !pending && (out == null || out.length() == 0);
    }

    public String getAndClear() {
        if (delimiterStack != null && !delimiterStack.isEmpty()) throw new IllegalStateException("Delimiter stack is not empty");
        String result = out == null ? "" : out.toString();
        clear();
        return result;
    }

    public DelimitedBuilder clear() {
        out = null;
        unmark();
        return this;
    }

    public String toStringOrNull() {
        if (delimiterStack != null && !delimiterStack.isEmpty()) throw new IllegalStateException("Delimiter stack is not empty");
        return out == null ? null : out.toString();
    }

    public DelimitedBuilder mark() {
        int length = out != null ? out.length() : 0;
        if (lastLen != length) pending = true;
        lastLen = length;
        return this;
    }

    public DelimitedBuilder unmark() {
        pending = false;
        lastLen = out != null ? out.length() : 0;
        return this;
    }

    public DelimitedBuilder push() {
        return push(delimiter);
    }

    public DelimitedBuilder push(String delimiter) {
        unmark();
        if (delimiterStack == null) delimiterStack = new Stack<>();
        delimiterStack.push(this.delimiter);
        this.delimiter = delimiter;
        return this;
    }

    public DelimitedBuilder pop() {
        if (delimiterStack == null || delimiterStack.isEmpty()) throw new IllegalStateException("Nothing on the delimiter stack");
        delimiter = delimiterStack.pop();
        return this;
    }

    private void doPending() {
        if (out == null) out = new StringBuilder();

        if (pending) {
            out.append(delimiter);
            pending = false;
        }
    }

    public DelimitedBuilder append(char v) {
        doPending();
        out.append(v);
        return this;
    }

    public DelimitedBuilder append(int v) {
        doPending();
        out.append(v);
        return this;
    }

    public DelimitedBuilder append(boolean v) {
        doPending();
        out.append(v);
        return this;
    }

    public DelimitedBuilder append(long v) {
        doPending();
        out.append(v);
        return this;
    }

    public DelimitedBuilder append(float v) {
        doPending();
        out.append(v);
        return this;
    }

    public DelimitedBuilder append(double v) {
        doPending();
        out.append(v);
        return this;
    }

    public DelimitedBuilder append(String v) {
        if (v != null && !v.isEmpty()) {
            doPending();
            out.append(v);
        }
        return this;
    }

    public DelimitedBuilder append(String v, int start, int end) {
        if (v != null && start < end) {
            doPending();
            out.append(v, start, end);
        }
        return this;
    }

    public DelimitedBuilder append(CharSequence v) {
        if (v != null && v.length() > 0) {
            doPending();
            out.append(v);
        }
        return this;
    }

    public DelimitedBuilder append(CharSequence v, int start, int end) {
        if (v != null && start < end) {
            doPending();
            out.append(v, start, end);
        }
        return this;
    }

    public DelimitedBuilder append(char[] v) {
        if (v.length > 0) {
            doPending();
            out.append(v);
        }
        return this;
    }

    public DelimitedBuilder append(char[] v, int start, int end) {
        if (start < end) {
            doPending();
            out.append(v, start, end);
        }
        return this;
    }

    public DelimitedBuilder append(Object o) {
        return append(o.toString());
    }

    public DelimitedBuilder appendCodePoint(int codePoint) {
        doPending();
        out.appendCodePoint(codePoint);
        return this;
    }

    public <V> DelimitedBuilder appendAll(V[] v) {
        return appendAll(v, 0, v.length);
    }

    public <V> DelimitedBuilder appendAll(V[] v, int start, int end) {
        for (int i = start; i < end; i++) {
            V item = v[i];
            append(item.toString());
            mark();
        }
        return this;
    }

    public <V> DelimitedBuilder appendAll(String delimiter, V[] v) {
        return appendAll(delimiter, v, 0, v.length);
    }

    public <V> DelimitedBuilder appendAll(String delimiter, V[] v, int start, int end) {
        int lastLength = out != null ? out.length() : 0;
        push(delimiter);
        appendAll(v, start, end);
        pop();

        if (lastLength != (out != null ? out.length() : 0)) mark();
        else unmark();

        return this;
    }

    public <V> DelimitedBuilder appendAll(List<? extends V> v) {
        return appendAll(v, 0, v.size());
    }

    public <V> DelimitedBuilder appendAll(List<? extends V> v, int start, int end) {
        for (int i = start; i < end; i++) {
            V item = v.get(i);
            append(item.toString());
            mark();
        }
        return this;
    }

    public <V> DelimitedBuilder appendAll(String delimiter, List<? extends V> v) {
        return appendAll(delimiter, v, 0, v.size());
    }

    public <V> DelimitedBuilder appendAll(String delimiter, List<? extends V> v, int start, int end) {
        int lastLength = out != null ? out.length() : 0;
        push(delimiter);
        appendAll(v, start, end);
        pop();

        if (lastLength != (out != null ? out.length() : 0)) mark();
        else unmark();

        return this;
    }
}
