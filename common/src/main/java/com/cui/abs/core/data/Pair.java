package com.cui.abs.core.data;

import org.jetbrains.annotations.Nullable;

public class Pair <T1, T2> {
    public T1 first;
    public T2 second;

    public Pair(@Nullable T1 first, @Nullable T2 second) {
        this.first = first;
        this.second = second;
    }

    public boolean isEmpty() {
        return first == null && second == null;
    }

    public boolean isFilled() {
        return !isEmpty();
    }

    public static Pair nullPair() {
        return new Pair<>(null, null);
    }
}
