package semaphore;

import java.util.concurrent.Semaphore;

/**
 * 信号量主要用于两个目的,一个是用于多个共享资源互斥使用,
 * 另一个用于并发线程数的控制
 * Semaphore(int permits,boolean fair)
 * permits表示资源数量; fair表示是否是公平锁(默认非公平锁,非公平锁效率高)
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //6部车抢3个车位
        Semaphore semaphore=new Semaphore(3);
        for (int i = 0; i < 6; i++) {
            new Thread(()->{
                try {
                    //已经抢占到车位
                    semaphore.acquire();
                    System.out.println(Thread.currentThread().getName()+"\t抢到车位");
                    Thread.sleep(3000);
                    System.out.println(Thread.currentThread().getName()+"\t停车3秒后离开车位");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }finally {
                    //车离开后释放停车位
                    semaphore.release();
                }
            },String.valueOf(i)).start();
        }
    }
}
