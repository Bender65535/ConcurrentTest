package queue;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * ConcurrentLinkedDeque
 * 队列(fifo) - 链表实现的
 */
public class Test1 {
    public static void main(String[] args) {
        Queue<String> queue =new ConcurrentLinkedDeque<>();
        for (int i = 0; i < 10; i++) {
            queue.offer("value"+i);
        }
        System.out.println(queue);
        System.out.println(queue.size());

        //查看首数据
        System.out.println(queue.peek());
        System.out.println(queue.size());

        //获取首数据
        System.out.println(queue.poll());
        System.out.println(queue.size());
    }
}
