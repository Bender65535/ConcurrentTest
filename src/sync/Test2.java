package sync;

/**
 * 锁是在堆空间或方法区中
 * 其他线程可以访问锁对象,但不能给锁对象加锁
 */
public class Test2 {
    private static int count;

    /**
     * 静态同步方法锁的是当前的class类对象
     */
    public static synchronized void testSync1() {
        System.out.println(Thread.currentThread().getName() + "count=" + count);
    }

    public static void testSync2() {
        synchronized (Test2.class) {
            System.out.println(Thread.currentThread().getName() + "count=" + count);
        }
    }
}
