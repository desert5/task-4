package com.task4.task4.util;

import java.util.function.Consumer;

public class Util {
    public static <T> T also(T receiver, Consumer<T> consumer) {
        consumer.accept(receiver);
        return receiver;
    }

    public static Double inRange(Double actual, Double lowerLimit, Double upperLimit) {
        if (actual > lowerLimit && actual < upperLimit) {
            return actual;
        } else if (actual > upperLimit) {
            return upperLimit;
        } else {
            return lowerLimit;
        }
    }
}
