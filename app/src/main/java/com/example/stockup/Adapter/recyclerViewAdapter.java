package com.example.stockup.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ContentView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.stockup.R;
import com.example.stockup.entity.objectInfo;

import java.util.ArrayList;
import java.util.List;

public class recyclerViewAdapter extends RecyclerView.Adapter<recyclerViewAdapter.ViewHolder>{

    private Context context;
    private List<objectInfo> Deadline_obj= new ArrayList<objectInfo>();

    static class ViewHolder extends RecyclerView.ViewHolder{
        //静态内部类， 每个条目对应的布局
        String str;
        Uri uri ;
        TextView between;
        TextView num;
        TextView recy_Name;
        TextView recy_ProduceDate;
        TextView recy_AfterDate;
        ImageView recy_Image;
        public ViewHolder (View view)
        {
            super(view);
            between = (TextView) view.findViewById(R.id.recy_between);
            num = (TextView) view.findViewById(R.id.recy_Num);
            recy_Name = (TextView) view.findViewById(R.id.recy_Name);
            recy_ProduceDate= (TextView) view.findViewById(R.id.recy_produceDate);
            recy_AfterDate = (TextView) view.findViewById(R.id.recy_afterDate);
            recy_Image= (ImageView) view.findViewById(R.id.recy_image);
        }
    }
    //FruitAdapter的构造方法，加入了数据源参数，在构造的时候赋值给Deadline_obj
    public recyclerViewAdapter(Context context, List<objectInfo> Deadline_obj){
        this.context=context;
        this.Deadline_obj=Deadline_obj;
    }
    //用于创建ViewHolder实例,并把加载的布局传入到ViewHolder的构造函数去
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);//实例化一个对象

        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    //是用于对子项的数据进行赋值,会在每个子项被滚动到屏幕内时执行。position得到当前项的Deadline_obj实例

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        objectInfo obj= Deadline_obj.get(position);
        String str= obj.getImageURL();
        Uri uri = Uri.parse((String) str);
        holder.num.setText(String.valueOf(obj.getOB_amount()));
        holder.between.setText(String.valueOf(obj.getBetweenDays()));
        holder.recy_AfterDate.setText(obj.getOB_after_date());
        holder.recy_Name.setText(obj.getOB_name());
        holder.recy_ProduceDate.setText(obj.getOB_produce_date());
        holder.recy_Image.setImageURI(uri);

    }

    @Override
    public int getItemCount() {
        return Deadline_obj.size();
    }
}
