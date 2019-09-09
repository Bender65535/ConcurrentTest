package aqs;


import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 错误的实现aqs
 */

/**
 * m++ ---->
 *   将m从0变1,cas要传两个值:(原来的值,期望的值)
 *   要先判断m值是否为0,如果m为0,将m变为1
 *                  如果不是0,说明另一个线程把m改变了,那就不做任何操作
 *   cas时往往做死循环,不断地进行cas操作(自旋),直到m为0,释放后占据该锁
 *
 *   cas必须为原子操作
 *
 *   锁的竞争情况不激烈时使用cas
 *
 *   cas自旋时消耗cpu资源
 */
public class Test1 extends AbstractQueuedSynchronizer {
    /**
     * aqs中保存着一个AtomicInteger数state
     * 在state的基础之上管理一系列的队列(FIFO双向链表)
     * 当有新的线程访问state的时候,如果竞争不到这个数的时候,就进入队列等待
     *
     * 线程调用tryAcquire判断是否拿到state,如果拿到,将0设为1,return true,表示当前线程已经持有这把锁
     */
    @Override
    protected boolean tryAcquire(int arg) {
        //cas
        if(compareAndSetState(0,1)){
            //设置互斥锁-共享锁

            setExclusiveOwnerThread(Thread.currentThread());
            return true;
        }
        return false;
    }

    /**
     * 尝试释放锁
     */
    @Override
    protected boolean tryRelease(int arg) {
        //有错误
        setExclusiveOwnerThread(null);
        setState(0);
        return true;
    }

    /**
     * 判断线程是否持有这把锁
     */
    @Override
    protected boolean isHeldExclusively() {
        //有错误
        return getState()==1;
    }
}
