package container.list;

import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.CountDownLatch;

/**
 * CopyOnWriteList
 *
 * 写操作效率低(除非删除的是尾数据),读操作效率高
 * 用空间来实现线程安全机制
 * 每增加一个数据创建一个数组
 * 读不到最新的数据(幻读),但是线程数据是安全
 */
public class Test1 {
    public static void main(String[] args) {
//        final List<String> list=new ArrayList<>();

        /**
         * 底层使用synchronized
         */
        final List<String> list=new Vector<>();
//        final List<String> list=new CopyOnWriteArrayList<>();

        final Random r=new Random();
        Thread[] array=new Thread[100];
        final CountDownLatch latch=new CountDownLatch(array.length);

        long begin=System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i]=new Thread(()->{
                for(int j=0;j<1000;j++){
                    list.add("value"+r.nextInt(10000));
                }
                latch.countDown();
            });
        }

        for(Thread t:array){
            t.start();
        }
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long end=System.currentTimeMillis();
        System.out.println("执行时间为:"+(end-begin)+"毫秒");
        System.out.println("size:"+list.size());
    }
}
