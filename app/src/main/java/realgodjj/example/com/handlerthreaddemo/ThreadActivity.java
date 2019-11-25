package realgodjj.example.com.handlerthreaddemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ThreadActivity extends AppCompatActivity {
    private static final String TAG = "RealgodJJ";
    private Button btSellTicket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread);

        btSellTicket = findViewById(R.id.bt_sell_ticket);
        btSellTicket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SellTicket sellTicket1 = new SellTicket(5);
                SellTicket sellTicket2 = new SellTicket(6);
                SellTicket sellTicket3 = new SellTicket(8);
                SellTicket sellTicket4 = new SellTicket(10);

                new Thread(sellTicket1).start();
                new Thread(sellTicket2).start();
                new Thread(sellTicket3).start();
                new Thread(sellTicket4).start();
//                sellTicket1.start();
//                sellTicket2.start();
//                sellTicket3.start();
//                sellTicket4.start();
//                try {
//                    Log.d(TAG, "Wait thread done!");
//                    sellTicket1.join();
//                    Log.d(TAG, "Join returned!");
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                new Thread(sellTicket1, "sellTicket1").start();
//                new Thread(sellTicket2, "sellTicket2").start();
//                new Thread(sellTicket3, "sellTicket3").start();
//                new Thread(sellTicket4, "sellTicket4").start();
            }
        });
    }

    public class SellTicket extends Thread {
        private int tickets;

        SellTicket(int tickets) {
            this.tickets = tickets;
        }

        @Override
        public void run() {
            super.run();
            while (tickets > 0) {
                sellTickets();
            }
            Log.d(TAG, Thread.currentThread().getName() + ": Sell tickets done.");
        }

        private void sellTickets() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            tickets--;
            Log.d(TAG, Thread.currentThread().getName() + ": Sell one ticket, left " + tickets + " tickets.");
        }
    }
}
