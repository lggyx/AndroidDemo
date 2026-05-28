package com.lggyx.lgdemo05;

import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView eventLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        eventLabel = findViewById(R.id.event_label);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Display("ACTION_DOWN", event);
                break;
            case MotionEvent.ACTION_UP:
                Display("ACTION_UP", event);
                break;
            case MotionEvent.ACTION_MOVE:
                Display("ACTION_MOVE", event);
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void Display(String eventType, MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        float pressure = event.getPressure();
        float size = event.getSize();

        String msg = "事件类型:" + eventType + "\n"
                   + "坐标(x ,y):" + x + "," + y + "\n"
                   + "触点压力:" + pressure + "\n"
                   + "触点尺寸:" + size;

        eventLabel.setText(msg);
    }
}
