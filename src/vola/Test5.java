package vola;

/**
 * 指令重排验证
 */
public class Test5 {
    int a = 0;
    boolean flag = false;

    public void method1() {
        a = 1;
        flag = true;
    }

    public void method2() {
        if (flag) {
            a = a + 5;
            System.out.println("************retValue:" + a);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Test5 t=new Test5();
        for (int i = 0; i < 10; i++) {
            new Thread(() -> t.method1()).start();
            new Thread(() -> t.method2()).start();
        }
    }
}
