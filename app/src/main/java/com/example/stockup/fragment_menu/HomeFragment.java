package com.example.stockup.fragment_menu;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.stockup.Stock_Activity.Food_Stock_Act;
import com.example.stockup.R;

import java.util.Calendar;

public class HomeFragment extends Fragment {

    Calendar c = Calendar.getInstance();
    int day = c.get(Calendar.DAY_OF_MONTH);
    int month = c.get(Calendar.MONTH);
    int year = c.get(Calendar.YEAR);
    String date = year + "年" + (month+1) + "月" + day+"日";

    TextView view_Data;
    ImageView bnt_food,bnt_supplies,bnt_cosmetics,bnt_drug;
   public HomeFragment() {
        // Required empty public constructor
    }


    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onStart() {
        super.onStart();
        initData();
        //日期显示
        view_Data.setText(date);
        //食物按钮
        bnt_food.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity() ,Food_Stock_Act.class);
                startActivity(intent);
            }
        });

    }

    private void initData() {
       view_Data=getView().findViewById(R.id.Data_view);
       bnt_food=getView().findViewById(R.id.food_view);
       bnt_supplies=getView().findViewById(R.id.supplies_view);
       bnt_cosmetics=getView().findViewById(R.id.cosmetics_view);
       bnt_drug=getView().findViewById(R.id.drug_view);
    }
}