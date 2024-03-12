package chapter1;

/**
 * @description: 以Thread/Runnable子类方式创建线程
 * @author: sakana
 * @date: 2024/3/12 16:34
 * @version: 1.0
 */

public class Example_1 {
    public static void main(String[] args) {
        //创建线程
        WelcomeThread thread = new WelcomeThread();
        Thread thread1 = new Thread(new WelcomeTask());
        //守护线程在运行中不会影响java虚拟机正常结束，用户线程才会
        //thread.setDaemon(true);
        //启动线程
        thread.start();
        thread1.start();
        System.out.println("主线程-名字：" + Thread.currentThread().getName());
        //一个线程不可多次启动会抛异常
        //thread.start();
    }

    //继承Thread方式
    static class WelcomeThread extends Thread {
        @Override
        public void run() {
            System.out.println("Thread线程-名字：" + Thread.currentThread().getName());
            try {
                Thread.sleep(10 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    //继承Runnable接口方式
    static class WelcomeTask implements Runnable {

        @Override
        public void run() {
            System.out.println("Runnable线程-名字：" + Thread.currentThread().getName());
        }
    }
}
