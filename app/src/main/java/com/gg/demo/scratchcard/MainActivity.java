package com.gg.demo.scratchcard;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.gg.demo.scratchcard.view.EraserView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EraserView eraserView;
    private Random random = new Random();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.tv_result);
        eraserView = findViewById(R.id.eraserView);
        setResult();
        findViewById(R.id.again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eraserView.reset();
                setResult();
            }
        });
    }

    private void setResult() {
        int number = random.nextInt(200);
        if (number < 5) {
            textView.setText("恭喜你 获得一等奖!!!\n外星人电脑一台");
        } else if (number < 15) {
            textView.setText("恭喜你 获得二等奖!!!\n海尔洗衣机一台");
        } else if (number < 35) {
            textView.setText("恭喜你 获得三等奖!!!\n美的热水壶一个");
        } else if (number < 65) {
            textView.setText("恭喜你 获得安慰奖!!!\n卫生纸一包");
        } else {
            textView.setText("真抱歉，你没有中奖");
        }
    }

}
