package sync.wait;

import java.util.stream.Stream;

/**
 * wait和sleep的区别
 * sleep是线程类的方法,wait是object的方法
 * sleep不会释放对象锁,wait会
 * sleep不需要在同步代码块中使用,wait必须在同步代码块中使用
 * wait需要被唤醒
 */
public class Demo1 {
    private final static Object LOCK= new Object();

    public static void m1(){
        synchronized (LOCK){
            try {
                System.out.println("The Thread"+Thread.currentThread().getName()+" enter");
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void m2(){
        synchronized (LOCK){
            try {
                System.out.println("The Thread"+Thread.currentThread().getName()+" enter");
                //放弃cpu执行权,进入block状态
                LOCK.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        //如果没有加同步代码块,wait会抛出异常
        //如果monitor对象不是LOCK,也会抛出异常
//        m2();

        Stream.of("T1","T2").forEach(name->{
            new Thread(name){
                @Override
                public void run() {
                    m2();
                }
            }.start();
        });

    }
}
