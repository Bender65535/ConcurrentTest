package atom;

import java.util.concurrent.atomic.AtomicReference;

/**
 * CAS ---> UnSafe --->CAS底层思想 ---> ABA --->原子引用更新 --->如何规避ABA问题
 * AtomicInteger的ABA问题
 * 线程1和线程2竞争资源,分别把变量A保存到自己的线程空间,线程2运行速度快,将A修改为B,再将B修改为A
 * 线程1发现期望值与预期值相同,对A进行操作,尽管整体上看并没有问题,但是不代表这个过程就是没有问题的
 *
 * 狸猫换太子
 * 如果变量A是引用类型,线程2在ABA时对内容进行操作,那就回引发问题
 *
 * 解决方案:原子引用 AtomicReference / 版本号(乐观锁):AtomicStampedReference
 */
public class Test3 {
    public static void main(String[] args) {
        User z3=new User("z3",22);
        User l4=new User("l4",23);

        AtomicReference<User> atomicReference=new AtomicReference<>();
        System.out.println(atomicReference.compareAndSet(z3,l4)+"\t"+atomicReference.get().toString());
        System.out.println(atomicReference.compareAndSet(z3,l4)+"\t"+atomicReference.get().toString());
    }
}

class User{
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
