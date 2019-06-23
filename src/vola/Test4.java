package vola;

/**
 * 单例模式中的指令重排序
 */
public class Test4 {
    private static volatile Test4 INSTANCE;

    private Test4(){

    }

    public static Test4 getInstance(){
        //jvm可能进行指令重排序,导致instance为半初始化状态
        if(INSTANCE==null){
            //synchronized防止指令重排,加在Test4上表示防止Test4中发生指令重排
            synchronized (Test4.class){
                if(INSTANCE==null){
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //指令重排序得到的对象为0
                    INSTANCE=new Test4();
                }
            }
        }
        return INSTANCE;
    }

    public void m(){
        System.out.println("m");
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            new Thread(()-> System.out.println(Test4.getInstance().hashCode()));
        }
    }

}
