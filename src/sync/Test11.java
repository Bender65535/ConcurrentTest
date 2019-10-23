package sync;

/**
 * 常量问题
 * 在定义同步代码块时,不要使用常量池里的对象作为锁对象(常量池在方法区中)
 *
 * 运行时方法区中存放的是字节码(代码执行时的机器码,包含了逻辑与数据,数据包含数据和引用数据)
 * 真实数据为8种基本数据,直接占用字节保存
 *
 *
 */
public class Test11 {
    //s1,s2为同一个对象
    String s1="hello";
    String s2="hello";
//    String s2=new String("hello");//new 关键字,一定是在堆中创建一个新的对象

    Integer i1=new Integer(1);  //当小于-127 大于128时,放入常量池中
    Integer i2=new Integer(1);

    void m1(){
        synchronized (s1){
            try {
                //t1释放锁
                s1.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("m1()");
            while(true){

            }
        }
    }

    void m2(){
        //t2获取锁
        synchronized (s2){
            //唤醒t1线程
            //但是t1被唤醒后,"hello"对象的偏向锁指向的是t2线程,所以当t1被唤醒执行m1时,获取不到锁
            s2.notifyAll();
            System.out.println("m2()");
            while(true){

            }
        }
    }

    public static void main(String[] args) {
        final Test11 t=new Test11();
        new Thread(()->t.m1()).start();
        new Thread(()->t.m2()).start();

    }
}
