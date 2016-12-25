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

/**
 * CharSequence that is the reverse of the given sequence
 *
 * The hashCode is purposefully matched to the string equivalent or this.toString().hashCode()
 */
public class ReversedCharSequence implements CharSequence {
    private final CharSequence myChars;
    private final int myStartIndex;
    private final int myEndIndex;
    private int myHash;

    @SuppressWarnings("WeakerAccess")
    private ReversedCharSequence(CharSequence chars, int start, int end) {
        if (start < 0 || end > chars.length() || start > end)
            throw new IndexOutOfBoundsException("[" + start + "," + end + ") not in [0," + length() + ")");
        myChars = chars;
        myStartIndex = start;
        myEndIndex = end;
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
    public int reversedIndex(int index) {
        if (index < 0 || index > length()) throw new IndexOutOfBoundsException("" + index + " not in [0," + (length() - 1) + "]");
        return myEndIndex - 1 - index;
    }

    @Override
    public int length() {
        return myEndIndex - myStartIndex;
    }

    @Override
    public char charAt(int index) {
        if (index < 0 || index >= length()) throw new IndexOutOfBoundsException("" + index + " not in [0," + (length() - 1) + ")");
        return myChars.charAt(reversedIndex(index));
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        if (start < 0 || end > length())
            throw new IndexOutOfBoundsException("[" + start + ", " + end + ") not in [0," + (length() - 1) + ")");
        final int startIndex = reversedIndex(end) + 1;
        final int endIndex = startIndex + end - start;
        return startIndex == myStartIndex && endIndex == myEndIndex ? this : new ReversedCharSequence(myChars, startIndex, endIndex);
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
            for (int i = myEndIndex; i-- > myStartIndex; ) {
                h = 31 * h + myChars.charAt(i);
            }
            myHash = h;
        }
        return h;
    }

    public static CharSequence of(final CharSequence chars) {
        return of(chars, 0, chars.length());
    }

    public static CharSequence of(final CharSequence chars, final int start) {
        return of(chars, start, chars.length());
    }

    public static CharSequence of(final CharSequence chars, final int start, final int end) {
        if (chars instanceof ReversedCharSequence) {
            final ReversedCharSequence reversedChars = (ReversedCharSequence) chars;
            final int startIndex = reversedChars.reversedIndex(end) + 1;
            final int endIndex = startIndex + end - start;
            return startIndex == 0 && endIndex == chars.length() ? reversedChars.myChars : reversedChars.myChars.subSequence(startIndex, endIndex);
        } else return new ReversedCharSequence(chars, start, end);
    }
}
