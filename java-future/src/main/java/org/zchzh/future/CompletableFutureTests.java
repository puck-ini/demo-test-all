package org.zchzh.future;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.*;

/**
 * @author zengchzh
 * @date 2021/5/27
 */
public class CompletableFutureTests {

    public static void main(String[] args) throws InterruptedException {
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
//            @Override
//            public String get() {
//                return "hello world";
//            }
//        });
//        CompletableFuture.runAsync(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("hello world");
//            }
//        });
        CompletableFuture<String> future = new CompletableFuture<>();
//        CompletableFuture<String> future = CompletableFuture.supplyAsync(new Supplier<String>() {
//            @Override
//            public String get() {
////                try {
////                    TimeUnit.SECONDS.sleep(1);
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
////                return "test";
//                throw new RuntimeException("test exception");
//            }
//        });

        future.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (Objects.nonNull(s)) {
                    System.out.println("future whenComplete s = " + s);
                }
                if (Objects.nonNull(throwable)) {
                    System.out.println("future whenComplete exception");
                    throwable.printStackTrace();
                }
            }
        });

        future.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (Objects.nonNull(s)) {
                    System.out.println("future whenCompleteAsync s = " + s);
                }
                if (Objects.nonNull(throwable)) {
                    System.out.println("future whenCompleteAsync exception");
                    throwable.printStackTrace();
                }
            }
        });

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("thenAccept1 s = " + s);
            }
        });

        future.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("thenAccept2 s = " + s);
            }
        });

        future.thenAcceptAsync(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("thenAcceptAsync s = " + s);
            }
        });

        future.thenApply(new Function<String, Object>() {
            @Override
            public Object apply(String s) {
                System.out.println("thenApply s = " + s);
                return s;
            }
        });

        // 提前 future1 complete 不会执行下面的 future.thenApplyAsync
        CompletableFuture<String> future1 = future.thenApplyAsync(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("future1 thenApplyAsync s = " + s);
                return "new " + s;
            }
        });

        future1.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (Objects.nonNull(s)) {
                    System.out.println("future1 whenCompleteAsync s = " + s);
                }
                if (Objects.nonNull(throwable)) {
                    System.out.println("future1 whenCompleteAsync exception");
                    throwable.printStackTrace();
                }
            }
        });

        CompletableFuture<String> exceptionFuture = future.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                System.out.println("exceptionally");
                throwable.printStackTrace();
                return throwable.getMessage();
            }
        });
        exceptionFuture.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("exceptionFuture thenAccept s = " + s);
            }
        });

        CompletableFuture<String> future2 = future.thenCombineAsync(future1, new BiFunction<String, String, String>() {
            @Override
            public String apply(String s, String s2) {
                return s + s;
            }
        });
        future2.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("future2 thenAccept s = " + s);
            }
        });


        // future1 提前 complete
//        future1.complete("test future1 complete");
//        TimeUnit.SECONDS.sleep(5);
        future.complete("test");
//        TimeUnit.SECONDS.sleep(5);
        //  future.complete 执行 future1 的 whenCompleteAsync 之后，future1 执行 complete 不会重复执行 whenCompleteAsync
//        future1.complete("test future1 complete");
        // 测试 completeExceptionally
//        future.completeExceptionally(new RuntimeException("exception test"));
        System.out.println("isCancelled： " + future.isCancelled());
        System.out.println("isDone： " + future.isDone());
        System.out.println("isCompletedExceptionally： " + future.isCompletedExceptionally());
//        System.out.println("future result : " + future.getNow("123"));
        TimeUnit.SECONDS.sleep(5);
    }


    private CompletableFuture<String> strFuture;

    

    @Test
    @BeforeEach
    public void initFuture() {
        System.out.println("init");
        strFuture = new CompletableFuture<>();
    }

    @Test
    @AfterEach
    public void completeFuture() throws InterruptedException {
//        System.out.println(Thread.currentThread().getName());
        strFuture.complete("hello future");
        System.out.println("complete");
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
//    @AfterEach
    public void completeExceptionally() throws InterruptedException {
        strFuture.completeExceptionally(new RuntimeException("test exception"));
        System.out.println("complete exception");
        TimeUnit.MILLISECONDS.sleep(100);
    }

    @Test
    public void testWhenComplete() {
//        System.out.println(Thread.currentThread().getName());
        strFuture.whenComplete(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (Objects.nonNull(s)) {
                    System.out.println("testWhenComplete ： " + LocalDateTime.now() + ", result : " + s);
                } else if (Objects.nonNull(throwable)) {
                    System.out.println("testWhenComplete ： " + LocalDateTime.now()+ " : " + throwable.getMessage());
                }
            }
        });
    }

    @Test
    public void testWhenCompleteAsync() {
        strFuture.whenCompleteAsync(new BiConsumer<String, Throwable>() {
            @Override
            public void accept(String s, Throwable throwable) {
                if (Objects.nonNull(s)) {
                    System.out.println("testWhenCompleteAsync ： " + LocalDateTime.now() + ", result : " + s);
                } else if (Objects.nonNull(throwable)) {
                    System.out.println("testWhenCompleteAsync ： " + LocalDateTime.now()+ " : " + throwable.getMessage());
                }
            }
        });
    }

    @Test
    public void testThenAccept() {
        strFuture.thenAccept(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("testThenAccept ： " + LocalDateTime.now() + ", result : " + s);
            }
        });
    }

    @Test
    public void testThenAcceptAsync() {
        strFuture.thenAcceptAsync(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println("testThenAccept ： " + LocalDateTime.now() + ", result : " + s);
            }
        });
    }


    @Test
    public void testThenApply() {
        strFuture.thenApply(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("testThenApply ： " + LocalDateTime.now() + ", result : " + s);
                return s + " thenApply";
            }
        });
    }

    @Test
    public void testThenApplyAsync() {
        strFuture.thenApplyAsync(new Function<String, String>() {
            @Override
            public String apply(String s) {
                System.out.println("testThenApplyAsync ： " + LocalDateTime.now() + ", result : " + s);
                return s + " thenApplyAsync";
            }
        });
    }

    @Test
    public void testExceptionally() {
        strFuture.exceptionally(new Function<Throwable, String>() {
            @Override
            public String apply(Throwable throwable) {
                System.out.println("testThenApplyAsync ： " + LocalDateTime.now() + " : " + throwable.getMessage());
                return throwable.getMessage();
            }
        });
    }
}
