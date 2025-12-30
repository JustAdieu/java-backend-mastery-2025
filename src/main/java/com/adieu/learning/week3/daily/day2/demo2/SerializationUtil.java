package main.java.com.adieu.learning.week3.daily.day2.demo2;

import java.io.*;

public class SerializationUtil {

    public static void main(String[] args){
        try {

            User originalUser = new User("Adieu", 18, "12345678");
            serialize(originalUser, "test.txt");
            User deserializedUser  = (User) deserialize("test.txt");
            System.out.println("\n验证:");
            System.out.println("用户名是否一致? " + originalUser.getName().equals(deserializedUser.getName()));
            System.out.println("年龄是否一致? " +(originalUser.getAge() == deserializedUser.getAge()));
            System.out.println("密码是否被序列化? " + (deserializedUser.getPassword() == null)); // 应为 null
            System.out.println("静态字段值? " + deserializedUser.appName); // 来自当前类的状态
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Object序列化
     * @param obj
     * @param filePath
     * @throws IOException
     */
    public static void serialize(Object obj,String filePath) throws IOException {
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))){
            oos.writeObject(obj);
            System.out.println("序列化成功");
        }
    }

    /**
     * Object反序列化
     * @param filePath
     * @return Object
     * @throws IOException
     * @throws ClassNotFoundException
     */

    public static Object deserialize(String filePath) throws IOException, ClassNotFoundException {
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))){
            Object obj = ois.readObject();
            System.out.println("反序列化成功");
            return obj;
        }
    }
}
