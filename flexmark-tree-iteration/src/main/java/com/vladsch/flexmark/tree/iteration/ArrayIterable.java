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

package com.vladsch.flexmark.tree.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

public class ArrayIterable<T> implements Iterable<T> {
    private final T[] myArray;

    public ArrayIterable(T[] array) {
        myArray = array;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new MyIterator<>(myArray);
    }

    private static class MyIterator<E> implements Iterator<E> {
        private int myIndex;
        final private E[] myArray;

        public MyIterator(E[] array) {
            myArray = array;
        }

        @Override
        public boolean hasNext() {
            return myIndex < myArray.length;
        }

        @Override
        public E next() {
            return myArray[myIndex++];
        }
    }
}
