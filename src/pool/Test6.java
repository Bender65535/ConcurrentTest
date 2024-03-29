package pool;

import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool
 * 分支合并
 * 分支合并线程池(mapduce类似的设计思想).适合用于处理复杂任务
 * 初始化线程容量与cpu核心数相关
 * 线程池中运行的内容必须是ForkJoinTask的子类型(RecursiveTask,RecursiveAction)
 * ForkJoinPool  分支合并线程池,可以递归完成复杂任务,要求可分支合并的任务必须是ForkJoinTask类型的子类型
 * 其中提供了分支合并能力,ForkJoinTask类型提供了两个抽象子类型,RecursiveTask有返回结果的分支合并任务
 * RecursiveAction无返回结果的分支合并任务(Callable/Runnable)
 *
 *
 * ForkJoinPool没有所谓的容量,默认都是1个线程,根据任务自动地分支新的子线程,当子线程任务结束后,自动合并,所谓自动是根据fork和join两个方法实现的
 * 空间换时间
 *
 * compute是任务的执行逻辑
 *
 * 应用:主要是科学计算或天文计算的,数据分析的
 */
public class Test6 {
    final static int[] numbers=new int[1000000];
    final static int MAX_SIZE=50000;
    final static Random r=new Random();

    static {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i]=r.nextInt(1000);
        }
    }

    static class AddTask extends RecursiveTask<Long>{
        int begin,end;

        public AddTask(int begin, int end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if((end-begin)<MAX_SIZE){
                long sum=0L;
                for (int i = begin; i <end ; i++) {
                    sum+=numbers[i];
                }
                return sum;
            }else {
                //大于5w开始分支
                int middle=begin+(end-begin)/2;
                AddTask task1=new AddTask(begin,middle);
                AddTask task2=new AddTask(middle,end);
                task1.fork();   //开启新的任务,就是分支工作,就是开启一个线程任务
                task2.fork();

                //join  合并,将任务结果获取,阻塞方法,一定会得到结果数据
                return task1.join()+task2.join();
            }
        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        long result=0L;
        for (int i = 0; i < numbers.length; i++) {
            result+=numbers[i];
        }
        System.out.println(result);

        ForkJoinPool pool=new ForkJoinPool();
        AddTask task=new AddTask(0,numbers.length);

        Future<Long> future=pool.submit(task);
        System.out.println(future.get());
    }
}
