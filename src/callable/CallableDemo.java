package callable;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class CallableDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //Thread的构造参数只能传Runnable
//        Thread t1=new Thread();
//        t1.start();

        FutureTask futureTask=new FutureTask(new MyThread());
        Thread t1=new Thread(futureTask,"aaa");
        //一个futureTask只执行一次,如果要执行多次,要创建多个futureTask
        Thread t2=new Thread(futureTask,"bbb");
        t1.start();

        //get方法要求获得Callable线程的计算结果,如果没有完成计算,就会导致阻塞
        //get方法建议放在最后
        System.out.println("result:"+futureTask.get());
    }
}

class MyThread implements Callable<Integer>{

    /**
     * call与run的区别:
     * call会抛异常,有返回值
     * @return
     * @throws Exception
     */
    @Override
    public Integer call() throws Exception {
        System.out.println("*********come in Callable");
        Thread.sleep(3000);
        return 1024;
    }
}