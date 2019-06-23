import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者消费者
 * 重入锁&条件
 * 条件 - Condition, 为Lock增加条件,当条件满足时,做什么事情,如加锁或解锁,等待或唤醒
 */
public class Test2<E> {
    private final LinkedList<E> list=new LinkedList<>();
    private final int MAX=10;
    private int count=0;

    private Lock lock=new ReentrantLock();
    private Condition producer=lock.newCondition();
    private Condition consumer=lock.newCondition();

    public int getCount(){
        return count;
    }

    public void put(E e){
        lock.lock();
        try {
            while (list.size()== MAX){
                System.out.println(Thread.currentThread().getName()+"等待...");
                //进入等待队列,释放锁标记
                //借助条件,进入的等待队列
                producer.await();
            }
            System.out.println(Thread.currentThread().getName()+"put ...");
            list.add(e);
            count++;
            //借助条件,唤醒所有消费者
            consumer.signalAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
    public E get(){
        E e=null;
        lock.lock();
        try {
            while (list.size()==0){
                System.out.println(Thread.currentThread().getName()+"等待...");
                consumer.await();
            }
            System.out.println(Thread.currentThread().getName()+"get...");
            e=list.removeFirst();
            count--;
            producer.signalAll();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }finally {
            lock.unlock();
        }
        return e;
    }

    public static void main(String[] args) {
        final Test2<String> c=new Test2<>();
        for (int i = 0; i < 10; i++) {
            //消费者
            new Thread(()->{
                for(int j=0;j<5;j++){
                    System.out.println(c.get());
                }
            },"consumer"+i).start();
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 2; i++) {
            //生产者
            new Thread(()->{
                for(int j=0;j<25;j++){
                    c.put("container value"+j);
                }
            },"producer"+i).start();
        }
    }
}
