package com.github.jamesnorris.flow.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings({"serial", "hiding"}) public class DataList<Object> extends CopyOnWriteArrayList<Object> {
    private boolean hasMax;
    private long max = -1;

    public DataList() {}

    public DataList(long max) {
        if (max != -1 /* if -1, infinite */&& max <= 0) {
            throw new IllegalArgumentException("Max size cannot be less than or equal to 0, unless -1");
        }
        hasMax = max != -1;
        this.max = max;
    }

    @Override public boolean add(Object obj) {
        if (hasMax && super.size() + 2 >= max) {
            return false;
        }
        return super.add(obj);
    }

    public long getMaxSize() {
        return max;
    }

    public DataList<Object> clone() {
        return (DataList<Object>) super.subList(0, super.size());// simply to make a clone
    }

    public void addAll(Object[] objects) {
        for (Object obj : objects) {
            add(obj);
        }
    }

    @SuppressWarnings("unchecked") public <O extends Object> List<O> getObjectsOfType(Class<O> type) {
        ArrayList<O> list = new ArrayList<O>();
        for (Object obj : this) {
            if (type.isAssignableFrom(obj.getClass())) {
                list.add((O) obj);
            }
        }
        return list;
    }
}
