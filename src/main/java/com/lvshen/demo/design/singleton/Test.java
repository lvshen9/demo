package com.lvshen.demo.design.singleton;

/**
 * Description:单列模式场景：
 * 在应用场景中，某类只要求生成一个对象的时候，如一个班中的班长、每个人的身份证号等。
 * 当对象需要被共享的场合。由于单例模式只允许创建一个对象，共享该对象可以节省内存，并加快对象访问速度。如 Web 中的配置对象、数据库的连接池等。
 * 当某类需要频繁实例化，而创建的对象又频繁被销毁的时候，如多线程的线程池、网络连接池等。
 *
 * @author yuange
 * @version 1.0
 * @date: 2019/12/30 10:14
 * @since JDK 1.8
 */
public class Test {
    @org.junit.Test
    public void test(){
        LazySingleton instance = LazySingleton.getInstance();
        LazySingleton instance1 = LazySingleton.getInstance();

        System.out.println(instance == instance1);
    }
}
