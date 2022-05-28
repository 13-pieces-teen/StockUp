package com.example.stockup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import ObjectDBHelper.ObjectDBHelper;


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
    private AddAndDecreaseButton btn_amount;//自定义的增减物品数量按钮

    private TextView tv_open_date;
    private TextView tv_remarks;//物品备注
    private TextView tv_percent;//进度条百分比
    private TextView tv_days;//距离到期(N)天
    private ImageView iv_object_image;
    private Spinner sp_object;//物品选择下拉框
    private SeekBar sb_remind;//seekbar日期进度条

    private Button btn_QR;//扫一扫
    private Button btn_else;//其他选项
    private Button btn_save;//保存按钮
    private Button btn_return;//返回按钮（返回一级菜单）
    private Button btn_clear;//重置按钮
    private Uri imageUri;

    private String objectType;//物品种类
    private String date;
    private String dateType;//年，月，日

    private int dayFlag;//默认选天
    private int guaranteeNumber;//保质天数
    private int objectAmount;//物品数量
    private int nowDay;//生产日期（天）
    private int nowMonth;//生产日期（月）
    private int nowYear;//生产日期（年）
    private int nextDay;//过期日期（天）
    private int nextMonth;//过期日期（月）
    private int nextYear;//过期日期（年）
    private int betweenDays;//间隔天数
    private ObjectDBHelper objectDBHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stock);

        initView();//初始化
        objectDBHelper = new ObjectDBHelper(this);//很重要，之前忘了实例化，空指针

        //Seekbar监听事件
        sb_remind.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //将seekBar的进度设置在textView上
                String ww = String.format("(%d%%)", progress);
                tv_percent.setText(ww);

                betweenDay();
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

        //spanner（物品种类的监听事件）
        sp_object.setSelection(0);//初始化，默认选择列表中第0个元素
        sp_object.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                //这个方法里可以对点击事件进行处理
                //i指的是点击的位置,通过i可以取到相应的数据源
                objectType=adapterView.getItemAtPosition(i).toString();//获取i所在的文本
                Toast.makeText(add_stock.this,objectType,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        //保质天数单选按钮组的监听事件
        DateGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
            // 选中状态改变时被触发
                switch (checkedId) {
                    case R.id.rb_day:
                        // 当用户选择“天”时
                        Log.i("DAY", "当前用户选择"+rb_day.getText().toString());
                        dayFlag = 0;
                        dateType = "天";
                        break;
                    case R.id.rb_month:
                        // 当用户选择“月”时
                        Log.i("MONTH", "当前用户选择"+rb_month.getText().toString());
                        dayFlag = 1;
                        dateType = "月";
                        break;
                    case R.id.rb_year:
                        // 当用户选择“年”时
                        Log.i("YEAR", "当前用户选择"+rb_year.getText().toString());
                        dayFlag = 2;
                        dateType = "年";
                        break;
                }
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
                                //把生产日期获取出来
                                nowDay = dayOfMonth;
                                nowMonth = month;
                                nowYear = year;
                                et_produce_date.setText(date);
                                //保质天数的获取
                                guaranteeNumber = Integer.parseInt(et_guarantee_date.getText().toString());
                                //获取生产日期
                                Calendar nowDate = Calendar.getInstance();
                                nowDate.set(nowYear,nowMonth,nowDay);
                                Calendar nextDate = nowDate;
                                //当前时间 加guaranteeNumber + radiogroup的选择（年/月/日）
                                if (dayFlag == 0)
                                {
                                    nextDate.add(Calendar.DAY_OF_MONTH, guaranteeNumber);
                                }else if (dayFlag == 1)
                                {
                                    nextDate.add(Calendar.MONTH, guaranteeNumber);
                                }else if (dayFlag == 2)
                                {
                                    nextDate.add(Calendar.YEAR, guaranteeNumber);
                                }
                                nextYear = nextDate.get(Calendar.YEAR);
                                nextMonth = nextDate.get(Calendar.MONTH);
                                nextDay = nextDate.get(Calendar.DAY_OF_MONTH);
                                String date2 = String.format("%d-%d-%d", nextYear, nextMonth + 1, nextDay);
                                et_after_date.setText(date2);
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

        //重置按钮的监听事件
        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_object_name.setText("");
                et_open_date.setText("");
                et_produce_date.setText("");
                et_remarks.setText("");
                et_guarantee_date.setText("");
                et_after_date.setText("");
                rb_day.setChecked(false);
                rb_month.setChecked(false);
                rb_year.setChecked(false);
            }
        });

        //保存按钮的监听事件
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!checkUIInput()) {// 界面输入验证
                    return;
                }
                objectInfo object1 = getObjectFromUI();
                // 插入数据库中
                String objectType = object1.getOB_type();
                long rowId;
                switch (objectType){
                    case "食品":
                        rowId = objectDBHelper.addFood(object1);
                        Log.i("数据库", "数据库加入食品");
                        break;
                    case "药品":
                        rowId = objectDBHelper.addDurg(object1);
                        Log.i("数据库", "数据库加入药品");
                        break;
                    case "化妆品":
                        rowId = objectDBHelper.addCosmetics(object1);
                        Log.i("数据库", "数据库加入化妆品");
                        break;
                    case "物资":
                        rowId = objectDBHelper.addSupplies(object1);
                        Log.i("数据库", "数据库加入物资");
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + objectType);
                }

                if (rowId != -1) {
                    Toast.makeText(add_stock.this, "添加成功！！！", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(add_stock.this, "添加失败！！！", Toast.LENGTH_SHORT).show();
                }

            }
        });
        //扫描条形码
        btn_QR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IntentIntegrator integrator = new IntentIntegrator(add_stock.this);
                // integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setDesiredBarcodeFormats(IntentIntegrator.ONE_D_CODE_TYPES);
                integrator.setPrompt("扫描条形码");
                integrator.setCameraId(0);
                integrator.setBeepEnabled(true);
                integrator.initiateScan();
            }
        });

    }


    //收集界面输入的数据，并将封装成objectInfo对象
    private objectInfo getObjectFromUI()
    {
        //物品名称，物品类别，保质天数，生产日期，过期日期，开封日期，备注，产品数量，间隔天数
        String objectName = et_object_name.getText().toString();
        //物品类别在全局变量里（objectType）
        //物品保质天数 （xx天/月/年）
        String objectGuarantee = String.format("%d%s",guaranteeNumber,dateType);
        String objectProduceDate = et_produce_date.getText().toString().trim();
        String objectAfterDate = et_after_date.getText().toString().trim();
        String objectOpenDate = et_open_date.getText().toString().trim();
        //间隔天数在全局变量（betweenDays）
        objectAmount = btn_amount.getAmount();
        String objectRemark = et_remarks.getText().toString().trim();

        objectInfo object = new objectInfo(objectName, objectType, objectGuarantee, objectProduceDate,
                objectAfterDate, objectOpenDate, objectRemark, objectAmount, betweenDays);
        return object;

    }


    //图像保存在页面上和返回json码
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
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (intentResult != null) {
            if (intentResult.getContents() == null) {
                Toast.makeText(this, "扫码失败", Toast.LENGTH_SHORT).show();
            } else {
                String result = intentResult.getContents();//返回值
                Toast.makeText(this, result, Toast.LENGTH_SHORT).show();
            }
        }
    }

    //自动计算过期日期(废案，会闪退)
    public void calculateDate() {
        //保质天数的获取
        guaranteeNumber = Integer.parseInt(et_guarantee_date.getText().toString());
        //获取生产日期
        Calendar nowDate = Calendar.getInstance();
        nowDate.set(nowYear,nowMonth,nowDay);
        Calendar nextDate = nowDate;

        //当前时间 加guaranteeNumber + radiogroup的选择（年/月/日）
        if (dayFlag == 0)
        {
            nextDate.add(Calendar.DAY_OF_MONTH, guaranteeNumber);
        }else if (dayFlag == 1)
        {
            nextDate.add(Calendar.MONTH, guaranteeNumber);
        }else if (dayFlag == 2)
        {
            nextDate.add(Calendar.YEAR, guaranteeNumber);
        }

        //new SimgpleDateFormat 进行格式化
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        //利用Calendar的getTime方法，将时间转化为Date对象
        Date date = (Date) nextDate.getTime();
        //利用SimpleDateFormat对象 把Date对象格式化
        String format = sdf.format(date);
        et_after_date.setText(format);

    }

    //计算间隔天数
    public int betweenDay()
    {
        Calendar a=Calendar.getInstance();
        Calendar b=Calendar.getInstance();
        a.set(nowYear,nowMonth,nowDay);
        b.set(nextYear,nextMonth,nextDay);;
        //计算此日期是一年中的哪一天
        int day1=a.get(Calendar.DAY_OF_YEAR);
        int day2=b.get(Calendar.DAY_OF_YEAR);
        betweenDays = day2-day1;
        return betweenDays;
    }


    private void initView() {
        et_object_name =  findViewById(R.id.et_object_name);
        et_guarantee_date =  findViewById(R.id.et_guarantee_date);
        et_produce_date =  findViewById(R.id.et_produce_date);
        et_after_date =  findViewById(R.id.et_after_date);
        et_open_date = findViewById(R.id.et_open_date);
        et_remarks = findViewById(R.id.et_remarks);

        DateGroup = findViewById(R.id.rg_date);
        sb_remind = findViewById(R.id.sb_remind);
        rb_day = findViewById(R.id.rb_day);
        rb_month = findViewById(R.id.rb_month);
        rb_year = findViewById(R.id.rb_year);

        btn_amount = findViewById(R.id.btn_amount);
        sp_object = findViewById(R.id.sp_object);
        tv_open_date = findViewById(R.id.tv_open_date);
        tv_remarks = findViewById(R.id.tv_remarks);
        tv_percent = findViewById(R.id.tv_percent);
        tv_days = findViewById(R.id.tv_days);
        iv_object_image =  findViewById(R.id.iv_object_image);
        btn_QR =  findViewById(R.id.btn_QR);
        btn_else =  findViewById(R.id.btn_else);
        btn_save =  findViewById(R.id.btn_save);
        btn_return = findViewById(R.id.btn_return);
        btn_clear = findViewById(R.id.btn_clear);
    }


    //验证用户是否按要求输入了数据,若不符合不得保存
    private boolean checkUIInput() { // 物品名称，保质期天数,生产日期,保质旋钮
        String objectName = et_object_name.getText().toString();
        //物品类别在全局变量里（objectType）
        String objectProduceDate = et_produce_date.getText().toString().trim();

        String message = null;
        View invadView = null;
        if (objectName.trim().length() == 0) {
            message = "请输入物品名称！";
            invadView = et_object_name;
        } else if (guaranteeNumber == 0) {
            message = "请输入保质天数！";
            invadView = et_guarantee_date;
        } else if (dayFlag == -1) {
            message = "未选择保质年/月/日！";
        }else if (objectProduceDate.length() == 0)
        {
            message = "未填入生产日期！";
            invadView = et_produce_date;
        }

        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            if (invadView != null)
                invadView.requestFocus();
            return false;
        }         return true;
    }




}