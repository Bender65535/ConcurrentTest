package atom;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS是什么? ==>compareAndSet 比较并交换
 * 底层原理: 自旋锁,Unsafe类
 *
 * Unsafe能像C的指针一样直接操作内存
 *
 * public final int getAndAdd(int delta) {
 *         return unsafe.getAndAddInt(this, valueOffset, delta);
 * }
 * this:对象本身
 * valueOffset:内存偏移量
 * delta:增长数
 *
 * CAS的全称为Compare-And-Swap,它是一条CPU并发原语
 * 它的功能是判断内存某个位置的值是否为预期值,如果是则更改为新的值,这个过程是原子的
 *
 * CAS并发原语提现在JAVA语言中就是sun.misc.Unsafe类中的各个方法.调用UnSafe类汇总的CAS方法,
 * JVM会帮我们实现出CAS汇编指令,这是一种完全依赖于硬件的功能,通过它实现了原子操作,
 * 由于CAS是一种系统原语,原语属于操作系统用于范畴,是由若干指令组成的,用于完成某个功能的一个过程,
 * 并且原语的执行必须是连续的,在执行过程中不允许被中断,也就是说CAS是一条CPU的原子指令,不会造成所谓的数据不一致问题
 *
 * CAS的缺点:
 * 1.循环时间长,开销大(如果CAS失败,会一直进行尝试,如果CAS长时间一直不成功,可能会给CPU带来很大的开销)
 * 2.只能保证一个共享变量的原子操作(对多个共享变量操作时,循环CAS就无法保证操作的原子性,这个时候就可以用锁来保证原子性)
 * 3.ABA问题
 */
public class Test2 {
    public static void main(String[] args) {
        //拿回线程的快照是5
        AtomicInteger atomicInteger=new AtomicInteger(5);
        //期望写回内存前,这个数字还是5,
        // 如果期望值和快照是一样的,说明主内存的值没有没人动过
        System.out.println(atomicInteger.compareAndSet(5, 2019)+"\t current data:" + atomicInteger.get());
        System.out.println(atomicInteger.compareAndSet(5, 2014)+"\t current data:" + atomicInteger.get());
    }
}
