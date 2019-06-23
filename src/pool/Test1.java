package pool;

import java.util.concurrent.Executor;

/**
 * 线程池
 *
 * 线程池底层处理机制,在使用线程池的时候,底层如何调用线程中的逻辑
 * 线程池就是创建一堆的Executor(已经启动好的线程)
 * Executor不能称作线程池
 */
public class Test1 implements Executor {
    public static void main(String[] args) {
        new Test1().execute(()-> System.out.println(Thread.currentThread().getName()+" - test executor"));
    }

    @Override
    public void execute(Runnable command) {
        new Thread(command).start();
    }
}
