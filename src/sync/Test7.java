package sync;

/**
 * 同步方法-继承
 * 子类同步方法覆盖父类同步方法,可以指定调用父类的同步方法
 * 相当于重入
 */
public class Test7 {
    synchronized void m(){
        System.out.println("super class m start");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("super class m end");

    }

    static class SubTest extends Test7{
        synchronized void m(){
            System.out.println("sub class m start");
            super.m();
            System.out.println("sub class m end");
        }
    }

    public static void main(String[] args) {
        new SubTest().m();
    }
}
