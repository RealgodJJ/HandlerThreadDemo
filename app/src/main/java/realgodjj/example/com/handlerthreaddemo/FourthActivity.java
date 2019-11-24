package realgodjj.example.com.handlerthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FourthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fourth);
    }

    public static void main(String[] args) {
        MyRunnable t1 = new MyRunnable();
//        MyThread t1 = new MyThread();
        new Thread(t1, "线程1").start();
        new Thread(t1, "线程2").start();
    }

    public static class MyThread extends Thread {
        private int total = 10;

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (this) {
                    if (total > 0) {
                        try {
                            Thread.sleep(100);
                            System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.total--));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    public static class MyRunnable implements Runnable {
        private int total = 10;

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {
                synchronized (this) {
                    if (total > 0) {
                        try {
                            Thread.sleep(100);
                            System.out.println(Thread.currentThread().getName() + "卖票---->" + (this.total--));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
