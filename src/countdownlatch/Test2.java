package countdownlatch;

import java.util.concurrent.CountDownLatch;

/**
 * 练习:
 * 自定义容器,提供新增元素(add)和获取元素数量(size)方法.
 * 启动两个线程,线程1向容器中新增10个数据,线程2监听容器元素数量,当容器元素数量为5时,线程2输出信息并终止
 */
public class Test2 {
    static int count;
    public static void main(String[] args) {
        final CountDownLatch latch=new CountDownLatch(1);
        new Thread(()->{
            if(count!=5){
                try {
                    latch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("end:"+count);
        }).start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                count++;
                System.out.println("count:"+count);

                if(count==5){
                    latch.countDown();
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
