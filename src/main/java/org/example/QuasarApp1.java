package org.example;


import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;
import co.paralleluniverse.strands.SuspendableRunnable;

/**
 * @author swrd
 * @version 1.0
 * @date 2023/5/29
 */
public class QuasarApp1 {
    private static long s;

    public static long factorial(int num) throws SuspendExecution, InterruptedException {
        Strand.sleep(1000);
        if (num <= 0) return 0;
        return num == 1 ? num : num * factorial(num - 1);
    }

    public static void test1() throws SuspendExecution, InterruptedException {
        s = System.currentTimeMillis();
        new Fiber<Void>(new SuspendableRunnable() {
            @Override
            public void run() throws SuspendExecution, InterruptedException {
                long r = factorial(10);
                System.out.println(r);
                System.out.println(System.currentTimeMillis() - s);
            }
        }).start();
        System.out.println(System.currentTimeMillis() - s);
        Strand.sleep(15000); // must wait Fiber to finished
    }

    public static void test2() throws SuspendExecution, InterruptedException {
        s = System.currentTimeMillis();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    long r = factorial(10);
                    System.out.println(r);
                    System.out.println(System.currentTimeMillis() - s);
                } catch (SuspendExecution suspendExecution) {
                    suspendExecution.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //-javaagent:/Users/swrd/.m2/repository/co/paralleluniverse/quasar-core/0.7.10/quasar-core-0.7.10.jar
    public static void main(String[] args) throws SuspendExecution, InterruptedException {
        test1();
        test2();
    }
}
