package sync;

/**
 * 练习:
 * 自定义容器,提供新增元素(add)和获取元素数量(size)方法.
 * 启动两个线程,线程1向容器中新增10个数据,线程2监听容器元素数量,当容器元素数量为5时,线程2输出信息并终止
 *
 * 生产者消费者模型
 */
public class Test12 {

    static int count = 0;

    public static void main(String[] args) {
        Object o=new Object();

        final Test12 t = new Test12();
        //线程1负责向容器中添加数据
        new Thread(() -> {
            synchronized (o){
                for (int i = 0; i < 10; i++) {
                    count++;
                    if (count == 5) {
                        o.notifyAll();
                        try {
                            //释放对象o
                            o.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println(count);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        //线程2监听容器中的元素
        new Thread(() -> {
            synchronized (o){
                if (count != 5) {
                    try {
                        //当前代码块阻塞,让出对象o
                        o.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("end,count=" + count);
                o.notifyAll();
            }
        }).start();

    }
}
