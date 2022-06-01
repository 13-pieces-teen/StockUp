package com.example.stockup.fragment_menu;

import static java.lang.String.valueOf;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockup.R;
import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarLayout;
import com.haibin.calendarview.CalendarView;


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
    private int mYear;
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cal, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        iniview();
        intidate();
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
                String date = year + "年" + (month) + "月" + day+"日";
                Toast.makeText(getActivity(), date, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void intidate() {
        mYear = calendarview.getCurYear();
        mTextYear.setText(String.valueOf(mYear));
        mTextMonthDay.setText(calendarview.getCurMonth() + "月" + calendarview.getCurDay() + "日");
        mTextLunar.setText("今日");
        mTextCurrentDay.setText(valueOf(calendarview.getCurDay()));


        /*
        添加标记
        Map<String, Calendar> map = new HashMap<>();
        map.put(getSchemeCalendar(year, month, 3, 0xFF40db25, "假").toString(),
                getSchemeCalendar(year, month, 3, 0xFF40db25, "假"));
        map.put(getSchemeCalendar(year, month, 6, 0xFFe69138, "事").toString(),
                getSchemeCalendar(year, month, 6, 0xFFe69138, "事"));
        private Calendar getSchemeCalendar(int year, int month, int day, int color, String text) {
        Calendar calendar = new Calendar();
        calendar.setYear(year);
        calendar.setMonth(month);
        calendar.setDay(day);
        calendar.setSchemeColor(color);//如果单独标记颜色、则会使用这个颜色
        calendar.setScheme(text);
        calendar.addScheme(new Calendar.Scheme());
        calendar.addScheme(0xFF008800, "假");
        calendar.addScheme(0xFF008800, "节");
        return calendar;
    }
         */
    }

    private void iniview() {
        calendarview = getView().findViewById(R.id.calendarView);
        mTextMonthDay = getView().findViewById(R.id.tv_month_day);
        mTextYear = getView().findViewById(R.id.tv_year);
        mTextLunar = getView().findViewById(R.id.tv_lunar);
        mTextCurrentDay = getView().findViewById(R.id.tv_current_day);
        mCalendarLayout=getView().findViewById(R.id.calendarLayout);
        fl_current=getView().findViewById(R.id.fl_current);
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