package com.example.stockup;

import static com.google.zxing.integration.android.IntentIntegrator.REQUEST_CODE;

import android.app.DatePickerDialog;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.stockup.entity.objectInfo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import ObjectDBHelper.ObjectDBHelper;


public class add_stock extends AppCompatActivity {

    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_PHOTO=2;
    String index = "";		//	作为拍照还是相册的标识
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
    String UriInfo = "";		//	uri变成字符串

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
        objectDBHelper = new ObjectDBHelper(this);//很重要，之前忘了实例化，会导致空指针

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

                //          获取本地时间，作为图片的名字，防止拍了多张照片时，出现图片覆盖导致之前图片消失的问题
                SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日HH:mm:ss");
                Date curDate = new Date(System.currentTimeMillis());
                String str = format.format(curDate);

                // 创建File对象，用于存储拍照后的图片
                // 将图片命名为output_image.jpg，并将它存放在手机SD卡的应用关联缓存目录下
                // getExternalCacheDir()可以得到这个目录
                File output = new File(getExternalCacheDir(),str+".jpg");
                try {
                    if (output.exists()) {  //  检查与File对象相连接的文件和目录是否存在于磁盘中
                        output.delete();    //  删除与File对象相连接的文件和目录
                    }
                    output.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= 24) {
                    /**
                     *          将File对象转换成一个封装过的Uri对象
                     *          第一个参数：  要求传入Context参数
                     *          第二个参数：  可以是任意唯一的字符串
                     *          第三个参数：  我们刚刚创建的File对象
                     */
                    imageUri = FileProvider.getUriForFile(add_stock.this,
                            "com.example.stockup.fileprovider", output);
                } else {
                    //  如果运行设备的系统版本低于 Android7.0
                    //  将File对象转换成Uri对象，这个Uri对象表示着output_image.jpg 这张图片的本地真实路径
                    imageUri = Uri.fromFile(output);
                }
                /**
                 *      启动相机程序
                 */
                //  将Intent的action指定为 拍照到指定目录 —— android.media.action.IMAGE_CAPTURE
                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                //  指定图片的输出地址
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //  把背景图片撤掉
                iv_object_image.setBackground(null);
                //  在通过startActivityForResult()，来启动活动，因此拍完照后会有结果返回到 onActivityResult()方法中
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
                        addWhole(object1);
                        Log.i("数据库", "数据库加入食品");
                        break;
                    case "药品":
                        rowId = objectDBHelper.addDurg(object1);
                        addWhole(object1);
                        Log.i("数据库", "数据库加入药品");
                        break;
                    case "化妆品":
                        rowId = objectDBHelper.addCosmetics(object1);
                        addWhole(object1);
                        Log.i("数据库", "数据库加入化妆品");
                        break;
                    case "物资":
                        rowId = objectDBHelper.addSupplies(object1);
                        addWhole(object1);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    // 使用try让程序运行在内报错
                    try {
                        //  根据Uri找到这张照片的位置，将它解析成Bitmap对象，然后将把它设置到imageView中显示出来
                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                        UriInfo = ""+imageUri;
                        index = "2";
                        iv_object_image.setImageBitmap(bitmap);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                /**
                 *          之所以这样做是是因为，Android 系统从4.4版本开始，选取相册图片不再返回图片真实的Uri了，而是一个封装过的Uri，因此
                 *          如果是4.4版本以上的手机就需要对这个Uri进行解析才行
                 */
                if (resultCode == RESULT_OK){
                    if (Build.VERSION.SDK_INT  >= 19){      //  如果是在4.4及以 上 系统的手机就调用该方法来处理图片
                        handleImageOnKitKat(data);
                    }else{
                        handleImageBeforeKitKat(data);      //  如果是在4.4以 下 系统的手机就调用该方法来处理图片
                    }
                }
                //REQUEST_CODE为扫一扫的
            case REQUEST_CODE:
                IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
                if (intentResult != null) {
                    if (intentResult.getContents() == null) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String object_name = doGet_json.get_jsonname("6938803995169");
                                Message retname = new Message();
                                retname.what = 0;
                                retname.obj = object_name;
                                mhandler.sendMessage(retname);

                            }
                        }).start();
                        Toast.makeText(this, "扫码失败", Toast.LENGTH_SHORT).show();
                        ;
                    }

                } else {
                    String object_Json = intentResult.getContents();//返回值
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            String object_name = doGet_json.get_jsonname(object_Json);
                            Message retname = new Message();
                            retname.what = 0;
                            retname.obj = object_name;
                            mhandler.sendMessage(retname);

                        }
                    }).start();
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
    //自动补全名称
    private Handler mhandler =new Handler(Looper.myLooper()){
        @Override
        public void handleMessage(@NonNull Message msg){
            super.handleMessage(msg);
            if(msg.what==0){
                String ob_name=(String) msg.obj;
                if(ob_name=="暂未收录此商品") Toast.makeText(add_stock.this, ob_name, Toast.LENGTH_SHORT).show();
                else et_object_name.setText(ob_name);
            }
        }

    };


    /**
     *          如何解析这个封装过的Uri
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void handleImageOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        if (DocumentsContract.isDocumentUri(this,uri)){
            //  如果返回的Uri是 document 类型的话，那就取出 document id 进行处理
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];        //  解析出数字格式id
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            //  如果是 content 类型的Uri，则使用普通方式处理
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            //  如果是 file 类型的 Uri ，直接获取图片路径即可
            imagePath = uri.getPath();
        }
        displayImage(imagePath);        //  拿到图片路径后，在调用 displayImage() 方法将图片显示到界面上
    }


    private void handleImageBeforeKitKat(Intent data) {
        Uri uri = data.getData();
        String imagePath = getImagePath(uri,null);
        displayImage(imagePath);
    }


    private void displayImage(String imagePath) {
        if (imagePath != null){
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            index = "1";
            UriInfo = imagePath;
            iv_object_image.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private void openAlbum() {
        /**
         *      启动相册程序
         */
        //  action —— android.intent.action.ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        //  该函数表示要查找文件的mime类型（如*/*），这个和组件在manifest里定义的相对应，但在源代码里
        intent.setType("image/*");
        startActivityForResult(intent,CHOOSE_PHOTO);    //打开相册,用自定义常量 —— CHOOSE_PHOTO来作为case处理图片的标识
    }

    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if(cursor != null){
            if(cursor.moveToFirst()){
                Log.i("CURSOR", "cursor.getColumnIndex(MediaStore.Images.Media.DATA:" + cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openAlbum();
                } else {
                    Toast.makeText(this, "You denied the permission", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }


    //添加基本物品信息
    public void addWhole(objectInfo OB)
    {
        ObjectDBHelper objectDBHelper1 = new ObjectDBHelper(add_stock.this, "objectManager.db", null,1);
        SQLiteDatabase db = objectDBHelper1.getReadableDatabase();
        ContentValues values = new ContentValues();

        values.put("OB_json","");
        values.put("OB_name",OB.getOB_name());
        values.put("OB_guarantee_day",OB.getOB_guarantee_day());
        values.put("OB_URL",UriInfo);

        db.insert("whole",null,values);
        db.close();
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
        //计算年数
        int year1 = a.get(Calendar.YEAR);
        int year2 = b.get(Calendar.YEAR);
        if(year1!=year2){
            for(int i = year1 ; i < year2 ; i ++)
            {
                if(i%4==0 && i%100!=0 || i%400==0) //闰年
                    day2 += 366;
                else //不是闰年
                    day2+= 365;
            }
            betweenDays = day2-day1;
            return betweenDays;
        }
        else {
            betweenDays = day2-day1;
            return betweenDays;
        }
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