package sync;

import java.util.concurrent.TimeUnit;

/**
 * 脏读问题
 *
 * 同步方法只能保证当前方法的原子性,不能保证多个业务方法之间的互相访问的原子性
 * 注意在商业开发中,多个方法要求结果访问原子操作,需要多个方法都加锁,且锁定一个资源
 *
 * 一般来说,商业项目中,不考虑业务逻辑上的脏读问题
 */
public class Test5 {
    private double d=0.0;

    //set
    public synchronized void m1(double d){
        try {
            //相当于复杂的业务逻辑代码
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.d=d;
    }

    //get
    public double m2(){
        //在另外一个线程延时时去访问变量,出现脏读
        return d;
    }

    public static void main(String[] args) throws InterruptedException {
        final Test5 t=new Test5();

        new Thread(() -> t.m1(100)).start();

        System.out.println(t.m2());
        TimeUnit.SECONDS.sleep(3);

        System.out.println(t.m2());
    }
}
