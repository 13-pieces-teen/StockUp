package com.example.stockup;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class update_stock extends AppCompatActivity {

    private Button btn_return;//返回到listview
    private Button btn_del;//删除该物品
    private Button btn_save;//保存修改

    private EditText et_produce_date;
    private EditText et_remarks;

    private TextView tv_guarantee;
    private TextView tv_after_date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);

        initView();


    }

    private void initView() {
        btn_return = findViewById(R.id.btn_return);
        btn_save = findViewById(R.id.btn_save);
        btn_del = findViewById(R.id.btn_del);

        et_produce_date = findViewById(R.id.et_produce_date);
        et_remarks = findViewById(R.id.et_remarks);

        tv_after_date = findViewById(R.id.tv_after_date);
        tv_guarantee = findViewById(R.id.tv_guarantee);

    }
}