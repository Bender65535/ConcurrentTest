package sync;


/**
 * 同步方法和非同步方法的调用
 *
 * 同步方法只影响其他线程调用同一个同步方法时的锁问题,不影响其他线程调用非同步方法,或调用其他锁资源的同步方法
 * 也不影响临界资源
 */
public class Test4 {
    Object o=new Object();
    public synchronized void m1() {
        System.out.println("public synchronized void m1() start");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("public synchronized void m1() end");
    }

    public void m2(){
        System.out.println("public synchronized void m2() start");
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("public synchronized void m2() end");
    }

    public void m3(){
        synchronized (o){
            System.out.println("public synchronized void m3() start");
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("public synchronized void m3() end");
        }
    }

    public static class Mythread01 implements Runnable{
        int i;
        Test4 t;
        public Mythread01(int i,Test4 t){
            this.i=i;
            this.t=t;
        }

        @Override
        public void run() {
            if(i==0){
                t.m1();
            }else if(i>0){
                t.m2();
            }else {
                t.m3();
            }
        }
    }

    public static void main(String[] args) {
        Test4 t=new Test4();
        new Thread(new Test4.Mythread01(0,t)).start();
        new Thread(new Test4.Mythread01(1,t)).start();
        new Thread(new Test4.Mythread01(-1,t)).start();
    }

}
