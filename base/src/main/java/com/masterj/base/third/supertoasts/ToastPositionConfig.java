package com.masterj.base.third.supertoasts;

import java.util.Objects;

public class ToastPositionConfig {
    private int gravity;
    private int xOffset;
    private int yOffset;

    public ToastPositionConfig(int gravity, int xOffset, int yOffset) {
        this.gravity = gravity;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public int getGravity() {
        return gravity;
    }

    public int getxOffset() {
        return xOffset;
    }

    public int getyOffset() {
        return yOffset;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ToastPositionConfig that = (ToastPositionConfig) o;
        return getGravity() == that.getGravity() &&
                getxOffset() == that.getxOffset() &&
                getyOffset() == that.getyOffset();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getGravity(), getxOffset(), getyOffset());
    }
}
