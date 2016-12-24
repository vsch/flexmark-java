/*
 * Copyright (c) 2016-2016 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.vladsch.flexmark.util.sequence;

import com.vladsch.flexmark.util.mappers.IndexMapper;

public class ReversedCharSequence implements CharSequence {
    final private CharSequence myChars;
    final private int myStart;
    final private int myEnd;
    private int myHash;

    @SuppressWarnings("WeakerAccess")
    public ReversedCharSequence(CharSequence chars, int start, int end) {
        if (start < 0 || end > chars.length() || start > end) {
            throw new IndexOutOfBoundsException("[" + start + "," + end + ") not in [0," + length() + ")");
        }
        myChars = chars;
        myStart = start;
        myEnd = end;
    }

    public IndexMapper getIndexMapper() {
        return new IndexMapper() {
            @Override
            public int map(final int index) {
                return ReversedCharSequence.this.reversedIndex(index);
            }
        };
    }

    @SuppressWarnings("WeakerAccess")
    public ReversedCharSequence(CharSequence chars) {
        this(chars, 0, chars.length());
    }

    @SuppressWarnings("WeakerAccess")
    public ReversedCharSequence(CharSequence chars, int start) {
        this(chars, start, chars.length());
    }

    @SuppressWarnings("WeakerAccess")
    protected int reversedIndex(int index) {
        if (index < 0 || index > length()) {
            throw new IndexOutOfBoundsException("" + index + " not in [0," + (length() - 1) + "]");
        }
        return myEnd - 1 - index;
    }

    @Override
    public int length() {
        return myEnd - myStart;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= length()) {
            throw new IndexOutOfBoundsException("" + index + " not in [0," + (length() - 1) + ")");
        }
        return myChars.charAt(reversedIndex(index));
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return new ReversedCharSequence(myChars, reversedIndex(end) + 1, reversedIndex(end) + 1 + end - start);
    }

    @Override
    public String toString() {
        //noinspection StringBufferReplaceableByString
        StringBuilder sb = new StringBuilder(length());
        sb.append(this);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CharSequence)) return false;

        if (o instanceof String || o instanceof ReversedCharSequence) {
            return hashCode() == o.hashCode();
        }

        CharSequence os = (CharSequence) o;
        if (length() != os.length()) return false;
        int iMax = length();
        for (int i = 0; i < iMax; i++) {
            if (charAt(i) != os.charAt(i)) return false;
        }

        return true;
    }

    /**
     * Make it equal the same hash code as the string it represents reversed
     *
     * @return hash code
     */
    @Override
    public int hashCode() {
        int h = myHash;
        if (h == 0 && length() > 0) {
            for (int i = myEnd; i-- > myStart; ) {
                h = 31 * h + myChars.charAt(i);
            }
            myHash = h;
        }
        return h;
    }
}
