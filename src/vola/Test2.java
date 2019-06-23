package vola;

import java.util.ArrayList;
import java.util.List;

/**
 * volatile只能保证可见性,不能保证原子性
 * 不是加锁问题,只是内存数据可见
 */
public class Test2 {
    volatile int count=0;
    synchronized void m(){
        for (int i = 0; i < 10000; i++) {
            count++;
        }
    }

    public static void main(String[] args) {
        final Test2 t=new Test2();
        List<Thread> threads=new ArrayList<>();
        for(int i=0;i<10;i++){
            threads.add(new Thread(()->t.m()));
        }
        for(Thread thread:threads){
            thread.start();
        }
        for(Thread thread:threads){
            try {
                //将线程的开始和结束连在一起,所有线程结束后才向下执行
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        System.out.println(t.count);
    }
}
