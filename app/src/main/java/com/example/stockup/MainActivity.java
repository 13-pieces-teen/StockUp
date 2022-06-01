package com.example.stockup;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.stockup.Adapter.ViewPagerAdapter;
import com.example.stockup.fragment_menu.CalFragment;
import com.example.stockup.fragment_menu.HomeFragment;
import com.example.stockup.fragment_menu.SetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private ViewPagerAdapter adapter;
    private BottomNavigationView bottomnv;
    private CalFragment calFragment;
    private SetFragment setFragment;
    private HomeFragment homeFragment;
    private FloatingActionButton fab_add;
    List<Fragment> fragmentList;
    private Button btn_test;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intView();


        intData();
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp.setAdapter(adapter);

        //添加按钮绑定
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, add_stock.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

        //底部导航栏监听
        bottomnv.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.i_home:
                        vp.setCurrentItem(0);
                        break;
                    case R.id.i_calendar:
                        vp.setCurrentItem(1);
                        break;
                    case R.id.i_set:
                        vp.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        //滑动监听
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener(){

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                onPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    }


    //将底部导航栏绑定vp
    private void onPagerSelected(int position) {
        switch (position){
            case 0:
                bottomnv.setSelectedItemId(R.id.i_home);
                break;
            case 1:
                bottomnv.setSelectedItemId(R.id.i_calendar);
                break;
            case 2:
                bottomnv.setSelectedItemId(R.id.i_set);
                break;
            default:
                break;
        }
    }





    //添加fragment到list中
    private void intView() {
        fragmentList = new ArrayList<>();
        homeFragment = new HomeFragment();
        setFragment = new SetFragment();
        calFragment=new CalFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(calFragment);
        fragmentList.add(setFragment);
    }
    //绑定实例按键
    private void intData() {
        vp=findViewById(R.id.vpager);
        bottomnv= findViewById(R.id.bnv);
        fab_add= findViewById(R.id.fab);
    }





}


