package main.java.com.adieu.learning.week3.daily.day2.demo3;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
    public static void main(String[] args){
        try {
            copyFileByStream("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
            copyFileByNIO("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
            copyFileByBuffer("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
            Long start = System.currentTimeMillis();
            copyFileByNIO("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
            System.out.println("耗时：" + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
            copyFileByStream("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
            System.out.println("耗时：" + (System.currentTimeMillis() - start));
            start = System.currentTimeMillis();
            copyFileByBuffer("C:\\Users\\Adieu\\Desktop\\1.jpg", "C:\\Users\\Adieu\\Desktop\\2.jpg");
            System.out.println("耗时：" + (System.currentTimeMillis() - start));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * 使用缓冲流复制文件
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFileByBuffer(String src,String dest) throws IOException {
        try(BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest))){
            byte[] buffer = new byte[4096];
            while((bis.read(buffer))!=-1){
                bos.write(buffer);
            }
        }
    }



    /**
     * 使用节点流复制文件
     * @param src
     * @param dest
     * @throws IOException
     */
    public static void copyFileByStream(String src,String dest) throws IOException {
        try(FileInputStream fis = new FileInputStream(src);
            FileOutputStream fos = new FileOutputStream(dest)){
            int len;
            while((len = fis.read())!=-1){
                fos.write(len);
            }

        }
    }

    /**
     * NIO复制文件
     * @param src
     * @param dest
     * @throws IOException
     */

    public static void copyFileByNIO(String src,String dest) throws IOException {
        try(FileInputStream fis = new FileInputStream(src);
            FileOutputStream fos = new FileOutputStream(dest);
            FileChannel inchannel = fis.getChannel();
            FileChannel outchannel = fos.getChannel()){
            ByteBuffer bb = ByteBuffer.allocate(4096);
            while(inchannel.read(bb)!=-1){
                bb.flip();
                outchannel.write(bb);
                bb.compact();
            }
        }
    }

}
