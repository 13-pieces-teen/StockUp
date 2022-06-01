package com.example.stockup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.stockup.Stock_Activity.Cosmetics_Stock_Act;
import com.example.stockup.entity.objectInfo;
import com.example.stockup.entity.wholeObject;

import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ObjectDBHelper.ObjectDBHelper;

public class update_stock extends AppCompatActivity {

    private Button btn_return;//返回到listview
    private Button btn_del;//删除该物品
    private Button btn_save;//保存修改
    private AddAndDecreaseButton btn_amount;
    private SeekBar sb_remind;
    private EditText et_produce_date;
    private EditText et_remarks;
    private EditText et_open_date;

    private ImageView iv_object_image;
    private TextView tv_days;
    private TextView tv_guarantee;
    private TextView tv_after_date;
    private TextView tv_percent;
    private ObjectDBHelper DBHelper;
    private int ID;
    private String objType;
    private int betweenDays;
    private String text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_stock);
        DBHelper = new ObjectDBHelper(this);
        Bundle bundle=getIntent().getExtras();
        ID = bundle.getInt("ID");
        objType = bundle.getString("objType");

        initView();
        getAllInfoSet();

        //返回到list的界面(好像不太对劲,需要调整)
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(update_stock.this, MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

        sb_remind.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //将seekBar的进度设置在textView上
                String ww = String.format("(%d%%)", progress);
                tv_percent.setText(ww);

                //计算离到期还有几天
                int calDays = betweenDays * progress / 100;
                String ss = String.format("%d天",calDays);
                tv_days.setText(ss);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btn_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delFromID();
                Intent intent = new Intent();
                intent.setClass(update_stock.this, MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

    }

    //更新页面
    private void getAllInfoSet()
    {
        switch (objType){
            case "food":
                isFoodType();
                Log.i("更新页面","food");
                break;
            case "durg":
                isDurgType();
                Log.i("更新页面","drug");
                break;
            case "cosmetics":
                isCosmType();
                Log.i("更新页面","cosmetics");
                break;
            case "supplies":
                isSuppliesType();
                Log.i("更新页面","supplies");
                break;
            default:
                Log.i("更新页面","未查询到该类别");
                break;
        }
    }

    //如果传过来是food类别
    private void isFoodType()
    {
        objectInfo obj1 = new objectInfo();
        obj1 = DBHelper.getFoodFromID(ID);
        Uri uri = Uri.parse((String) obj1.getImageURL());
        iv_object_image.setImageURI(uri);
        et_produce_date.setText(obj1.getOB_produce_date());
        tv_after_date.setText(obj1.getOB_after_date());
        tv_guarantee.setText(obj1.getOB_guarantee_day());
        betweenDays = obj1.getBetweenDays();
        tv_days.setText(betweenDays/2 + "天");
        et_remarks.setText(obj1.getOB_remarks());
        btn_amount.changeAmount(obj1.getOB_amount());
        et_open_date.setText(obj1.getOB_open_date());
    }

    //如果传过来是durg类别
    private void isDurgType()
    {
        objectInfo obj1 = new objectInfo();
        obj1 = DBHelper.getDurgFromID(ID);
        Uri uri = Uri.parse((String) obj1.getImageURL());
        iv_object_image.setImageURI(uri);
        et_produce_date.setText(obj1.getOB_produce_date());
        tv_after_date.setText(obj1.getOB_after_date());
        tv_guarantee.setText(obj1.getOB_guarantee_day());
        betweenDays = obj1.getBetweenDays();
        tv_days.setText(betweenDays/2 + "天");
        et_remarks.setText(obj1.getOB_remarks());
        btn_amount.changeAmount(obj1.getOB_amount());
        et_open_date.setText(obj1.getOB_open_date());
    }

    //如果传过来是cosmetics类别
    private void isCosmType()
    {
        objectInfo obj1 = new objectInfo();
        obj1 = DBHelper.getCosmFromID(ID);
        Uri uri = Uri.parse((String) obj1.getImageURL());
        iv_object_image.setImageURI(uri);
        et_produce_date.setText(obj1.getOB_produce_date());
        tv_after_date.setText(obj1.getOB_after_date());
        tv_guarantee.setText(obj1.getOB_guarantee_day());
        betweenDays = obj1.getBetweenDays();
        tv_days.setText(betweenDays/2 + "天");
        et_remarks.setText(obj1.getOB_remarks());
        btn_amount.changeAmount(obj1.getOB_amount());
        et_open_date.setText(obj1.getOB_open_date());
    }

    //如果传过来是supplies类别
    private void isSuppliesType()
    {
        objectInfo obj1 = new objectInfo();
        obj1 = DBHelper.getSuppliesFromID(ID);
        Uri uri = Uri.parse((String) obj1.getImageURL());
        iv_object_image.setImageURI(uri);
        et_produce_date.setText(obj1.getOB_produce_date());
        tv_after_date.setText(obj1.getOB_after_date());
        tv_guarantee.setText(obj1.getOB_guarantee_day());
        betweenDays = obj1.getBetweenDays();
        tv_days.setText(betweenDays/2 + "天");
        et_remarks.setText(obj1.getOB_remarks());
        btn_amount.changeAmount(obj1.getOB_amount());
        et_open_date.setText(obj1.getOB_open_date());
    }

    //通过ID删除
    private void delFromID()
    {
        switch (objType){
            case "food":
                DBHelper.deleteFoodWithID(ID);
                text = objType + "编号为" + ID + "被删除";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            case "durg":
                DBHelper.deleteDurgWithID(ID);
                text = objType + "编号为" + ID + "被删除";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            case "cosmetics":
                DBHelper.deleteComeWithID(ID);
                text = objType + "编号为" + ID + "被删除";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            case "supplies":
                DBHelper.deleteSuppliesWithID(ID);
                text = objType + "编号为" + ID + "被删除";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            default:

                break;
        }
    }

    private objectInfo updateInfo()
    {
        objectInfo obj1 = new objectInfo();



        return obj1;
    }


    //通过ID更新
    private void updateFromID()
    {
        objectInfo obj1 = new objectInfo();
        obj1 = updateInfo();
        switch (objType){
            case "food":
                DBHelper.updateFoodWithID(obj1,ID);
                text = objType + "编号为" + ID + "被更新";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            case "durg":
                DBHelper.updateDurgWithID(obj1,ID);
                text = objType + "编号为" + ID + "被更新";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            case "cosmetics":
                DBHelper.updateCosmeticsWithID(obj1,ID);
                text = objType + "编号为" + ID + "被更新";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            case "supplies":
                DBHelper.updateSuppliesWithID(obj1,ID);
                text = objType + "编号为" + ID + "被更新";
                Toast.makeText(update_stock.this,text,Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
    }



    private void initView() {
        btn_return = findViewById(R.id.btn_return);
        btn_save = findViewById(R.id.btn_save);
        btn_del = findViewById(R.id.btn_del);
        btn_amount = findViewById(R.id.btn_amount);

        et_open_date = findViewById(R.id.et_open_date);
        et_produce_date = findViewById(R.id.et_produce_date);
        et_remarks = findViewById(R.id.et_remarks);
        tv_days = findViewById(R.id.tv_days);
        tv_after_date = findViewById(R.id.tv_after_date);
        tv_guarantee = findViewById(R.id.tv_guarantee);
        tv_percent = findViewById(R.id.tv_percent);
        iv_object_image =findViewById(R.id.iv_object_image);
        sb_remind = findViewById(R.id.sb_remind);
    }
}