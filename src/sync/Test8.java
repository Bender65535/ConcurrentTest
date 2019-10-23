package sync;

/**
 * 锁与异常
 * 当同步方法中发生异常,自动释放锁资源,不会影响其他线程的执行
 * 注意,同步业务逻辑中,如果发生异常如何处理
 */
public class Test8 {
    int i = 0;

    synchronized void m() {
        System.out.println(Thread.currentThread().getName() + "- start");
        while (true) {
            i++;
            System.out.println(Thread.currentThread().getName() + "- " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (i == 5) {
                i = 1 / 0;
            }
        }
    }

    public static void main(String[] args) {
        final Test8 t = new Test8();

        //调用同一个对象的同步方法
        new Thread(() -> t.m(), "t1").start();

        //t1抛出异常释放锁后就不再执行
        new Thread(() -> t.m(), "t2").start();
    }
}
