package com.lggyx.lgdemo08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnSend, btnOrderedSend;
    private Intent sendIntent;
    private MyBcReceiver2 myReceiver2;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSend = findViewById(R.id.btn_send);
        btnOrderedSend = findViewById(R.id.btn_orderedsend);

        sendIntent = new Intent();
        sendIntent.setAction("myaction");
        sendIntent.putExtra("msg", "广播消息:教育部下发2020新规定。");
        sendIntent.setPackage(getPackageName());

        myReceiver2 = new MyBcReceiver2();

        btnSend.setOnClickListener(v -> {
            Log.e(TAG, "发送无序广播");
            sendBroadcast(sendIntent);
        });

        btnOrderedSend.setOnClickListener(v -> {
            Log.e(TAG, "发送有序广播");
            MyBcReceiver3 myReceiver3 = new MyBcReceiver3();
            sendOrderedBroadcast(sendIntent, null, myReceiver3, null, android.app.Activity.RESULT_OK, null, null);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter("myaction");
        filter.setPriority(99);
        registerReceiver(myReceiver2, filter, Context.RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            unregisterReceiver(myReceiver2);
        } catch (IllegalArgumentException e) {
            Log.e(TAG, "Receiver not registered", e);
        }
    }
}
