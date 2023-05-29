package org.example;

import com.ea.async.Async;
import com.google.common.util.concurrent.*;

import java.util.concurrent.*;

/**
 * Hello world!
 */
public class App {
    public static long factorial(int num) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException ignored) {
        }
        if (num <= 0) return 0;
        return num == 1 ? num : num * factorial(num - 1);
    }

    public static void future() throws Exception {
        ExecutorService threadpool = Executors.newFixedThreadPool(1);
        Future<Long> future = threadpool.submit(() -> factorial(10));
        while (!future.isDone()) {
            System.out.println("waiting...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("get..." + future.get());
        threadpool.shutdown();
    }

    public static void completableFuture() throws Exception {
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(10));
        while (!completableFuture.isDone()) {
            System.out.println("waiting...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("get..." + completableFuture.get());
    }

    public static void guavaFuture() throws Exception {
        ExecutorService threadpool = Executors.newCachedThreadPool();
        ListeningExecutorService service = MoreExecutors.listeningDecorator(threadpool);
        ListenableFuture<Long> guavaFuture = service.submit(()->factorial(10));
        while (!guavaFuture.isDone()) {
            System.out.println("waiting...");
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println("get..." + guavaFuture.get());
        threadpool.shutdown();
    }

    public static void eaFuture() {
        Async.init();
        CompletableFuture<Long> completableFuture = CompletableFuture.supplyAsync(() -> factorial(10));
        System.out.println("get..." + Async.await(completableFuture));
    }

    public static void main(String[] args) throws Exception {
//        future();
//        completableFuture();
//        guavaFuture();
        eaFuture();
    }
}
