package com.lvshen.demo.serializable;

import com.lvshen.demo.memoryleak.User;

import java.io.*;

/**
 * Description:
 *
 * @author Lvshen
 * @version 1.0
 * @date: 2020-9-4 10:57
 * @since JDK 1.8
 */
public class Test {
    private static void serialize(UserInfo user) throws Exception {
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("F:\\article\\lvshen.txt")));
        oos.writeObject(user);
        oos.close();
    }

    private static UserInfo deserialize() throws Exception{
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("F:\\article\\lvshen.txt")));
        return (UserInfo) ois.readObject();
    }

    @org.junit.Test
    public void test() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.setName("Lvshen");
        userInfo.setHobby("看书");
        System.out.println("序列化前：" + userInfo);

        //Test.serialize(userInfo);

        UserInfo deserializeUserInfo = deserialize();
        System.out.println("反序列化后：" + deserializeUserInfo);
    }
}
