package threadlocal;

/**
 * 使用ThreadLocal
 * 
 * JDK的bin目录下的 jvisualvm.exe可以查看java运行时的内存情况
 */
public class UseThreadLocal {
    public static ThreadLocal<Integer> threadLocal=new ThreadLocal<>();

    /**
     * 运行3个线程
     */
    public void startThreadArray(){
        Thread[] runs=new Thread[3];
        for (int i = 0; i < runs.length; i++) {
            runs[i]=new Thread(new TestThread(i));
        }
        for (int i = 0; i < runs.length; i++) {
            runs[i].start();
        }
    }

    /**
     * 类说明:打印出赋予的id的值,应该包含0,1,2
     */
    public static class TestThread implements Runnable{
        int id;

        public TestThread(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            threadLocal.set(id);
            System.out.println(Thread.currentThread().getName()
            +" : "+threadLocal.get());
        }
    }

    public static void main(String[] args) {
        UseThreadLocal test=new UseThreadLocal();
        test.startThreadArray();
    }
}
