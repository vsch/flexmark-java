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

import org.junit.Test;

import static org.junit.Assert.assertEquals;

@SuppressWarnings("SpellCheckingInspection")
public class ReversedCharSequenceTest {
    @Test
    public void test_basic() throws Exception {
        CharSequence orig = "abcdef";
        CharSequence reved = "fedcba";
        CharSequence test = ReversedCharSequence.of(orig);

        assertEquals(reved, test.toString());
        assertEquals(orig, ReversedCharSequence.of(test));
    }

    @Test
    public void test_length() throws Exception {
        CharSequence orig = "abcdef";
        CharSequence reved = "fedcba";
        CharSequence test = ReversedCharSequence.of(orig);

        assertEquals(reved.length(), test.length());
    }

    @Test
    public void test_hashCode() throws Exception {
        CharSequence orig = "abcdef";
        CharSequence reved = "fedcba";
        CharSequence test = ReversedCharSequence.of(orig);

        assertEquals(reved.hashCode(), test.hashCode());
    }

    @Test
    public void test_charAt() throws Exception {
        CharSequence orig = "abcdef";
        CharSequence reved = "fedcba";
        CharSequence test = ReversedCharSequence.of(orig);

        int iMax = orig.length();
        for (int i = 0; i < iMax; i++) {
            assertEquals(String.valueOf(reved.charAt(i)), String.valueOf(test.charAt(i)));
        }
    }

    @Test
    public void subSequence() throws Exception {
        CharSequence orig = "abcdef";
        CharSequence reved = "fedcba";
        ReversedCharSequence test = (ReversedCharSequence) ReversedCharSequence.of(orig);

        int iMax = orig.length();
        for (int i = 0; i < iMax; i++) {
            for (int j = iMax - i - 1; j >= 0 && j >= i; j--) {
                assertEquals("subSequence(" + i + "," + j + ")", reved.subSequence(i, j), test.subSequence(i, j).toString());
                assertEquals("reverse.of(subSequence(" + i + "," + j + "))", orig.subSequence(test.reversedIndex(j) + 1, test.reversedIndex(j) + 1 + j - i), ReversedCharSequence.of(test.subSequence(i, j)));
                assertEquals("subSequence(" + i + "," + j + ").hashCode()", reved.subSequence(i, j).hashCode(), test.subSequence(i, j).hashCode());
                assertEquals("subSequence(" + i + "," + j + ").equals()", true, test.subSequence(i, j).equals(reved.subSequence(i, j)));
            }
        }
    }
}
