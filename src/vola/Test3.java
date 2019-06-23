package vola;

/**
 * 练习:
 * 自定义容器,提供新增元素(add)和获取元素数量(size)方法.
 * 启动两个线程,线程1向容器中新增10个数据,线程2监听容器元素数量,当容器元素数量为5时,线程2输出信息并终止
 */
public class Test3 {
    volatile int count=0;
    public void add(){
        for (int i = 0; i < 10; i++) {
            count++;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count);
        }
    }
    public void size(){
        while(true){
            if(count==5){
                System.out.println("end,count="+count);
                break;
            }
        }
    }


    public static void main(String[] args) {
        Test3 t=new Test3();
        new Thread(()->t.add()).start();
        new Thread(()->t.size()).start();
    }
}
