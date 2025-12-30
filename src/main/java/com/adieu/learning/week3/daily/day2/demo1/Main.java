package main.java.com.adieu.learning.week3.daily.day2.demo1;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Main {
    /**
     * 测试File的一些方法
     * @param args
     */
//    public static void main(String[] args) throws IOException {
//        File file = new File("C:\\Users\\Adieu\\Desktop\\study exercise");
//        if(file.exists()){
//            System.out.println("文件存在");
//        }else{
//            System.out.println("文件不存在");
//        }
//        if(file.isFile()){
//            System.out.println("文件");
//        }else{
//            System.out.println("不是文件");
//        }
//        if(file.isDirectory()){
//            System.out.println("文件夹");
//        }else{
//            System.out.println("不是文件夹");
//        }
//        System.out.println(file.length());
//        File file = new File("test.txt");
//        if (file.createNewFile()) {
//            System.out.println("文件创建成功");
//        } else {
//            System.out.println("文件已存在");
//        }
//        String workingDir = System.getProperty("user.dir");
//        System.out.println("当前工作目录是: " + workingDir);
//        File file = new File("aa");
//        if(file.mkdirs()){
//            System.out.println("文件夹创建成功");
//        }else{
//            System.out.println("文件夹已存在");
//        }
//        File file = new File("src/main/java/com/adieu/learning/week3/daily");
//        File[] files = file.listFiles();
//        if(files != null){
//            for(File f : files){
//                System.out.println(f.getName());
//            }
//        }
//    }

    /**
     * 测试字节流的常用方法
     */
//    public static void main(String[] args) throws IOException {
//        copy1("test.txt", "test2.txt");
//        copy2("test.txt", "test2.txt");
//        long time1,time2;
//        long start = System.nanoTime();
//        copy2("test.txt", "test2.txt");
//        System.out.println("耗时：" + (time1 = (System.nanoTime() - start)));
//        start = System.nanoTime();
//        copy1("test.txt", "test2.txt");
//        System.out.println("耗时：" + (time2 = (System.nanoTime() - start)));
//        System.out.println(time2 * 1.0/ time1 + "倍" );
//    }

    /**
     * 带缓冲复制，使用缓冲流
     * @param src
     * @param dest
     * @throws IOException
     */

//    private static final int BUFFER_SIZE = 4096;
//    public static void copy2(String src,String dest) throws IOException {
//        try(InputStream in = new BufferedInputStream(new FileInputStream(src), BUFFER_SIZE);
//            OutputStream out = new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE)){
//            byte[] buffer = new byte[BUFFER_SIZE];
//            int len;
//            while((len = in.read(buffer)) != -1){
//                out.write(buffer, 0, len);
//            }
//        }
//
//    }
    /**
     * 无缓冲复制，使用节点流
     * @param src
     * @param dest
     * @throws IOException
     */
//    public static void copy1(String src, String dest) throws IOException {
//        try(InputStream in = new FileInputStream(src);
//            OutputStream out = new FileOutputStream(dest)){
//            int len;
//            while((len = in.read()) != -1){
//                out.write(len);
//            }
//        }
//    }

    /**
     * 测试字符流的常用方法
     */
//    public static void main(String[] args) throws IOException {
//        copy1("test.txt", "test2.txt");
//        read("test.txt");
//        write("test.txt", "666");
//    }

    /**
     * 采用节点流进行字符复制
     * @param src
     * @param dest
     * @throws IOException
     */
//    public static void copy1(String src,String dest) throws IOException {
//        try(Reader in = new FileReader(src);
//            Writer out = new FileWriter(dest)){
//            int len;
//            while((len = in.read()) != -1){
//                out.write(len);
//            }
//        }
//    }

    /**
     * 带缓冲的字符写入
     * @param dest
     * @param content
     * @throws IOException
     */
//    public static void write(String dest,String content) throws IOException {
//        try(BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(dest),StandardCharsets.UTF_8))){
//            bw.write(content);
//            bw.newLine();
//        }
//    }

    /**
     * 将字节流转换为字符流读取
     * @param src
     * @throws IOException
     */
//    public static void read(String src) throws IOException {
//        try(BufferedReader br = new BufferedReader(
//                new InputStreamReader(new FileInputStream(src),StandardCharsets.UTF_8))){
//            String line;
//            if((line = br.readLine())!= null){
//                System.out.println(line);
//            }
//        }
//    }

}
