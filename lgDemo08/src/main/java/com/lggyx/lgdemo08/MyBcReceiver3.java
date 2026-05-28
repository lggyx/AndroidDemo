package com.lggyx.lgdemo08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.Gravity;

public class MyBcReceiver3 extends BroadcastReceiver {

    private static final String TAG = "MyBcReceiver3";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: MyBcReceiver3 received broadcast");

        Bundle extras = isOrderedBroadcast() ? getResultExtras(true) : null;
        String extraMsg = extras != null ? extras.getString("extra", "无附加消息") : "无附加消息";

        String msg = intent.getStringExtra("msg");
        String result = "原始消息:" + msg + "\n接收到的附加消息:" + extraMsg;

        Toast toast = Toast.makeText(context, result, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM, 0, 120);
        toast.show();
    }
}
