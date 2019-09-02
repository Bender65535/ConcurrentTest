package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 门栓
 * 可以和锁混合使用,或替代锁的功能
 * 在门闩完全开发之前等待,当门闩完全开放后执行
 * 避免锁的效率低下问题(效率比重量级锁高)
 *
 * 让一些线程阻塞直到另一些线程完成一系列操作后才被唤醒
 */
public class Test1 {
    //在一个门上挂5个锁
    CountDownLatch latch=new CountDownLatch(5);

    void m1(){
        try {
            latch.await();  //等待门栓开发
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("m1()method");
    }

    void m2(){
        for (int i = 0; i < 10; i++) {
            if(latch.getCount()!=0){
                System.out.println("latch count:"+latch.getCount());
                //减门栓上的锁
                latch.countDown();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m2()method:"+i);

        }
    }

    public static void main(String[] args) {
        final Test1 t=new Test1();
        new Thread(()->t.m1()).start();
        new Thread(()->t.m2()).start();

    }
}
