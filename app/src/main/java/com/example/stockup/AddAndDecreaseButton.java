package com.example.stockup;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockup.R;

public class AddAndDecreaseButton extends LinearLayout
{
    private int amount = 1;     //数量

    private TextView etAmount;
    private Button btnDecrease;
    private Button btnIncrease;

    public AddAndDecreaseButton(Context context, AttributeSet attrs)
    {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.activity_add_and_decrease, this);

        etAmount = findViewById(R.id.etAmount);
        btnDecrease = findViewById(R.id.btnDecrease);
        btnIncrease = findViewById(R.id.btnIncrease);

        //设置‘-’号按键点击事件
        btnDecrease.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (amount > 1) {
                    amount--;
                }
                else
                {
                    Toast.makeText(getContext(), "已是最小数量", Toast.LENGTH_SHORT).show();
                }
                etAmount.setText(String.valueOf(amount));

            }
        });

        //设置‘+’号按键点击事件
        btnIncrease.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                amount++;
                etAmount.setText(String.valueOf(amount));
            }
        });
    }
}
