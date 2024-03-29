package sync;

import java.util.concurrent.TimeUnit;

/**
 * 重入锁
 * 前提:同一个线程,多次调用同步代码,锁定同一个锁对象,可重入
 */
public class Test6 {
    synchronized void m1(){//锁this
        System.out.println("m1 start");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        m2();
        System.out.println("m1 end");
    }
    synchronized void m2(){//锁this
        System.out.println("m2 start");
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m2 end");
    }

    public static void main(String[] args) {
        new Test6().m1();
    }
}
