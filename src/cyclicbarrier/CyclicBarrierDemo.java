package cyclicbarrier;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * CyclicBarrier的字面意思是可循环(Cyclic)使用的是屏障(Barrier).它要做的事情是,
 * 让一组线程到达一个屏障(也可以叫同步点)时被阻塞,知道最后一个线程到达屏障时,
 * 屏障才会开门,所有被屏障拦截的线程才会继续干活,线程进入屏障通过CyclicBarrier的await方法
 * 实例:开会时人没到就等待,到了就开会
 *
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier=new CyclicBarrier(7,()-> System.out.println("********召唤神龙"));

        for (int i = 0; i < 7; i++) {
            final int tempInt=i;
            new Thread(()->{
                System.out.println(Thread.currentThread().getName()+"收集到第"+tempInt+"颗龙珠");
                try {
                    //先到了就阻塞等待
                    cyclicBarrier.await();
                    //await数量到7后就释放屏障
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
