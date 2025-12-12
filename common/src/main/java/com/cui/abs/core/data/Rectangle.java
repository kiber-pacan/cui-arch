package com.cui.abs.core.data;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/// <h5> Render purpose rectangle </h5>
public class Rectangle {
    public Pair<Integer, Integer> position;

    public Pair<Integer, Integer> dimensions;

    public Rectangle(@NotNull Pair<Integer, Integer> position, @NotNull Pair<Integer, Integer> dimensions) {
        this.position = position;
        this.dimensions = dimensions;
    }

    public Rectangle(int x, int y, int width, int height) {
        this.position = new Pair<>(x, y);
        this.dimensions = new Pair<>(width, height);
    }

    public Rectangle(@NotNull Pair<Integer, Integer> position, int width, int height) {
        this.position = position;
        this.dimensions = new Pair<>(width, height);
    }

    public Rectangle(int x, int y, @NotNull Pair<Integer, Integer> dimensions) {
        this.position = new Pair<>(x, y);
        this.dimensions = dimensions;
    }

    public boolean isPositionFilled() {
        return position != null && position.isFilled();
    }

    public boolean isDimensionsFilled() {
        return dimensions != null && dimensions.isFilled();
    }

    public boolean isFilled() {
        return isPositionFilled() && isDimensionsFilled();
    }

    public boolean isEmpty() {
        return !isPositionFilled() && !isDimensionsFilled();
    }
}
