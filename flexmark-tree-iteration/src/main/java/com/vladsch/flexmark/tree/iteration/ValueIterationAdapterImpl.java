package com.vladsch.flexmark.tree.iteration;

import com.vladsch.flexmark.util.collection.iteration.ArrayIterable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;
import java.util.function.Predicate;

public class ValueIterationAdapterImpl<N, T> implements ValueIterationAdapter<N, T> {
    final private @NotNull ValueIterationConsumerAdapter<N, T> myConsumerAdapter;

    static class ConsumerAdapter<P, T> implements ValueIterationConsumerAdapter<P, T> {
        final private @NotNull Function<? super P, ? extends T> myFunction;
        final private @Nullable ValueIterationFilter<? super T> myFilter;

        public ConsumerAdapter(@NotNull Function<? super P, ? extends T> function, @Nullable ValueIterationFilter<? super T> filter) {
            myFunction = function;
            myFilter = filter;
        }

        @NotNull
        @Override
        public <R> ValueIterationConsumer<? super P, R> getConsumer(ValueIterationConsumer<? super T, R> valueConsumer) {
            return new MyValueIterationConsumer<>(myFunction, myFilter, valueConsumer);
        }

        @NotNull
        @Override
        public <R> ValueIterationConsumer<? super P, R> getConsumer(VoidIterationConsumer<? super T> voidConsumer) {
            return new MyValueIterationConsumer<>(myFunction, myFilter, new VoidToValueIConsumerAdapter<>(voidConsumer));
        }
    }

    private static class MyValueIterationConsumer<P, T, R> implements ValueIterationConsumer<P, R> {
        final private ValueIterationConsumer<? super T, R> myConsumer;
        final private Function<? super P, ? extends T> myFunction;
        final private @Nullable ValueIterationFilter<? super T> myFilter;

        public MyValueIterationConsumer(@NotNull Function<? super P, ? extends T> function, @Nullable ValueIterationFilter<? super T> filter, ValueIterationConsumer<? super T, R> consumer) {
            myFunction = function;
            myFilter = filter;
            myConsumer = consumer;
        }

        @Override
        public void accept(@NotNull P it, @NotNull ValueIteration<R> iteration) {
            T applied = myFunction.apply(it);
            if (applied == null || myFilter != null && !myFilter.filter(applied, iteration)) {
                //loop.Continue();
            } else {
                myConsumer.accept(applied, iteration);
            }
        }

        @Override
        public void beforeStart(@NotNull ValueIteration<R> iteration) {
            myConsumer.beforeStart(iteration);
        }

        @Override
        public void startRecursion(@NotNull VoidIteration iteration) {
            myConsumer.startRecursion(iteration);
        }

        @Override
        public void endRecursion(@NotNull VoidIteration iteration) {
            myConsumer.endRecursion(iteration);
        }

        @Override
        public void afterEnd(@NotNull ValueIteration<R> iteration) {
            myConsumer.afterEnd(iteration);
        }
    }

    static class ChainedConsumerAdapter<P, T, V> implements ValueIterationConsumerAdapter<P, V> {
        final private ValueIterationConsumerAdapter<? super P, T> myBeforeAdapter;
        final private ValueIterationConsumerAdapter<? super T, V> myAfterAdapter;

        public ChainedConsumerAdapter(ValueIterationConsumerAdapter<? super P, T> beforeAdapter, ValueIterationConsumerAdapter<? super T, V> afterAdapter) {
            myBeforeAdapter = beforeAdapter;
            myAfterAdapter = afterAdapter;
        }

        @NotNull
        @Override
        public <R> ValueIterationConsumer<? super P, R> getConsumer(ValueIterationConsumer<? super V, R> valueConsumer) {
            return myBeforeAdapter.getConsumer(myAfterAdapter.getConsumer(valueConsumer));
        }

        @NotNull
        @Override
        public <R> ValueIterationConsumer<? super P, R> getConsumer(VoidIterationConsumer<? super V> voidConsumer) {
            return myBeforeAdapter.getConsumer(myAfterAdapter.getConsumer(voidConsumer));
        }
    }

    public ValueIterationAdapterImpl(@NotNull Function<? super N, T> function) {
        this(function, null);
    }

    public ValueIterationAdapterImpl(@NotNull Function<? super N, T> function, @Nullable ValueIterationFilter<? super T> filter) {
        this(new ConsumerAdapter<>(function, filter));
    }

    public ValueIterationAdapterImpl(@NotNull ValueIterationConsumerAdapter<N, T> consumerAdapter) {
        myConsumerAdapter = consumerAdapter;
    }

    @NotNull
    @Override
    public ValueIterationConsumerAdapter<N, T> getConsumerAdapter() {
        return myConsumerAdapter;
    }

    @NotNull
    @Override
    public <V> ValueIterationAdapter<N, V> andThen(ValueIterationAdapter<? super T, V> after) {
        return new ValueIterationAdapterImpl<>(new ChainedConsumerAdapter<>(myConsumerAdapter, after.getConsumerAdapter()));
    }

    @NotNull
    @Override
    public ValueIterationAdapter<N, T> compose(ValueIterationAdapter<? super N, N> before) {
        return new ValueIterationAdapterImpl<>(new ChainedConsumerAdapter<>(before.getConsumerAdapter(), myConsumerAdapter));
    }

    public static <N> ValueIterationAdapter<N, N> of() {
        return new ValueIterationAdapterImpl<>(Function.identity());
    }

    public static <N> ValueIterationAdapter<N, N> of(ValueIterationFilter<? super N> filter) {
        return new ValueIterationAdapterImpl<>(Function.identity(), filter);
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Function<? super N, T> function) {
        return new ValueIterationAdapterImpl<>(function);
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Class<? extends T> clazz) {
        return new ValueIterationAdapterImpl<>(it -> clazz.isInstance(it) ? clazz.cast(it) : null);
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Iterable<Class<? extends T>> clazzes) {
        return new ValueIterationAdapterImpl<>(it -> {
            for (Class<? extends T> clazz : clazzes) {
                if (clazz.isInstance(it)) return clazz.cast(it);
            }
            return null;
        });
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Class<? extends T>... clazzes) {
        return of(new ArrayIterable<>(clazzes));
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Class<? extends T> clazz, Predicate<? super T> filter) {
        return new ValueIterationAdapterImpl<>(it -> clazz.isInstance(it) ? clazz.cast(it) : null, (it, loop) -> filter.test(it));
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Predicate<? super T> filter, Iterable<Class<? extends T>> clazzes) {
        return new ValueIterationAdapterImpl<>(it -> {
            for (Class<? extends T> clazz : clazzes) {
                if (clazz.isInstance(it)) return clazz.cast(it);
            }
            return null;
        }, (it, loop) -> filter.test(it));
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Predicate<? super T> filter, Class<? extends T>... clazzes) {
        return of(filter, new ArrayIterable<>(clazzes));
    }

    public static <N, T> ValueIterationAdapter<N, T> of(Class<T> clazz, ValueIterationFilter<? super T> filter) {
        return new ValueIterationAdapterImpl<>(((it) -> clazz.isInstance(it) ? clazz.cast(it) : null), filter);
    }

    public static <N, T> ValueIterationAdapter<N, T> of(ValueIterationFilter<? super T> filter, Class<? extends T>... clazzes) {
        return of(filter, new ArrayIterable<>(clazzes));
    }

    public static <N, T> ValueIterationAdapter<N, T> of(ValueIterationFilter<? super T> filter, Iterable<Class<? extends T>> clazzes) {
        return new ValueIterationAdapterImpl<>(it -> {
            for (Class<? extends T> clazz : clazzes) {
                if (clazz.isInstance(it)) return clazz.cast(it);
            }
            return null;
        }, filter);
    }
}
