package com.github.jamesnorris.flow;

public enum Stat {
    PROXIMITY_CHANGE_COUNT(0),
    STREAM_FIX_COUNT(0),
    RAIN_FILLED_BUCKET_COUNT(0),
    CONTROLLED_BUCKET_COUNT(0),
    SPONGE_REMOVE_WATER_COUNT(0),
    SPONGE_REPLACE_WATER_COUNT(0),
    FINITE_LIQUID_COUNT(0);
    private int count;

    Stat(int initial) {
        this.count = initial;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void increment() {
        count += 1;
    }

    public void decrement() {
        count -= 1;
    }

    public void reset() {
        count = 0;
    }

    public int getCount() {
        return count;
    }
}
