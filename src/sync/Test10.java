package sync;

/**
 * 锁对象变更问题
 * 同步代码一旦加锁后,那么会有一个临时的锁引用执行锁对象,和真实的引用无直接关联
 * 在锁为释放之前,修改锁对象引用,不会影响同步代码块的执行
 *
 * 改变临界,线程1栈帧中的引用所指的对象不变,打印出来的o对象并不是栈帧中的,而是this对象中的
 * 栈帧中的锁对象是被锁定的
 *
 * 启动线程时会创建栈帧,并将锁的引用放在栈帧中(锁的是对象,不是引用),形成一个临时变量
 *
 * 栈帧弹栈时只会打断引用,但是主方法中的数据和堆中的数据不会回收
 */
public class Test10 {
    Object o=new Object();
    void m(){

        System.out.println(Thread.currentThread().getName()+"start");
        synchronized (o){
            while (true){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //t1这里打印的却是被修改后的o
                System.out.println(Thread.currentThread().getName()+" - "+o);
            }
        }

    }

    int i=0;
    int a(){
        try{
            /**
             * return i ->
             * int _returnValue=i; //0
             * return _returnValue;
             */
            return i;
        }finally{
            i=10;
        }
    }

    public static void main(String[] args) {
        final Test10 t=new Test10();
        new Thread(()->t.m(),"thread1").start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread t2=new Thread(()->t.m(),"thread2");


        //改变o的指向,修改锁对象引用,不会影响同步代码块的执行
        t.o=new Object();
        t2.start();

        System.out.println(t.i);   //0
        System.out.println(t.a()); //0
        System.out.println(t.i);   //10
    }

}
