package container.queue;

import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TransferQueue;

/**
 * LinkedTransferQueue
 * 转移队列
 * 无容量,当接收一个数据时阻塞,直到有个线程来取数据
 *
 * add - 队列会保存数据,不做阻塞等待
 * transfer - 是TransferQueue的特有方法,必须有消费者(take()方法的调用者)
 * 如果没有任意线程消费数据,transfer方法阻塞,一般用于即时消息
 *
 * 使用transfer方法,实现数据的即时处理,没有消费者就阻塞
 */
public class Test5 {
    TransferQueue<String> queue=new LinkedTransferQueue<>();

    public static void main(String[] args) throws Exception {
        final Test5 t=new Test5();

//        new Thread(()->{
//            System.out.println(Thread.currentThread().getName()+"thread begin");
//            try {
//                System.out.println(Thread.currentThread().getName()+" - "+t.queue.take());
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        },"output thread").start();
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        /*try {
            //将数据交给线程1
            t.queue.transfer("test string");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/

        new Thread(()->{
            try {
                //将数据交给线程1
                t.queue.transfer("test string");
//                t.queue.add("test string");
                System.out.println("add end");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(1000);

        new Thread(()->{
            System.out.println(Thread.currentThread().getName()+"thread begin");
            try {
                System.out.println(Thread.currentThread().getName()+" - "+t.queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

    }
}
