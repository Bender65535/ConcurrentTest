package vola;

import java.util.concurrent.TimeUnit;

/**
 * cpu中的临时数据存储在cpu缓存中(cpu在计算时会开辟一个空间当做缓存)
 * 磁盘(class字节码)->内存(类对象,对象)->缓存(对象中的临时变量)
 * cpu运行是默认找缓存中的数据
 * cpu一有中断就"可能"清空缓存,缓存重新从内存中读取数据
 *              也可能不清空缓存(cpu只要不中断就永远不会清空缓存),仍旧使用缓存中的数据
 *
 * volatile改变的是内存特性
 * 当加了volatile,通知操作系统底层,cpu每次计算时,先查看内存中的有效性,保证最新的内存数据使用
 *
 * (线程栈帧是在内存中)
 *
 *
 * cpu通过线程1访问t1对象,将临时变量放入缓存中
 */
public class Test1 {
    /*volatile*/ boolean b=true;
    void m(){
        System.out.println("start");
        while (b){}
        System.out.println("end");
    }

    public static void main(String[] args) {
        final Test1 t=new Test1();
        new Thread(()->t.m()).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t.b=false;
    }
}
