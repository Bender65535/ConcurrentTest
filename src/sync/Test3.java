package sync;

/**
 * 加锁的目的:保证操作的原子性
 */
public class Test3 implements Runnable{
    private int count=0;

    @Override
    public /*synchronized*/ void run() {
        System.out.println(Thread.currentThread().getName()
        +"\t count="+count++);
    }

    public static void main(String[] args) {
        Test3 t =new Test3();
        for (int i = 0; i <50; i++) {
            //参数2为线程名字
            new Thread(t,"thread"+i).start();
        }
    }
}
