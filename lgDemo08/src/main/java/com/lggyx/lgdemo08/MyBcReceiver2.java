package com.lggyx.lgdemo08;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.Gravity;

public class MyBcReceiver2 extends BroadcastReceiver {

    private static final String TAG = "MyBcReceiver2";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG, "onReceive: MyBcReceiver2 received broadcast");

        Toast toast = Toast.makeText(context, "第2个接收器,接收到广播消息。", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 200);
        toast.show();

        Bundle bundle = new Bundle();
        bundle.putString("extra", "第2个接收器传递的消息啦!!!");

        if (isOrderedBroadcast()) {
            setResultExtras(bundle);
        }
    }
}
