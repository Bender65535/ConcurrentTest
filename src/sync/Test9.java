package sync;

/**
 * 商业开发中尽量避免使用同步方法,尽量使用同步代码块
 * 细粒度解决同步问题,可以提高效率
 */
public class Test9 {
    //不建议使用
    synchronized void m1(){
        System.out.println("同步逻辑");
    }

    void m2(){
        //前置逻辑
        synchronized (this){
            System.out.println("同步逻辑");
        }
        //后置逻辑
    }


}
