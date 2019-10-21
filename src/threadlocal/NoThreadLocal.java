package threadlocal;

/**
 * 涉及到java内存模型
 */
public class NoThreadLocal {

    public static int count;

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
            count=id;  //变量赋值给变量不是原子性操作
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
            System.out.println(Thread.currentThread().getName()
                    +" : "+count);
        }
    }
    public static void main(String[] args) {
        NoThreadLocal test=new NoThreadLocal();
        test.startThreadArray();
    }
}
