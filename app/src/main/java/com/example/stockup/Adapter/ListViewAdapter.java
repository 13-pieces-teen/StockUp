package com.example.stockup.Adapter;

import static java.lang.Math.abs;

import android.content.Context;
import android.net.Uri;
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
    private int indexID;//物品所对应id


    public ListViewAdapter(Context context, int textViewResourceId, List<objectInfo> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        objectInfo items = (objectInfo) getItem(position); // 获取当前项实例,position
        indexID = position + 1;
        View view = LayoutInflater.from(getContext()).inflate(resourceId, null);//实例化一个对象

        //获取该布局内的控件
        ImageView list_Image = (ImageView) view.findViewById(R.id.recy_image);
        TextView list_Name = (TextView) view.findViewById(R.id.recy_Name);
        TextView list_Num= (TextView) view.findViewById(R.id.recy_Num);
        TextView list_produceDate = (TextView) view.findViewById(R.id.recy_produceDate);
        TextView list_afterDate = (TextView) view.findViewById(R.id.recy_afterDate);
        TextView list_between = (TextView) view.findViewById(R.id.recy_between);
        ProgressBar progressBar=(ProgressBar) view.findViewById(R.id.progressBar);


        String str= items.getImageURL();
        Uri uri = Uri.parse((String) str);

        list_Image.setImageURI(uri);
        list_Name.setText(items.getOB_name());
        list_Num.setText(Integer.toString(items.getOB_amount())+" 个");
        list_produceDate.setText(items.getOB_produce_date());
        list_afterDate.setText(items.getOB_after_date());
        int between_days=items.getBetweenDays();
        list_between.setText(items.getOB_guarantee_day());
       // 对于东西保质期进行100等分；progressbar
        try {
            int runDay=progressBar.getProgress();
            double run_day=( (double)StringtoData(items.getOB_produce_date())/(double)between_days)*100;
            runDay=runDay+(int)run_day;
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

        int day1=cal_Pro.get(Calendar.DAY_OF_YEAR);
        int day2=cal2.get(Calendar.DAY_OF_YEAR);
        //计算年数
        int year1 = cal_Pro.get(Calendar.YEAR);
        int year2 = cal2.get(Calendar.YEAR);
        int diffyear=0;
        if(year1!=year2){
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                    day2 += 366;
                else //不是闰年
                    day2+= 365;
            }
            diffyear = day2-day1;
            return diffyear;
        }
        else {
            diffyear = day2-day1;
            return diffyear;
        }
    }
}
