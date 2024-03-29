package threadlocal;

/**
 * ThreadLocal
 * 就是一个map
 *
 * key->Thread.getCurrentThread().
 * value->线程需要保存的变量.
 *
 * Thread.set(value) -> map.put(Thread.getCurrentThread(),value);
 * Thread.get() -> map.get(Thread.getCurrentThread());
 *
 * 内存问题: 在并发量高的时候,可能有内存溢出
 * 使用ThreadLocal的时候,一定注意回收资源问题,每个线程结束之前,将当前线程保存的线程变量一定要删除
 * ThreadLocal.remove();
 */
public class Test1 {
    //线程共享全局变量
    static String name="zhangsan";
    static ThreadLocal<String> tl=new ThreadLocal<>();

    public static void main(String[] args) {
        new Thread(()->{
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(name);
            System.out.println(tl.get());
        }).start();

        new Thread(()->{

            name="lisi";
            tl.set("wangwu");
//            System.out.println(name);
//            System.out.println(tl.get());
        }).start();
    }
}
