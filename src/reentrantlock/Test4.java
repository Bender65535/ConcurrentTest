package reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 公平锁
 *
 * 操作系统中本身是不公平的(竞争机制)
 */
public class Test4 {
    public static void main(String[] args) {
//        TestReentrantlock t=new TestReentrantlock();
        TestSync t=new TestSync();
        Thread t1=new Thread(t);
        Thread t2=new Thread(t);
        t1.start();
        t2.start();
    }
}
class TestReentrantlock extends Thread{
    //定义一个公平锁
    private static ReentrantLock lock=new ReentrantLock(true);
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            lock.lock();
            try {
                System.out.println(Thread.currentThread().getName()+"get lock");
            }finally {
                lock.unlock();
            }
        }
    }
}

class TestSync extends Thread{
    @Override
    public void run() {
        for (int i = 0; i < 5; i++) {
            synchronized (this){
                System.out.println(Thread.currentThread().getName()+"get lock");
            }
        }
    }
}