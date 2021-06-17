package org.skoal.restrictor.utils;

public class ThreadUtils {
    public static void sleepSeconds(long seconds) {
        try {
            Thread.sleep(1000 * seconds);
        } catch (InterruptedException e) {
            // 恢复线程中断状态 （默认是中断异常时清除当前线程的中断状态)
            // 以便于在多线程程序设计里用 Thread.currentThread().interrupted()来判断是否收到中断请求
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void sleepMillis(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    /**
     * @param thread 被连接的子线程
     */
    public static void join(Thread thread) {
        try {
            thread.join();
        } catch (InterruptedException e) {
            // 恢复主线程中断状态
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }
}
