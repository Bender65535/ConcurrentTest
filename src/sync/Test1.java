package sync;

/**
 * synchronize为锁对象:this,临界资源对象(共享的对象,多线程都能访问到的对象),Class类对象
 *
 * 每个线程中有线程栈帧(存引用)
 *
 * 缺点:一致性保证,并发性下降
 */
public class Test1 {
    private int count = 0;
    private Object o = new Object();

    /**
     * 临街资源对象
     *
     * 对当前对象访问限定较低时使用临界资源对象,锁级别比较高时使用this
     */
    public void testSync1() {
        synchronized (o) {
            System.out.println(Thread.currentThread().getName() + "count=" + count);
        }
    }

    public void testSync2() {
        synchronized (this) {
            System.out.println(Thread.currentThread().getName() + "count=" + count);
        }
    }

    /**
     * 与test2相同都是锁当前对象
     *
     * 同步方法的锁粒度太大
     */
    public synchronized void testSync3() {
        System.out.println(Thread.currentThread().getName() + "count=" + count);
    }
}
