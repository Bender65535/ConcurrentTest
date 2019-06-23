package pool.future;

import java.util.concurrent.*;


/**
 * 线程池生命周期:
 *      开始:创建线程池
 *      结束:jvm关闭或调用shutdown方法并处理完所有的任务
 */
public class Test1 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService service= Executors.newFixedThreadPool(1);
        Future<String> future= service.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(500);
                System.out.println("hello");
                return Thread.currentThread().getName()+" - test executor";
            }
        });
        System.out.println(future);
        System.out.println(future.isDone());  //查看现场是否结束,任务是否完成,call方法是否执行结束

        System.out.println(future.get());    //获取call方法的返回值
        System.out.println(future.isDone());

//        service.shutdown();
    }
}
