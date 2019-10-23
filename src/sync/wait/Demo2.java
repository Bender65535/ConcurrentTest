package sync.wait;

import java.util.*;

public class Demo2 {
    final static LinkedList<Control> CONTROLS=new LinkedList<>();

    private final static int MAX_WORKER=5;

    public static void main(String[] args) {

        List<Thread> worker=new ArrayList<>();
        Arrays.asList("M1","M2","M3","M4","M5","M6","M7","M8","M9","M10").stream()
                .map(Demo2::createCaptureThread)
                .forEach(t->{
                    //开启所有线程
                    t.start();
                    worker.add(t);
                });
        worker.stream().forEach(t->{
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Optional.of("all of capture work finished").ifPresent(System.out::println);
    }

    private static Thread createCaptureThread(String name){
        return new Thread(()->{
            Optional.of("The worker ["+Thread.currentThread().getName()+"]begin capture data")
                    .ifPresent(System.out::println);

            synchronized (CONTROLS){
                System.out.println(Thread.currentThread().getName()+"获取了锁");
                //当工人数量大于5时,当前的线程wait
                while (CONTROLS.size()>=MAX_WORKER ){
                    try {
                        //释放锁,让线程竞争
                        System.out.println(Thread.currentThread().getName()+" wait");
                        //当批次达到5的时候释放
                        CONTROLS.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                //添加工人
                CONTROLS.addLast(new Control());
                System.out.println("CONTROLS的数量"+CONTROLS.size());
            }

            Optional.of("The worker ["+Thread.currentThread().getName()+"] is working...")
                    .ifPresent(System.out::println);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            synchronized (CONTROLS){
                Optional.of("The worker["+Thread.currentThread().getName() + "] END capture data")
                        .ifPresent(System.out::println);
                //当一个工人结束工作后,唤醒所有wait的线程,让他们再次竞争
                CONTROLS.removeFirst();
                CONTROLS.notifyAll();
            }
        },name);
    }

    private static class Control{

    }
}
