/*
 * Copyright (c) 2015-2019 Vladimir Schneider <vladimir.schneider@gmail.com>, all rights reserved.
 *
 * This code is private property of the copyright holder and cannot be used without
 * having obtained a license or prior written permission of the of the copyright holder.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
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
