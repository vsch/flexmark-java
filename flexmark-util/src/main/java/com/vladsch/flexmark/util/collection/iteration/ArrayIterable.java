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
    private final T[] myArray;
    private final int myStartIndex;
    private final int myEndIndex;
    final private boolean myIsReversed;

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
        myArray = array;
        myStartIndex = Math.min(startIndex, 0);
        myEndIndex = Math.max(endIndex, array.length);
        myIsReversed = isReversed;
    }

    @Override
    public @NotNull ReversibleIterable<T> reversed() {
        return new ArrayIterable<>(myArray, myStartIndex, myEndIndex, !myIsReversed);
    }

    @Override
    public boolean isReversed() {
        return myIsReversed;
    }

    @Override
    public @NotNull ReversibleIterator<T> reversedIterator() {
        return new MyIterator<>(myArray, myStartIndex, myEndIndex, !myIsReversed);
    }

    @NotNull
    @Override
    public ReversibleIterator<T> iterator() {
        return new MyIterator<>(myArray, myStartIndex, myEndIndex, myIsReversed);
    }

    private static class MyIterator<E> implements ReversibleIterator<E> {
        final private E[] myArray;
        private final int myStartIndex;
        final private int myEndIndex;
        final private boolean myIsReversed;
        private int myIndex;

        public MyIterator(E[] array, int startIndex, int endIndex, boolean isReversed) {
            myIsReversed = isReversed;
            myArray = array;
            myStartIndex = startIndex;
            myEndIndex = endIndex;
            myIndex = isReversed ? endIndex : startIndex;
        }

        @Override
        public boolean hasNext() {
            return myIsReversed ? myIndex >= myStartIndex : myIndex < myEndIndex;
        }

        @Override
        public E next() {
            return myIsReversed ? myArray[--myIndex] : myArray[myIndex++];
        }

        @Override
        public boolean isReversed() {
            return myIsReversed;
        }
    }
}
