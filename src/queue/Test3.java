package queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue
 * 有界容器
 * 当容量不足的时候,有阻塞能力
 *
 * add:在容量不足时,抛出异常
 * put:在容量不足时,阻塞等待消费
 * offer:
 *       单参数:不阻塞,容量不足的时候,返回false,当前新增数据放弃
 *       三参数方法:容量不足时,阻塞times时长,如果在阻塞时长内,有容量空闲,新增数据返回true
 *                  如果阻塞时长范围内,无容量空闲,放弃新增数据,返回false
 */
public class Test3 {
    final BlockingQueue<String> queue=new ArrayBlockingQueue<>(3);

    public static void main(String[] args) {
        final Test3 t=new Test3();

        for (int i = 0; i < 5; i++) {
//            System.out.println("add method:"+t.queue.add("value"+1));

//            try {
//                t.queue.put("put"+i);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("put method:"+i);

//            System.out.println("offer method:"+t.queue.offer("value"+i));

            try {
                System.out.println("offer method:"+
                        //参数2:阻塞时长,参数3:时间单位
                        t.queue.offer("value"+i,1, TimeUnit.SECONDS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
