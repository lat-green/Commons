package com.greentree.commons.tests;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

public final class ExecuteCounter implements Runnable, AutoCloseable {

    private final int count;
    private final AtomicInteger incrementCount;

    public ExecuteCounter(int count) {
        this.count = count;
        this.incrementCount = new AtomicInteger();
    }

    @Override
    public void run() {
        incrementCount.incrementAndGet();
    }

    @Override
    public void close() {
        assertEquals(count, incrementCount.get());
    }

}
