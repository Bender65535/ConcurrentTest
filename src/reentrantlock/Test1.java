package reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ReentrantLock  重入锁
 *
 *  重入锁,建议应用的同步方式,相对效率比synchronized高,量级较轻,
 *  synchronized在jdk1.5版本开始,尝试优化.到jdk1.7版本后,优化效率已经非常好了
 * 在绝对效率上,不比ReentrantLock差多少
 *  使用重入锁,必须释放锁标记,一般在finally中释放锁
 */
public class Test1 {
    //ReentrantLock中有类似synchronized的方法
    Lock lock=new ReentrantLock();

    void m1(){

        try {
            lock.lock();  //加锁(相当于synchronized),在一个对象上加上标记信息
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.println("m1() method"+i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            System.out.println("end");
            lock.unlock();
        }

    }

    void m2(){
        lock.lock();
        System.out.println("m2() method");
        lock.unlock();
    }


    public static void main(String[] args) {
        final Test1 t=new Test1();
        new Thread(()->t.m1()).start();
        new Thread(()->t.m2()).start();
    }
}
