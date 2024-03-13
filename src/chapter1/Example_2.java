package chapter1;

import java.io.*;
import java.util.Random;

/**
 * @description: 测试多线程和单线程拷贝一个文件夹
 * @author: sakana
 * @date: 2024/3/13 15:13
 * @version: 1.0
 */

public class Example_2 {
    //文件夹路径
    private static final String source = "D:/Tencent/QQNT/download";

    private static final String dest = "D:\\Code";

    public static void main(String[] args) throws InterruptedException {
        long singleStart = System.currentTimeMillis();
        single(source, dest);
        long singleEnd = System.currentTimeMillis();
        long multiStart = System.currentTimeMillis();
        multi(source, dest);
        long multiEnd = System.currentTimeMillis();
        System.out.println("单线程拷贝耗时：" + (singleEnd - singleStart) + "ms");
        System.out.println("多线程拷贝耗时：" + (multiEnd - multiStart) + "ms");
    }

    //单线程拷贝
    public static void single(String source, String dest) {
        File sf = new File(source);
        //是文件夹
        if (sf.isDirectory()) {
            //在目标路径创建文件夹
            File df = new File(dest + "/" + sf.getName() + new Random().nextInt());
            df.mkdir();
            //拿到源路径问价夹下所有文件
            for (File file : sf.listFiles()) {
                single(file.getPath(), df.getPath());
            }
        } else {
            //不是文件夹
            try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(source));
                 BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(dest + "/" + new File(source).getName()))) {
                bis.transferTo(bos);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        }
    }

    //多线程拷贝
    public static void multi(String source, String dest) throws InterruptedException {
        File sf = new File(source);
        //在目标路径创建文件夹
        File df = new File(dest + "/" + sf.getName() + new Random().nextInt());
        df.mkdir();
        //拿到源路径问价夹下所有文件
        for (File file : sf.listFiles()) {
            // single(file.getPath(), df.getPath());
            Thread thread = new Thread(new copy(df.getPath(), file.getPath()));
            thread.start();
            thread.join();
        }
    }


    static class copy implements Runnable {
        private String dest;
        private String source;

        public copy(String dest, String source) {
            this.dest = dest;
            this.source = source;
        }

        @Override
        public void run() {
            single(source, dest);
        }
    }
}
