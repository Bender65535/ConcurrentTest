package container.concurrentmap;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 * 解决并发情况下的容器线程安全问题,给多线程环境准备一个线程安全的容器对象
 * 都是使用synchronized方法实现的
 *
 * concurrent包中的同步容器,大多是使用系统底层技术实现的线程安全,类似native,java8中使用cas
 * key与value不能为null
 */
public class Test1 {
    public static void main(String[] args) {
//        final Map<String,Object> map=new HashMap<>();   //效率极低,不推荐使用
//        final Map<String,Object> map=new Hashtable<>();

        /**
         * 底层哈希实现的同步map(set).效率高,线程安全.使用系统底层技术实现线程安全
         * 量级较synchronized低
         */
        final Map<String,Object> map=new ConcurrentHashMap<>();

        /**
         * 底层跳表实现的同步map(set).有序,效率比ConcurrentHashMap稍低
         */
//        final Map<String,Object> map=new ConcurrentSkipListMap<>();  //效率慢,但是有排序

        final Random r=new Random();
        Thread[] array=new Thread[100];
        final CountDownLatch latch=new CountDownLatch(array.length);

        long begin=System.currentTimeMillis();
        for (int i = 0; i < array.length; i++) {
            array[i]=new Thread(()->{
                for (int j=0;j<10000;j++){
                    map.put("key"+r.nextInt(10000),"value"+r.nextInt(10000));
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
    }
}
