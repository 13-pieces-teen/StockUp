package com.example.stockup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.widget.Button;
import android.view.View;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btn_add;//添加物品按钮

    private ImageView iv_food;//食品
    private ImageView iv_medicine;//药品
    private ImageView iv_supplies;//日用品（物资）
    private ImageView iv_cosmetics;//化妆品



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();//初始化控件

        //添加物品按钮的点击监听事件
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, add_stock.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });


    }

    private void initView() {
        btn_add = findViewById(R.id.btn_add);

        iv_cosmetics = findViewById(R.id.cosmetics_view);
        iv_food = findViewById(R.id.food_view);
        iv_medicine = findViewById(R.id.drug_view);
        iv_supplies = findViewById(R.id.supplies_view);

    }
}