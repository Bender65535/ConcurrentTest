package atom;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * AtomicXxx
 * 原子操作类型,其中的每个方法都是原子操作,可以保证线程安全
 */
public class Test1 {
    AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 10000; i++) {
//            if (count.get() < 10000)
            //++count
            count.incrementAndGet();
        }
    }

    public static void main(String[] args) {
        final Test1 t = new Test1();
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(() -> t.m()));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(t.count.intValue());
    }
}
