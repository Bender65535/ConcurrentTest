package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * CachedThreadPool
 * 缓存的线程池,容量不限,自动扩容,
 * 容量管理策略:如果线程池中的线程数量不满足任务指向,创建新的线程,每次都有新任务无法即时处理的时候,都会创建新的线程
 * 当线程池中的线程空闲时长打到一定的临界值,自动释放线程
 * 默认线程空间60秒,自动销毁
 *
 * 应用场景:内部应用或测试应用.内部应用,有条件的内部数据瞬间处理时应用,
 *          如:电信平台夜间执行数据(有把握在短时间内处理完所有工作,且对硬件有足够的信心)
 *            测试应用,在测试的时候尝试得到硬件或软件的最高负载量,用于提供FixedThread容量的指导
 */
public class Test3 {
    public static void main(String[] args) {
        ExecutorService service= Executors.newCachedThreadPool();
        System.out.println(service);
        for (int i = 0; i < 5; i++) {
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
        try {
            Thread.sleep(65000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service);
    }
}
