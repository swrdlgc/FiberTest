package org.example;

import com.ea.async.Async;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * @author swrd
 * @version 1.0
 * @date 2023/5/29
 */
public class AsyncApp {
    public static void sleep(int t) {
        try {
            Thread.sleep(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static CompletableFuture<String> hello() {
        sleep(1000);
        return CompletableFuture.completedFuture("hello");
    }

    public static CompletableFuture<String> mergeWorld(String s) {
        sleep(1000);
        return CompletableFuture.completedFuture(s + "word");
    }

    public static void print(String s) {
        System.out.println(s);
    }

    public static void test1() throws ExecutionException, InterruptedException {
        CompletableFuture<Void> completableFuture = hello()
                .thenComposeAsync(hello -> mergeWorld(hello))
                .thenAcceptAsync(helloWorld -> print(helloWorld))
                .exceptionally(throwable -> {
                    System.out.println(throwable.getCause());
                    return null;
                });
        completableFuture.get();
    }

    public static void test2() {
        //Async.init();
        String hello = Async.await(hello());
        String helloWorld = Async.await(mergeWorld(hello));
        Async.await(CompletableFuture.runAsync(() -> print(helloWorld)));
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        test1();
        test2();
    }
}
