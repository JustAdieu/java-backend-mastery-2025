package main.java.com.adieu.learning.week1.daily.day2.demo7;

import java.lang.reflect.*;

public class Main {
    /**
     * function:反射的演示
     * author:adieu
     * date:2025/12/9
     * @param args
     */
    public static void main(String[] args){
        /**
         * function:Class演示
         * author:adieu
         * date:2025/12/9
         */
//        User u = new User();
//        User u2 = new User("张三", 18);
//        Class c = u.getClass();
//        Class c2 = u2.getClass();
//        System.out.println(c.getName());
//        System.out.println(c2.getName());
//        System.out.println(c==c2);

//        System.out.println(void.class);//void
//        System.out.println(int.class);//void
//        System.out.println(User.class);//class demo7.User

//        Class c1 = User.class;//第一种获取方法
//        Class c2 = new User().getClass();//第二种获取方法
//        Class c3 = null;
//        try {
//            c3 = Class.forName("demo7.User");//第三种获取方法
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(c1);//class demo7.User
//        System.out.println(c2);//class demo7.User
//        System.out.println(c3);//class demo7.User

//        Class c1 = User.class;
//        try {
//            User u1 = (User)c1.getConstructor().newInstance();
//            System.out.println(u1);
//        } catch (InstantiationException e) {
//            throw new RuntimeException(e);
//        } catch (IllegalAccessException e) {
//            throw new RuntimeException(e);
//        } catch (InvocationTargetException e) {
//            throw new RuntimeException(e);
//        } catch (NoSuchMethodException e) {
//            throw new RuntimeException(e);
//        }
        /**
         * function：捕获异常演示
         * author：adieu
         * date：2025/12/9
         */
//        try {
//            Class u1 = Class.forName("demo7.Use");
//            System.out.println(u1);
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
        /**
         * function：反射获取类的信息
         * author：adieu
         * date：2025/12/9
         */
//        String name;
//        Scanner in = new Scanner(System.in);
//        System.out.println("Enter class name(java.util.Date):");
//        name = in.next();
//
//        System.out.println(Modifier.toString(int.class.getModifiers()));
//
//        try {
//            Class c = Class.forName(name);
//            Class superC = c.getSuperclass();
//            String modifiers = Modifier.toString(c.getModifiers());
//            if(modifiers.length()>0)System.out.print(modifiers+" ");
//            System.out.print("class "+c.getName()+" ");
//            if(superC!=null && superC!=Object.class){
//                System.out.print("extends "+superC.getName());
//            }
//            System.out.println();
//            System.out.println("{");
//            printConstructors(c);
//            System.out.println();
//            printMethods(c);
//            System.out.println();
//            printFields(c);
//            System.out.println("}");
//        } catch (ClassNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    private static void printFields(Class c){
//        Field[] fields = c.getDeclaredFields();
//        for(Field f : fields){
//            String modifiers = Modifier.toString(f.getModifiers());
//            System.out.print("    ");
//            if(modifiers.length()>0)System.out.print(modifiers+" ");
//            System.out.print(f.getType().getName()+" ");
//            System.out.println(f.getName()+";");
//        }
//    }
//
//    private static void printMethods(Class c){
//        Method[] methods = c.getDeclaredMethods();
//        for (Method m : methods) {
//            String modifiers = Modifier.toString(m.getModifiers());
//            System.out.print("    ");
//            if(modifiers.length()>0)System.out.print(modifiers+" ");
//            String returnType = m.getReturnType().getName();
//            System.out.print(returnType+" ");
//            System.out.print(m.getName()+"(");
//            Class[] paramTypes = m.getParameterTypes();
//            for(int i = 0;i < paramTypes.length;i++){
//                if(i>0)System.out.print(",");
//                System.out.print(paramTypes[i].getName());
//            }
//            System.out.println(");");
//        }
//    }
//
//    private static void printConstructors(Class c){
//        Constructor[] constructors = c.getDeclaredConstructors();
//        for (Constructor ctor : constructors) {
//            String modifiers = Modifier.toString(ctor.getModifiers());
//            System.out.print("    ");
//            if(modifiers.length()>0)System.out.print(modifiers+" ");
//            System.out.print(ctor.getName()+"(");
//
//            Class[] paramTypes = ctor.getParameterTypes();
//            for(int i = 0;i < paramTypes.length;i++){
//                if(i>0)System.out.print(",");
//                System.out.print(paramTypes[i].getName());
//            }
//            System.out.println(");");
//        }
//
        /**
         * function:运行时候利用反射分析对象
         * author:Adieu
         * Date:2025/12/9
         */
        User user = new User("张三",18);
        Class c = user.getClass();
        try {
            Field f = c.getDeclaredField("age");
            f.set(user,20);
            Object value = f.get(user);
            System.out.println(value);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }catch (IllegalAccessException e){
            throw new RuntimeException(e);
        }

    }

}
