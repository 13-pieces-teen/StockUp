package com.example.stockup.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.stockup.R;
import com.example.stockup.entity.objectInfo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
        ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progressBar);


        //list_Image.setImageResource(items.get);
        list_Name.setText(items.getOB_name());
        list_Num.setText(Integer.toString(items.getOB_amount()));
        list_produceDate.setText(items.getOB_produce_date());
        list_afterDate.setText(items.getOB_after_date());
        int between_days=items.getBetweenDays();
       // 对于东西保质期进行100等分；progressbar
        try {
            int runDay=progressBar.getProgress();
            int run_day=StringtoData(items.getOB_after_date());
            run_day=(run_day/between_days)*100;
            runDay=runDay+run_day;
            progressBar.setProgress(runDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return view;
    }
    //输出离生产日期过去多久
    public int StringtoData(String str) throws ParseException {
        int diffday;//天数差

        //得到生产时间
        SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
        Date date =sdf.parse(str);
        Calendar cal_Pro = Calendar.getInstance();
        cal_Pro.setTime(date);
        //得到当前日期
        Calendar cal2 = Calendar.getInstance();

        //计算
        diffday = cal2.get(Calendar.DAY_OF_YEAR) -cal_Pro.get(Calendar.DAY_OF_YEAR);
        return  diffday;
    }
}
