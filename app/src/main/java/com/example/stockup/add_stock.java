package com.example.stockup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Calendar;

public class add_stock extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int REQUEST_CODE_CHOOSE = 0;
    private EditText et_object_name;//产品名称
    private EditText et_guarantee_date;//保质期
    private EditText et_after_date;//过期日期
    private EditText et_produce_date;//生产日期
    private EditText et_open_date;//开封日期
    private EditText et_remarks;//物品备注

    private RadioGroup DateGroup;//保质期的单选按钮
    private RadioButton rb_year;
    private RadioButton rb_month;
    private RadioButton rb_day;

    private TextView tv_open_date;
    private TextView tv_remarks;//物品备注

    private Spinner sp_object;//物品选择下拉框
    private ImageButton btn_object_image;//物品图像
    private ImageView iv_object_image;
    private Button btn_QR;//扫一扫
    private Button btn_else;//其他选项
    private Button btn_save;//保存按钮
    private Button btn_return;//返回按钮（返回一级菜单）
    private Uri imageUri;

    private String date;
    private int guaranteeDay;//保质天数
    private int amount;//物品数量

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        initView();//初始化

        //保质天数单选按钮组的监听事件
        DateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                String result = radioButton.getText().toString();
            }
        });

        //拍摄图片
        iv_object_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 创建File对象，用于存储拍照后的图片
                // 将图片命名为output_image.jpg，并将它存放在手机SD卡的应用关联缓存目录下
                // getExternalCacheDir()可以得到这个目录
                File output = new File(getExternalCacheDir(), "output_image.jpg");
                try {
                    if (output.exists()) {
                        output.delete();
                    }
                    output.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    //图片的保存路径
                    imageUri = FileProvider.getUriForFile(add_stock.this, "com.example.stockup.fileprovider", output);
                } else {
                    imageUri = Uri.fromFile(output);
                }
                //跳转界面到系统自带的拍照界面
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                iv_object_image.setBackground(null);
                startActivityForResult(intent, TAKE_PHOTO);
            }
        });

        //其他选项按钮的监听事件
        btn_else.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.setVisibility(View.GONE);
                tv_open_date.setVisibility(View.VISIBLE);
                et_open_date.setVisibility(View.VISIBLE);
                tv_remarks.setVisibility(View.VISIBLE);
                et_remarks.setVisibility(View.VISIBLE);
            }
        });

        //返回按钮的监听事件
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(add_stock.this, MainActivity.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                startActivity(intent);
            }
        });

        //生产日期的监听事件
        et_produce_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(add_stock.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                                String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                                Toast.makeText(add_stock.this, text, Toast.LENGTH_SHORT).show();
                                et_produce_date.setText(date);


                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //限制选择日期在今天之前
                dialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        //过期日期的监听事件
        et_after_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(add_stock.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                                String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                                Toast.makeText(add_stock.this, text, Toast.LENGTH_SHORT).show();
                                et_after_date.setText(date);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //限制选择日期在今天之后
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

        //开封日期的监听事件
        et_open_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();

                DatePickerDialog dialog = new DatePickerDialog(add_stock.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date = String.format("%d-%d-%d", year, month + 1, dayOfMonth);
                                String text = "你选择了：" + year + "年" + (month + 1) + "月" + dayOfMonth + "日";
                                Toast.makeText(add_stock.this, text, Toast.LENGTH_SHORT).show();
                                et_open_date.setText(date);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                //限制选择日期在今天之后
                dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                dialog.show();
            }
        });

    }

    //图像保存在页面上
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 使用try让程序运行在内报错
                    try {
                        //将图片保存
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        iv_object_image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            default:
                break;
        }
    }


    public void calculateDate()
    {
        //保质天数的获取
        guaranteeDay = Integer.parseInt(et_guarantee_date.getText().toString());


    }


    private void initView() {
        et_object_name =  findViewById(R.id.et_object_name);
        et_guarantee_date =  findViewById(R.id.et_guarantee_date);
        et_produce_date =  findViewById(R.id.et_produce_date);
        et_after_date =  findViewById(R.id.et_after_date);
        et_open_date = findViewById(R.id.et_open_date);
        et_remarks = findViewById(R.id.et_remarks);

        DateGroup = findViewById(R.id.rg_date);
        rb_day = findViewById(R.id.rb_day);
        rb_month = findViewById(R.id.rb_month);
        rb_year = findViewById(R.id.rb_year);

        sp_object = findViewById(R.id.sp_object);
        tv_open_date = findViewById(R.id.tv_open_date);
        tv_remarks = findViewById(R.id.tv_remarks);
        iv_object_image =  findViewById(R.id.iv_object_image);
        btn_QR =  findViewById(R.id.btn_QR);
        btn_else =  findViewById(R.id.btn_else);
        btn_save =  findViewById(R.id.btn_save);
        btn_return = findViewById(R.id.btn_return);

    }

//    //验证用户是否按要求输入了数据
//    private boolean checkUserInput() { // 物品名称，保质期天数,生产日期
//        String ObjectName = et_object_name.getText().toString();
//        String guaranteeDay = et_guarantee_date.getText().toString();
//        Date ProduceDate = (Date) et_produce_date.getText();
//
//        String message = null;
//        View invadView = null;
//        if (name.trim().length() == 0) {
//            message = "请输入姓名！";
//            invadView = nameText;
//        } else if (age.trim().length() == 0) {
//            message = "请输入年龄！";
//            invadView = ageText;
//        } else if (id == -1) {
//            message = "请选择性别！";
//        }
//        if (message != null) {
//            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//            if (invadView != null)
//                invadView.requestFocus();
//            return false;
//        }         return true;
//    }





}