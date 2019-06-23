package pool;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * ScheduledThreadPool
 * 定时完成任务scheduleAtFixedRate(Runnable,start_limit,limit,timeUnit)
 * runnable:要执行的任务
 * start_limit:第一次任务执行的间隔
 * limit:多次任务执行的间隔
 * timeUnit:任务执行间隔的时间
 * 阻塞式
 * 本质是DelayQueue
 *
 * 使用场景:计划任务时选用(DelayQueue),如:电信行业中的数据整理,没分钟整理,每小时整理,每天整理
 */
public class Test4 {
    public static void main(String[] args) {
        ScheduledExecutorService service= Executors.newScheduledThreadPool(3);
        System.out.println(service);

        service.scheduleAtFixedRate(()->{
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName());
        },0,300, TimeUnit.MILLISECONDS);
    }
}
