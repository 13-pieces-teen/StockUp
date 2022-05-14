package com.example.stockup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.os.Bundle;
import android.view.MenuItem;


import com.example.stockup.fragment.CalFragment;
import com.example.stockup.fragment.HomeFragment;
import com.example.stockup.fragment.SetFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ViewPager vp;
    private ViewPagerAdapter adapter;
    private BottomNavigationView bottomnv;
    private CalFragment calFragment;
    private SetFragment setFragment;
    private HomeFragment homeFragment;
    List<Fragment> fragmentList;



    private BottomNavigationView bnv;
    public List<Fragment> getFragmentList() {
        return fragmentList;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentList = new ArrayList<>();
        intView();
        vp=findViewById(R.id.vpager);
        bottomnv= findViewById(R.id.bnv);
        adapter = new ViewPagerAdapter(getSupportFragmentManager(),fragmentList);
        vp.setAdapter(adapter);

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

    private void intView() {
        homeFragment = new HomeFragment();
        setFragment = new SetFragment();
        calFragment=new CalFragment();
        fragmentList.add(homeFragment);
        fragmentList.add(calFragment);
        fragmentList.add(setFragment);
    }




}


