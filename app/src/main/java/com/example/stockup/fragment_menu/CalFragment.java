package com.example.stockup.fragment_menu;

import static java.lang.Integer.parseInt;
import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockup.Adapter.recyclerViewAdapter;
import com.example.stockup.R;
import com.example.stockup.entity.objectInfo;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ObjectDBHelper.ObjectDBHelper;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalFragment extends Fragment implements CalendarView.OnYearChangeListener, CalendarView.OnCalendarSelectListener {
    CalendarView calendarview;
    TextView mTextMonthDay;
    TextView mTextYear;
    TextView mTextLunar;
    TextView mTextCurrentDay;
    CalendarLayout mCalendarLayout;
    FrameLayout fl_current;
    RecyclerView recyclerView;
    recyclerViewAdapter recyadapter;
    ImageView btn_fresh;
    private String date;
    private int mYear;
    private ObjectDBHelper objectDBHelper;

    //当日过期的东西
    private List<objectInfo> Deadline_obj= new ArrayList<objectInfo>();

    public CalFragment() {
        // Required empty public constructor
    }


    public static CalFragment newInstance(String param1, String param2) {
        CalFragment fragment = new CalFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        objectDBHelper = new ObjectDBHelper(getActivity());//很重要，之前忘了实例化，会导致空指针
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_cal, container, false);
        iniview(v);
        try {
            intidate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        inilist();
        //日历
        calendarview.setOnCalendarSelectListener(this);
        calendarview.setOnYearChangeListener(this);
        mTextMonthDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mCalendarLayout.isExpand()) {
                    mCalendarLayout.expand();
                    return;
                }
                calendarview.showYearSelectLayout(mYear);
                mTextLunar.setVisibility(View.GONE);
                mTextYear.setVisibility(View.GONE);
                mTextMonthDay.setText(valueOf(mYear));
            }
        });
        fl_current.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarview.scrollToCurrent();
            }
        });
        calendarview.setOnCalendarSelectListener(new CalendarView.OnCalendarSelectListener() {
            @Override
            public void onCalendarOutOfRange(Calendar calendar) {

            }

            @Override
            public void onCalendarSelect(Calendar calendar, boolean isClick) {
                int day = calendar.getDay();
                int month = calendar.getMonth();
                int year = calendar.getYear();
                date = year + "-" + (month) + "-" + day;
                inilist();
                recyadapter = new recyclerViewAdapter(getActivity(),Deadline_obj);
                recyclerView.setAdapter(recyadapter);
               //Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
            }
        });

        //recyview 设置
        //创建新的recyadapter
        recyadapter = new recyclerViewAdapter(getActivity(),Deadline_obj);
        //
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //设置分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        //设置适配器
        recyclerView.setAdapter(recyadapter);

        //刷新按钮
        btn_fresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calendarview.update();
            }
        });

    }

    private void inilist() {
        Deadline_obj=objectDBHelper.getFoodInfo_after(date);
        Deadline_obj.addAll(objectDBHelper.getCosmeticsInfo_after(date));
        Deadline_obj.addAll(objectDBHelper.getDrupInfo_after(date));
        Deadline_obj.addAll(objectDBHelper.getSuppliesInfo_after(date));
    }

    private void intidate() throws ParseException {
        mYear = calendarview.getCurYear();
        mTextYear.setText(String.valueOf(mYear));
        mTextMonthDay.setText(calendarview.getCurMonth() + "月" + calendarview.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(valueOf(calendarview.getCurDay()));
        date=calendarview.getCurYear()+"-"+calendarview.getCurMonth()+"-"+calendarview.getCurDay();

        //日历标记
        List<objectInfo> food_obj= new ArrayList<objectInfo>();
        List<objectInfo> drug_obj= new ArrayList<objectInfo>();
        List<objectInfo> supplies_obj= new ArrayList<objectInfo>();
        List<objectInfo> cosmetics_obj= new ArrayList<objectInfo>();
        food_obj=objectDBHelper.getFoodInfo();
        drug_obj=objectDBHelper.getDurgInfo();
        supplies_obj=objectDBHelper.getSuppliesInfo();
        cosmetics_obj=objectDBHelper.getCosmeticsInfo();
        Map<String, Calendar> map = new HashMap<>();
        for (int i = 0; i < food_obj.size(); i++) {
            objectInfo temp=(objectInfo)food_obj.get(i);
            String temps=temp.getOB_after_date();
            String[] tempdate=temps.split("-");
            map.put(getSchemeCalendar(parseInt(tempdate[0]),parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFF40db25, "食").toString(),
                    getSchemeCalendar(parseInt(tempdate[0]), parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFF40db25, "食"));

        }
        for (int i = 0; i < drug_obj.size(); i++) {
            objectInfo temp=(objectInfo)drug_obj.get(i);
            String temps=temp.getOB_after_date();
            String[] tempdate=temps.split("-");
            map.put(getSchemeCalendar(parseInt(tempdate[0]),parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFFdf1356, "药").toString(),
                    getSchemeCalendar(parseInt(tempdate[0]), parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFFdf1356, "药"));

        }
        for (int i = 0; i < supplies_obj.size(); i++) {
            objectInfo temp=(objectInfo)supplies_obj.get(i);
            String temps=temp.getOB_after_date();
            String[] tempdate=temps.split("-");
            map.put(getSchemeCalendar(parseInt(tempdate[0]),parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFF0099FF, "物").toString(),
                    getSchemeCalendar(parseInt(tempdate[0]), parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFF0099FF, "物"));

        }
        for (int i = 0; i < cosmetics_obj.size(); i++) {
            objectInfo temp=(objectInfo)cosmetics_obj.get(i);
            String temps=temp.getOB_after_date();
            String[] tempdate=temps.split("-");
            map.put(getSchemeCalendar(parseInt(tempdate[0]),parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFFbc13f0, "妆").toString(),
                    getSchemeCalendar(parseInt(tempdate[0]), parseInt(tempdate[1]), parseInt(tempdate[2]), 0xFFbc13f0, "妆"));

        }
        calendarview.setSchemeDate(map);






    }
    private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        return calendar;
    }

    private void iniview(View v) {
        calendarview = v.findViewById(R.id.calendarView);
        mTextMonthDay = v.findViewById(R.id.tv_month_day);
        mTextYear = v.findViewById(R.id.tv_year);
        mTextLunar = v.findViewById(R.id.tv_lunar);
        mTextCurrentDay = v.findViewById(R.id.tv_current_day);
        mCalendarLayout=v.findViewById(R.id.calendarLayout);
        fl_current=v.findViewById(R.id.fl_current);
        recyclerView=v.findViewById(R.id.recyclerView);
        btn_fresh=v.findViewById(R.id.image_fresh);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {

    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        mTextLunar.setVisibility(View.VISIBLE);
        mTextYear.setVisibility(View.VISIBLE);
        mTextMonthDay.setText(calendar.getMonth() + "月" + calendar.getDay() + "日");
        mTextYear.setText(String.valueOf(calendar.getYear()));
        mTextLunar.setText(calendar.getLunar());
        mYear = calendar.getYear();
    }

    @Override
    public void onYearChange(int year) {
        mTextMonthDay.setText(String.valueOf(year));
    }
}