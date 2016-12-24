/*
 * Copyright (c) 2016-2016 Vladimir Schneider <vladimir.schneider@gmail.com>
 *
 * private  final nsed to the Apache Software Foundation (ASF) under one
 * or private  final ore contributor license agreements.  See the NOTICE file
 * private  final ributed with this work for additional information
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

public class RepeatedCharSequence implements CharSequence {
    private final CharSequence myChars;
    private final int myStartIndex;
    private final int myEndIndex;

    private RepeatedCharSequence(final CharSequence chars, int startIndex, int endIndex) {
        myChars = chars;
        myStartIndex = startIndex;
        myEndIndex = endIndex;
    }

    @Override
    public int length() {
        return myEndIndex - myStartIndex;
    }

    @Override
    public char charAt(final int index) {
        if (index >= 0 && index < length()) return myChars.charAt((myStartIndex + index) % myChars.length());
        throw new IndexOutOfBoundsException();
    }

    @Override
    public CharSequence subSequence(final int start, final int end) {
        if (start >= 0 && start <= end && end <= length()) {
            return (start == end) ? BasedSequence.NULL : new RepeatedCharSequence(myChars, start, end - start);
        }

        throw new IllegalArgumentException("subSequence($startIndex, $endIndex) in RepeatedCharSequence('', " + myStartIndex + ", " + myEndIndex + ")");
    }

    public RepeatedCharSequence repeat(int count) {
        return new RepeatedCharSequence(myChars, myStartIndex, (myEndIndex - myStartIndex) * count);
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj == this || (obj instanceof CharSequence && toString().equals(obj.toString()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this, myStartIndex, myEndIndex);
        return sb.toString();
    }

    public static RepeatedCharSequence of(final CharSequence chars) {
        return new RepeatedCharSequence(chars, 0, chars.length());
    }

    public static RepeatedCharSequence of(final CharSequence chars, final int count) {
        return new RepeatedCharSequence(chars, 0, chars.length() * count);
    }

    public static RepeatedCharSequence of(final CharSequence chars, final int start, final int end) {
        return new RepeatedCharSequence(chars, start, end);
    }
}
