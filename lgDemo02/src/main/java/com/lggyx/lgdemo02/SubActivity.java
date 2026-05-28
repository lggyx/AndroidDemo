package com.lggyx.lgdemo02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SubActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subactivity);

        String msg = getIntent().getStringExtra("msg");
        setTitle("这是子页面," + (msg != null ? msg : ""));
    }

    public void back(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("back_msg", "自子页返回。");
        intent.putExtras(bundle);
        setResult(RESULT_OK, intent);
        finish();
    }
}
