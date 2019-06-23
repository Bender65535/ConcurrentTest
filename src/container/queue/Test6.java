package container.queue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue
 *
 * 同步队列,是一个容量为0的队列,是一个特殊的TransferQueue,
 *
 * 必须先有消费者线程等待,才能使用的队列
 *
 * add方法:无阻塞,若没有消费线程则抛出异常
 * put方法:有阻塞,若没有消费线程,阻塞等待数据
 */
public class Test6 {
    BlockingQueue<String> queue=new SynchronousQueue<>();

    public static void main(String[] args) {
        final Test6 t=new Test6();

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"thread begin");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {
                System.out.println(Thread.currentThread().getName()+" - "+t.queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        },"out thread").start();

//        t.queue.add("test add");

        try {
            t.queue.put("test put");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName()+"queue size:"+t.queue.size());
    }
}
