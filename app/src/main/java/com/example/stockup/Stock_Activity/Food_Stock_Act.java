package com.example.stockup.Stock_Activity;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import com.example.stockup.R;
import com.example.stockup.fragment_menu.CalFragment;
import com.example.stockup.fragment_menu.HomeFragment;
import com.example.stockup.fragment_menu.SetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class Food_Stock_Act extends AppCompatActivity{
   /*
    private ListView listView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.com_activity_stock);
        listView = findViewById(R.id.listView);


        //给listView设置ArrayAdapter，绑定数据
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.listview_items, s));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(Food_Stock_Act.this, "你点击了第" + i + "行", Toast.LENGTH_SHORT).show();
            }
        });
    }
    */

}
