package com.lggyx.lgdemo02;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            String msg = getIntent().getExtras().getString("msg");
            setTitle(msg != null ? msg : getTitle());
        }
    }

    public void skip(View view) {
        Intent intent = new Intent();
        intent.setClass(this, SubActivity.class);
        intent.putExtra("msg", "从主页进入。");
        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK && data != null) {
                String backMsg = data.getStringExtra("back_msg");
                setTitle("现在返回到主页了," + backMsg);
            } else if (resultCode == RESULT_CANCELED) {
                setTitle("取消返回");
            }
        }
    }
}
