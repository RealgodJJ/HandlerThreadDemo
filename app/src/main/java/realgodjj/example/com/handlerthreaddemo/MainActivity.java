package realgodjj.example.com.handlerthreaddemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "workHandler";
    private static final String TAG1 = "mainHandler";
    Handler mainHandler, workHandler;
    HandlerThread mHandlerThread;
    TextView tvMessage;
    Button bt1, bt2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvMessage = findViewById(R.id.tv_message);

        // 创建与主线程关联的Handler
        mainHandler = new Handler(Looper.getMainLooper())/* {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        tvMessage.setText("第一次执行" + Looper.myLooper().getThread().getId());
                        break;
                    case 2:
                        tvMessage.setText("第二次执行" + Looper.myLooper().getThread().getId());
                        break;
                }
            }
        }*/;

        /**
         * 步骤①：创建HandlerThread实例对象
         * 传入参数 = 线程名字，作用 = 标记该线程
         */
        mHandlerThread = new HandlerThread("handlerThread");

        /**
         * 步骤②：启动线程
         */
        mHandlerThread.start();

        /**
         * 步骤③：创建工作线程Handler & 复写handleMessage（）
         * 作用：关联HandlerThread的Looper对象、实现消息处理操作 & 与其他线程进行通信
         * 注：消息处理操作（HandlerMessage（））的执行线程 = mHandlerThread所创建的工作线程中执行
         */

        workHandler = new Handler(mHandlerThread.getLooper()) {
            @Override
            public void handleMessage(Message msg) {
                //设置了两种消息处理操作,通过msg来进行识别
                switch (msg.what) {
                    case 1:
                        try {
                            //延时操作
                            Log.d(TAG, "handleMessage: " + Looper.myLooper().getThread().getId());
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        // 通过主线程Handler.post方法进行在主线程的UI更新操作
//                        Message message1 = new Message();
//                        message1.what = 1;
//                        message1.obj = "A";
//                        mainHandler.sendMessage(message1);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                Log.d(TAG1, "handleMessage: " + Looper.myLooper().getThread().getId());
                                tvMessage.setText("第一次执行" + Looper.myLooper().getThread().getId());
                            }
                        });
                        break;
                    case 2:
                        try {
                            Log.d(TAG, "handleMessage: " + Looper.myLooper().getThread().getId());
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
//                        Message message2 = new Message();
//                        message2.what = 2;
//                        message2.obj = "B";
//                        mainHandler.sendMessage(message2);
                        mainHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                tvMessage.setText("第二次执行" + Looper.myLooper().getThread().getId());
                                Log.d(TAG1, "handleMessage: " + Looper.myLooper().getThread().getId());
                            }
                        });
                        break;
                    default:
                        break;
                }
            }
        };

        /**
         * 步骤④：使用工作线程Handler向工作线程的消息队列发送消息
         * 在工作线程中，当消息循环时取出对应消息 & 在工作线程执行相关操作
         */
        findViewById(R.id.bt1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = 1; //消息的标识
                msg.obj = "A"; // 消息的存放
                // 通过Handler发送消息到其绑定的消息队列
                workHandler.sendMessage(msg);
            }
        });

        findViewById(R.id.bt2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg = Message.obtain();
                msg.what = 2;
                msg.obj = "B";
                workHandler.sendMessage(msg);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandlerThread.quit(); // 退出消息循环
        workHandler.removeCallbacks(null); // 防止Handler内存泄露 清空消息队列
    }
}
