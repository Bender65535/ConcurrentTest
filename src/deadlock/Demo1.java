package deadlock;

/**
 *  死锁
 */
public class Demo1  {


    static class LockHolder implements Runnable{
        private String A;
        private String B;

        public LockHolder(String a, String b) {
            A = a;
            B = b;
        }

        @Override
        public void run() {
            //这时候双方都持有各自的锁
            synchronized (A){
                System.out.println(Thread.currentThread().getName()+"自己拥有锁"+A+",尝试获取锁"+B);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //这时候双方都想获取对方的锁
                synchronized (B){

                    System.out.println(Thread.currentThread().getName()+"自己拥有锁"+B+":,尝试获取锁"+A);
                }
            }
        }
    }

    public static void main(String[] args) {
        String A="AAA";
        String B="BBB";
        new Thread(new LockHolder(A,B),"ThreadA").start();
        new Thread(new LockHolder(B,A),"ThreadB").start();

        //查看java进程的命令: jps -l
        //查看进程是否死锁  jstack 9636   9636为进程id
    }
}
