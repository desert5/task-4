package com.task4.task4.util;

import java.util.function.Consumer;

public class Util {
    public static <T> T also(T receiver, Consumer<T> consumer) {
        consumer.accept(receiver);
        return receiver;
    }
}
