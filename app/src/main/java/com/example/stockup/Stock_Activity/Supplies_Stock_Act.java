package com.example.stockup.Stock_Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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

public class Supplies_Stock_Act extends AppCompatActivity {
    private TextView reminder_text;
    private ListView listView;
    private ObjectDBHelper objectDBHelper;
    //列表数组
    private List<objectInfo> supplies_list = new ArrayList<objectInfo>();
    private int index;//行数索引，也是该食品在数据库里的id

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_activity_stock);

        objectDBHelper = new ObjectDBHelper(this);//很重要，之前忘了实例化，会导致空指针
        reminder_text=findViewById(R.id.reminder_text);
        listView = findViewById(R.id.listView);
        //数据库查询并添加到数组
        initlist();
        //给listView设置ArrayAdapter，绑定数据
        ListViewAdapter listadapter=new ListViewAdapter(Supplies_Stock_Act.this,R.layout.listview_items,supplies_list);
        listView.setAdapter(listadapter);
        //点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Supplies_Stock_Act.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
                index = i+1;
                Bundle bundle = new Bundle();//用Bundle传递参数给下一个页面
                bundle.putInt("ID", index);
                bundle.putString("objType", "supplies");
                Intent intent = new Intent();
                intent.putExtras(bundle);
                intent.setClass(Supplies_Stock_Act.this, update_stock.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                Log.i("listView", "物资列表跳转至更新界面");
                startActivity(intent);
            }
        });
    }

    private void initlist() {
        supplies_list=objectDBHelper.getSuppliesInfo();
        //判断数组为空
        if(supplies_list.size()==0){
            reminder_text.setText("当前为空，快去添加吧");
        }
    }


}
