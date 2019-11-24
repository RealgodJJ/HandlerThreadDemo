package realgodjj.example.com.handlerthreaddemo;

import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class ThirdActivity extends AppCompatActivity {
    public static final int DELAY = 101;
    public static final int DELAY_TIME = 1000;
    public static final int START_TIME = 1;
    private TextView textView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        textView2 = findViewById(R.id.textView2);

        DunkHandler dunkHandler = new DunkHandler(this);
        Message message = new Message();
        message.what = DELAY;
        message.arg1 = START_TIME;
        dunkHandler.sendMessageDelayed(message, DELAY_TIME);
    }

    private static class DunkHandler extends Handler {
        WeakReference<ThirdActivity> weakReference;

        public DunkHandler(ThirdActivity thirdActivity) {
            weakReference = new WeakReference<>(thirdActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            ThirdActivity thirdActivity = weakReference.get();
            switch (msg.what) {
                case DELAY:
                    int value = msg.arg1;
                    thirdActivity.textView2.setText("哎呦艾瑞博迪在你头上暴扣" + (value++) + "次！");

                    if (value <= 10) {
                        Message message = Message.obtain();
                        message.what = DELAY;
                        message.arg1 = value;
                        sendMessageDelayed(message, DELAY_TIME);
                    }
                    break;
            }
        }
    }
}
