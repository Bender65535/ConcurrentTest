package queue;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * BlockingQueue
 *
 * 阻塞容器
 * put&take - 自动阻塞
 * put自动阻塞,队列容量满后,自动阻塞
 * take自动阻塞方法,队列容量为0后,自动阻塞
 */
public class Test2 {
    final BlockingQueue<String> queue=new LinkedBlockingDeque<>(1);
    final Random r=new Random();

    public static void main(String[] args) {
        final Test2 t=new Test2();

        new Thread(()->{
            while (true){
                try {
                    t.queue.put("value"+t.r.nextInt(1000));
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"producer").start();

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                while (true){
                    try {
                        System.out.println(Thread.currentThread().getName()+"-"+t.queue.take());
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"consumer"+i).start();
        }
    }
}
