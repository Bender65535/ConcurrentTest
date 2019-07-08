package cas;

public class Test1 {
    public static void main(String[] args) {
        final CompareAndSwap cas=new CompareAndSwap();
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                int expectedValue=cas.get();
                boolean b=cas.compareAndSet(expectedValue,(int)(Math.random()*101));
                System.out.println(b);
            }).start();
        }
    }
}

class CompareAndSwap{
    private int value;
    public synchronized int get(){
        return value;
    }

    //比较并交换
    public synchronized int compareAndSwap(int expectedValue,int newValue){
        //取出内存值
        int oldValue=value;

        //如果内存值和预估值一样,那就替换
        if (oldValue == expectedValue) {
            this.value=newValue;
        }
        return oldValue;
    }

    //设置
    public synchronized boolean compareAndSet(int expectedValue,int newValue){
        //预估值与内存值相同(双重比较,查看交换是否成功)
        return expectedValue==compareAndSwap(expectedValue,newValue);
    }
}