package reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 尝试打断锁
 *
 * 阻塞状态:包括普通阻塞,等待队列,锁池队列
 * 普通阻塞:sleep(),可以被打断(thread.interrupt()打断阻塞状态,抛出异常)
 * 等待队列:wait()方法被调用,也是一种阻塞状态,只能由notify唤醒,无法打断
 * 锁池队列:无法获取锁标记,不是所有的锁池队列都能被打断
 *
 *  使用ReentrantLock的lock方法,获取锁标记的时候,如果需要阻塞等待锁标记,无法被打断
 *  使用ReentrantLock的lockInterruptibly方法,获取锁标记的时候,如果需要阻塞等待,可以被打断
 *
 *  synchronize使用的是另一种锁机制
 */
public class Test3 {
    Lock lock=new ReentrantLock();

    void m1(){
        try{
            lock.lock();
            for (int i = 0; i < 5; i++) {
                Thread.sleep(1000);
                System.out.println("m1() method"+i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    void m2(){
        try {
            //可尝试打断锁,阻塞等待锁,可以被其他线程打断阻塞状态
            lock.lockInterruptibly();
            System.out.println("m2() method");
        } catch (InterruptedException e) {
            System.out.println("m2() method is interrupted");
        }finally {
            try {
                lock.unlock();
            }catch (Exception e){

            }
        }
    }

    public static void main(String[] args) {
        Test3 t=new Test3();
        new Thread(()->t.m1()).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Thread t2= new Thread(()->t.m2());
        t2.start();
        try {
            Thread.sleep(1000);
            //可以被打断的一定会抛出InterruptedException
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();  //打断线程休眠,打断t2
        //非正常结束阻塞状态的线程,都会抛出异常
    }
}
