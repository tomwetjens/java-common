package com.wetjens.collections;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class FindOne<T> {

    private final Supplier<RuntimeException> exceptionSupplier;

    private T value;

    private FindOne(Supplier<RuntimeException> exceptionSupplier) {
        this.exceptionSupplier = exceptionSupplier;
    }

    public static <T> Collector<T, FindOne<T>, Optional<T>> findOne(Supplier<RuntimeException> exceptionSupplier) {
        return Collector.of(() -> new FindOne<>(exceptionSupplier), FindOne::accumulate, FindOne::combine, FindOne::finish);
    }

    private FindOne<T> accumulate(T element) {
        if (value == null) {
            value = element;
        } else {
            throw exceptionSupplier.get();
        }
        return this;
    }

    private static <T> FindOne<T> combine(FindOne<T> a, FindOne<T> b) {
        return a.value != null ? b.accumulate(a.value) : b;
    }

    private Optional<T> finish() {
        return Optional.ofNullable(value);
    }
}