package com.github.jamesnorris.flow.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@SuppressWarnings("serial") public class ProbabilityMap<K extends Object, V extends Number> extends HashMap<Object, Number> {
    private static final Random RANDOM = new Random();
    private int maxProbability = 100;

    @Override public Number put(Object key, Number value) {
        if (value.doubleValue() > maxProbability) {
            value = maxProbability;
        }
        if (value.doubleValue() <= 0) {
            return null;// don't put anything
        }
        return super.put(key, value);
    }

    public void setMaxProbability(int max) {
        int oldProbability = maxProbability;
        maxProbability = max;
        Map<Object, Number> newMap = new ProbabilityMap<Object, Number>();
        for (Object obj : keySet()) {
            double value = (get(obj).doubleValue() * max) / oldProbability;
            newMap.put(obj, value);
        }
        super.clear();
        super.putAll(newMap);
    }

    public int getMaxProbability() {
        return maxProbability;
    }

    public Object getProbableKey() {
        int totalPercent = RANDOM.nextInt(maxProbability) * size();
        int addedPercent = 0;
        for (Object obj : keySet()) {
            int percent = get(obj).intValue();
            addedPercent += percent;
            if (totalPercent <= addedPercent && totalPercent > addedPercent - percent) {
                return obj;
            }
        }
        return null;
    }
}
