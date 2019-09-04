package vola;

/**
 * volatile没有原子性的证明
 *
 * why?
 * num++在jvm中分为3个步骤
 * 1.获得值
 * 2.+1
 * 3.将数写回内存
 *
 * 当写回内存时,有可能线程被挂起,无法接受到数字被修改的通知
 */
public class Test2 {
    volatile static int num = 0;

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    num++;
                }
            }).start();
        }

        Thread.sleep(2000);
        //如果volatile真的有原子性,则结果为20000
        System.out.println(num);
    }

}
