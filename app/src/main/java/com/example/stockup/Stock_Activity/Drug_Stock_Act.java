package com.example.stockup.Stock_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stockup.Adapter.ListViewAdapter;
import com.example.stockup.R;
import com.example.stockup.entity.objectInfo;
import com.example.stockup.update_stock;

import java.util.ArrayList;
import java.util.List;

import ObjectDBHelper.ObjectDBHelper;

public class Drug_Stock_Act extends AppCompatActivity {
    private TextView reminder_text;
    private ListView listView;
    private ObjectDBHelper objectDBHelper;
    private Button btn_back;
    private List<objectInfo> drug_list = new ArrayList<objectInfo>();
    private int index;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_activity_stock);

        objectDBHelper = new ObjectDBHelper(this);//很重要，之前忘了实例化，会导致空指针
        reminder_text=findViewById(R.id.reminder_text);
        listView = findViewById(R.id.listView);
        btn_back=findViewById(R.id.btn_Toback);

        //点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i+1;
                Toast.makeText(Drug_Stock_Act .this, "你点击了第" + index + "行", Toast.LENGTH_SHORT).show();

                Bundle bundle = new Bundle();//用Bundle传递参数给下一个页面
                bundle.putInt("ID", index);
                bundle.putString("objType", "durg");
                String OB_name = drug_list.get(i).getOB_name();
                bundle.putString("OBname",OB_name);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(Drug_Stock_Act.this, update_stock.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                Log.i("listView", "物资列表跳转至更新界面");
                startActivity(intent);
            }
        });
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //数据库查询并添加到数组
        initlist();
        //给listView设置ArrayAdapter，绑定数据
        ListViewAdapter listadapter=new ListViewAdapter(Drug_Stock_Act .this,R.layout.listview_items,drug_list);
        listView.setAdapter(listadapter);
    }

    private void initlist() {
        drug_list=objectDBHelper.getDurgInfo();
        //判断数组为空
        if(drug_list.size()==0){
            reminder_text.setText("当前为空，快去添加吧");
        }
    }

}