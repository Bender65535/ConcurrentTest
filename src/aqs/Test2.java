package aqs;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * 修正版  换为内部类(文档中的建议)
 */

public class Test2 implements Lock {

    private Sync sync=new Sync();

    @Override
    public void lock() {
        /**
         * acquire:
         *          判断!tryAcquire和acquireQueued方法
         *          没有拿到锁,并且进入队列,如果也没拿到,就进行中断
         */
        sync.acquire(1);
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

    }

    @Override
    public Condition newCondition() {
        return null;
    }

    private class Sync extends AbstractQueuedSynchronizer {
        //被acquire方法调用
        @Override
        protected boolean tryAcquire(int acquire) {
            //acquire必须为1
            assert acquire == 1;
            if(compareAndSetState(0,1)){
                //设置互斥锁-共享锁
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }

        //被release方法调用
        @Override
        protected boolean tryRelease(int release) {
            assert release==1;
            //判断当前线程是否独一无二地持有该锁
            if(!isHeldExclusively()) throw new IllegalMonitorStateException();
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            //获得当前排他锁,如果和当前线程一样,返回true
            return getExclusiveOwnerThread()==Thread.currentThread();
        }
    }
}
