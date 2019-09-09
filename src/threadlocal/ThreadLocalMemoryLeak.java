package threadlocal;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal导致的内存泄漏
 */
public class ThreadLocalMemoryLeak {
    public static final int TACK_LOOP_SIZE=100;

    final static ThreadPoolExecutor poolExecutor
            =new ThreadPoolExecutor(5,5,1,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingDeque<>());

    static class LocalVariable{

        private byte[] a=new byte[1024*1024*5];
    }

    ThreadLocal<LocalVariable> localVariable;

    public static void main(String[] args) {
        for (int i = 0; i < 100000; i++) {
            /**
             * 由于使用线程池,线程不会销毁
             */
            poolExecutor.execute(()->{
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                ThreadLocalMemoryLeak oom=new ThreadLocalMemoryLeak();
                oom.localVariable=new ThreadLocal<>();
                oom.localVariable.set(new LocalVariable());
                System.out.println("use local");
            });
            /**
             * 如果线程不被释放,那么这个线程将一直绑定着ThreadLocalMap
             */
        }
    }
}
