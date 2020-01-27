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

package com.vladsch.flexmark.experimental.util.collection.iteration;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.function.Function;

public class MappingIterable<T, R> implements Iterable<R> {
    final private @NotNull Iterable<T> myIterable;
    final private @NotNull Function<T, R> myFunction;

    public MappingIterable(@NotNull Iterable<T> iterable, @NotNull Function<T, R> function) {
        myIterable = iterable;
        myFunction = function;
    }

    @NotNull
    @Override
    public Iterator<R> iterator() {
        return new MyIterator<>(myIterable.iterator(), myFunction);
    }

    private static class MyIterator<E, V> implements Iterator<V> {
        final private @NotNull Iterator<E> myIterator;
        final private @NotNull Function<E, V> myFunction;

        public MyIterator(@NotNull Iterator<E> iterator, @NotNull Function<E, V> function) {
            myIterator = iterator;
            myFunction = function;
        }

        @Override
        public boolean hasNext() {
            return myIterator.hasNext();
        }

        @Override
        public V next() {
            return myFunction.apply(myIterator.next());
        }
    }
}
