package com.example.sftpdemo.utils;

import java.util.function.Supplier;

public class RetryHelper<T> {
    private int retryCounter;
    private final int maxRetries;

    public RetryHelper(int maxRetries) {
        this.maxRetries = maxRetries;
    }

    public T run(Supplier<T> function) {
        try {
            return function.get();
        } catch (Exception e) {
            return retry(function);
        }
    }

    public int getRetryCounter() {
        return retryCounter;
    }

    private T retry(Supplier<T> function) throws RuntimeException {
        System.out.println("FAILED - Job failed, will be retried " + maxRetries + " times.");
        retryCounter = 0;
        while (retryCounter < maxRetries) {
            try {
                return function.get();
            } catch (Exception ex) {
                retryCounter++;
                System.out.println("FAILED - Job failed on retry " + retryCounter + " of " + maxRetries + " error: " + ex );
                if (retryCounter >= maxRetries) {
                    System.out.println("Max retries exceeded.");
                    break;
                }
            }
        }
        throw new RuntimeException("Job failed on all of " + maxRetries + " retries");
    }
}