package pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * SingleThreadExecutor
 *
 * 单一容量的线程池
 * 使用场景: 保证任务顺序时使用,如:游戏大厅中的公告频道聊天.秒杀
 */
public class Test5 {
    public static void main(String[] args) {
        ExecutorService service= Executors.newSingleThreadExecutor();
        System.out.println(service);

        for (int i = 0; i < 5; i++) {
            service.execute(()->{
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName()+"- test executor");
            });
        }
    }
}
