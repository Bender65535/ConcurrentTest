package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadPoolExecutor
 *
 * 使用场景:默认提供的线程池不满足条件的时候使用,如:初始化线程数为4,最大线程数为200,线程空闲周期30秒
 *
 * 线程池要用尽量用ThreadPoolExecutor创建,而不是Executors:
 *      FixedThreadPool和SingleThread:
 *          允许请求队列长度为:Integer.MAX_VALUE,可能会堆积大量的请求,从而导致内存溢出
 *      CachedThreadPool:
 *          允许的创建线程数量为Integer.MAX_VALUE,可能会创建大量的线程,从而导致OOM
 */
public class Test7 {
    public static void main(String[] args) {
        //模拟fixedThreadPool,
        // 核心线程5个(创建线程池的时候默认有多少线程,也是线程池保持的最少线程数)
        // 最大容量5个,
        // 线程的生命周期无限(线程空闲多久后自动回收,0为永久)
        //BlockingQueue<Runnable>(任务队列,阻塞线程,泛型必须是Runnable)
        ExecutorService service=new ThreadPoolExecutor(5,5,0L, TimeUnit.MILLISECONDS,new LinkedBlockingDeque<Runnable>());
        for (int i = 0; i < 6; i++) {
            service.execute(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+" - test executor");
            });
        }
        System.out.println(service);
        service.shutdown();

        System.out.println(service.isTerminated());

        System.out.println(service.isShutdown());
        System.out.println(service);

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // service.shutdown();
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());
        System.out.println(service);
    }
}
