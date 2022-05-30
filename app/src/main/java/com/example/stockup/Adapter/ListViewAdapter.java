package com.example.stockup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.stockup.R;
import com.example.stockup.entity.objectInfo;

import java.util.List;

public class ListViewAdapter extends ArrayAdapter{



    private final int resourceId;

    public ListViewAdapter(Context context, int textViewResourceId, List<objectInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        objectInfo items = (objectInfo) getItem(position); // 获取当前项实例
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        //获取该布局内的控件
        ImageView list_Image = (ImageView) view.findViewById(R.id.list_image);
        TextView list_Name = (TextView) view.findViewById(R.id.list_Name);
        TextView list_Num= (TextView) view.findViewById(R.id.list_Num);
        TextView list_produceDate = (TextView) view.findViewById(R.id.list_produceDate);
        TextView list_afterDate = (TextView) view.findViewById(R.id.list_afterDate);


        //list_Image.setImageResource(items.get);
        list_Name.setText(items.getOB_name());
        list_Num.setText(items.getOB_amount());
        list_produceDate.setText(items.getOB_produce_date());
        list_afterDate.setText(items.getOB_after_date());
        return view;
    }
}
