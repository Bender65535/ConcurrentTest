package reentrantlock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尝试锁
 */
public class Test2 {
    Lock lock = new ReentrantLock();

    void m1() {
        lock.lock();
        try {
            for (int i = 0; i < 10; i++) {
                Thread.sleep(1000);
                System.out.println("m1() method"+i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    void m2() {
        boolean isLocked = false;
        //尝试锁,如果有锁,无法获取锁标记,返回false
        //如果获取锁标记,返回true
//        isLocked = lock.tryLock();

        //阻塞尝试锁,阻塞参数代表时长,尝试获取锁标记
        //如果超时,不等待,直接返回
        try {
            isLocked = lock.tryLock(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (isLocked) {
            System.out.println("m2() method synchronized");
        } else {
            System.out.println("m2() method unsynchronized");
        }

        if (isLocked) {
            //尝试锁在解除锁标记的时候,一定要判断是否获取到锁标记
            //如果当前线程没有获取到锁标记,会抛出异常
            lock.unlock();
        }
    }


    public static void main(String[] args) {
        final Test2 t=new Test2();
        new Thread(()->t.m1()).start();
        new Thread(()->t.m2()).start();
    }
}
