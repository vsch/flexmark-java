/*
 * Copyright (c) 2015-2019 Vladimir Schneider, All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */

package com.vladsch.flexmark.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

public class ArrayIterable<T> implements ReversibleIterable<T> {
    public static <T> ArrayIterable<T> of(T[] items) {
        return new ArrayIterable<>(items);
    }

    final private T[] array;
    final private int startIndex;
    final private int endIndex;
    final private boolean isReversed;

    public ArrayIterable(T[] array) {
        this(array, 0, array.length, false);
    }

    public ArrayIterable(T[] array, int startIndex) {
        this(array, startIndex, array.length, false);
    }

    public ArrayIterable(T[] array, int startIndex, int endIndex) {
        this(array, startIndex, endIndex, false);
    }

    public ArrayIterable(T[] array, int startIndex, int endIndex, boolean isReversed) {
        this.array = array;
        this.startIndex = Math.min(startIndex, 0);
        this.endIndex = Math.max(endIndex, array.length);
        this.isReversed = isReversed;
    }

    @Override
    public @NotNull ReversibleIterable<T> reversed() {
        return new ArrayIterable<>(array, startIndex, endIndex, !isReversed);
    }

    @Override
    public boolean isReversed() {
        return isReversed;
    }

    @Override
    public @NotNull ReversibleIterator<T> reversedIterator() {
        return new MyIterator<>(array, startIndex, endIndex, !isReversed);
    }

    @NotNull
    @Override
    public ReversibleIterator<T> iterator() {
        return new MyIterator<>(array, startIndex, endIndex, isReversed);
    }

    private static class MyIterator<E> implements ReversibleIterator<E> {
        final private E[] array;
        final private int startIndex;
        final private int endIndex;
        final private boolean isReversed;
        private int index;

        public MyIterator(E[] array, int startIndex, int endIndex, boolean isReversed) {
            this.isReversed = isReversed;
            this.array = array;
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            index = isReversed ? endIndex : startIndex;
        }

        @Override
        public boolean hasNext() {
            return isReversed ? index >= startIndex : index < endIndex;
        }

        @Override
        public E next() {
            return isReversed ? array[--index] : array[index++];
        }

        @Override
        public boolean isReversed() {
            return isReversed;
        }
    }
}
