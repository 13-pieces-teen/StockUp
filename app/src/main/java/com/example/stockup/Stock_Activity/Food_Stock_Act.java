package com.example.stockup.Stock_Activity;


import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.stockup.Adapter.ListViewAdapter;
import com.example.stockup.R;
import com.example.stockup.entity.objectInfo;

import java.util.ArrayList;
import java.util.List;
import ObjectDBHelper.ObjectDBHelper;

public class Food_Stock_Act extends AppCompatActivity{
    private ListView listView;//食物列表数组
    private ObjectDBHelper objectDBHelper;
    private List<objectInfo> food_list = new ArrayList<objectInfo>();
    private int index;//行数索引，也是该食品在数据库里的id
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_activity_stock);
        objectDBHelper = new ObjectDBHelper(this);//很重要，之前忘了实例化，会导致空指针

        listView = findViewById(R.id.listView);
        //数据库查询并添加到数组
        initlist();
        //给listView设置ArrayAdapter，绑定数据
        ListViewAdapter listadapter=new ListViewAdapter(Food_Stock_Act.this,R.layout.listview_items,food_list);
        listView.setAdapter(listadapter);
        //点击监听
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                index = i+1;
                Toast.makeText(Food_Stock_Act.this, "你点击了第" + index + "行", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void initlist() {
        food_list=objectDBHelper.getFoodInfo();

    }

    //获取当前list的id
    public int getIndex()
    {
        return index;
    }

}
