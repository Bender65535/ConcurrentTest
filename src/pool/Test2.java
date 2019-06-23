package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * FixedThreadPool
 * 真正的线程池
 * 固定容量的线程池,创建线程池的时候,容量固定
 * 构造时提供线程池最大容量
 *   所有线程池中都有一个任务队列,使用的是BlockingQueue<Runnable>作为任务的载体
 *   当任务数量大于线程池容量的时候,没有运行的任务保存在任务队列中,当线程有空闲的,自动从队列中取出任务执行
 *   线程池默认的容量上限是Integer.MAX_VALUE
 *   常见的线程池容量: PC : 200  , 服务器 : 1000-10000
 *
 * ExecutorService : 线程池服务类型,所有的线程池类型都是实现了这个接口
 *  实现这个接口,代表可以提供线程池能力
 *  使用场景:大多数情况下,使用的线程池,首选推荐FixedThreadPool,OS操作系统和硬件是有线程支持上限的,不能随意的无限制提供线程池
 *
 *
 *  常见方法: void execute(Runnable);  Future submit(Callable);  Future submit(Runnable)
 *  线程池状态: Running , ShuttingDown , Terminated
 *      Running : 线程池正在执行中,活动状态
 *      ShuttingDown : 线程池正在关闭过程中.优雅关闭.一旦进入这个状态,线程池不在接受新的任务,处理所有已接收的任务,处理完毕后,关闭线程池
 *      Terminated : 线程池已经关闭
 *
 *
 *
 *  Executors: Executor的工具类, 类似Collection和Collections的关系
 *   可以更简单的创建若干种线程池
 *
 *  shutdown:优雅关必,不是强行关闭线程池,回收线程池中的资源
 *     而是不在处理新的任务,将已接收的任务处理完毕后再关闭
 *
 *  服务方法无返回值,原因是Runnable接口中的run方法无返回值
 *
 *
 *  Executor可以快速提供若干种线程池工具方法,可以快速地提供若干种线程池,如:固定容量的,无限容量的,容量为1的个种线程池
 *      线程池是一个进程级的重量级资源,默认的生命周期和jvm一致,当开启线程池后,知道jvm关闭为止,是线程池的默认生命周期
 *      如果手工调用shutdown方法,那么线程池执行所有任务后,自动关闭
 *
 *
 *
 *
 *  Executor提供了一个新的服务方法,submit,有返回值Future类型
 *     submit方法提供了重载方法,其中参数类型有Runnable的,
 *     有参数类型为Callable,可以提供线程执行后的返回值
 *
 *
 *     Future是submit的返回值,代表未来,也就是线程执行结束后的一种结果
 *      如返回值
 *
 *      Future获取线程执行结果的方法是通过get方法来获取的
 *          T.get();   阻塞等待线程执行结束,并得到结果
 *          T.get(long,TimeUnit)  阻塞固定时长,等待线程执行结束后的结果,
 *                  如果在阻塞时长内,线程未执行结束,抛出异常
 *
 *
 *  Callable:
 *      类似Runnable接口,有返回值
 *      其中定义的方法是call方法,与run方法完全一致,但是call方法有返回值
 */
public class Test2 {
    public static void main(String[] args) {
        ExecutorService service= Executors.newFixedThreadPool(5);
        for (int i = 0; i < 6; i++) {
            //传参为一个线程,execute自动运行这些线程
            service.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName()+"text - executor");
                }
            });
        }
        System.out.println(service);

        service.shutdown();
        // 是否已经结束， 相当于回收了资源。
        System.out.println(service.isTerminated());
        // 是否已经关闭， 是否调用过shutdown方法
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
