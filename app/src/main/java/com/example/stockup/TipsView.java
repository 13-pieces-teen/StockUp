package com.example.stockup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class TipsView extends LinearLayout {

    private TextView tv_tips;//提示里的文本框
    private Button btn_next;//下一个提示按钮


    public TipsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.activity_tipsview, this);

        tv_tips = findViewById(R.id.tv_tips);
        btn_next = findViewById(R.id.btn_next);

    }

    public void setTv_tips(String text)
    {
        tv_tips.setText(text);

    }


}
