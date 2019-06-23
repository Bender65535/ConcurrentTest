import java.util.LinkedList;

/**
 * 生产者消费者
 * wait&notify
 * wait/notify都是和while配合应用的,可以避免多线程并发判断逻辑失效问题
 */
public class Test1<E> {
    private final LinkedList<E> list=new LinkedList<>();
    private final int MAX=10;
    private int count=0;

    public synchronized int getCount(){
        return count;
    }

    public synchronized void put(E e){
        while (list.size()== MAX){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        list.add(e);
        count++;
        this.notifyAll();
    }

    public synchronized E get(){
        E e=null;
        while (list.size()==0){
            try {
                this.wait();
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
        e=list.removeFirst();
        count--;
        this.notifyAll();
        return e;
    }

    public static void main(String[] args) {
        final Test1<String> c=new Test1<>();
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
