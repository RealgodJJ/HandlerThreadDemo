package realgodjj.example.com.handlerthreaddemo;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {
    private static final String TAG = "RealgodJJ";
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Handler handler = new Handler(Looper.myLooper()) {
                    @Override
                    public void handleMessage(Message msg) {
                        Log.i(TAG, "threadHandler handle message");
                        Log.i(TAG, "handleMessage: " + Thread.currentThread().getName());
                        //不建议在子线程进行更新UI操作
                        // 其根本原因在于android实现更新UI的对象——ViewRootImpl是在onResume方法中去初始化的，
                        // 由于会先执行onCreate方法，所以此时的viewRootImpl还没有被初始化。
                        textView.setText("哎呦艾瑞博迪在你头上暴扣！");
                    }
                };
                Message message = Message.obtain();
                handler.sendMessageDelayed(message, 5000);
                Looper.loop();
            }
        };
        new Thread(runnable).start();
    }
}
