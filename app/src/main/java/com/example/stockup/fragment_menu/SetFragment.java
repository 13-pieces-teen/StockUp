package com.example.stockup.fragment_menu;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stockup.MainActivity;
import com.example.stockup.NEVER;
import com.example.stockup.R;
import com.example.stockup.add_stock;

public class SetFragment extends Fragment {

    private TextView tv_group;//开发人员
    private TextView tv_feedback;//反馈
    private TextView tv_egg;//小彩蛋

    public SetFragment() {
        // Required empty public constructor

    }


    public static SetFragment newInstance(String param1, String param2) {
        SetFragment fragment = new SetFragment();


        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_set, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        initView();
        MutiClick();
        tv_feedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //此处要想在fragment里添加弹窗，需要把context里的内容改成getActivity()
                AlertDialog textTips = new AlertDialog.Builder(getActivity())
                        .setTitle("意见与反馈：")
                        .setMessage("这是我们两人团队独立制作的第一个完整APP，还有许多未完善的部分\n" +
                                "如果遇到功能上的恶性BUG或有其他想法建议可以联系QQ：2019177515，我们会在空闲时间更新版本\n" +
                                "感谢你的体验和反馈")
                        .create();
                textTips.show();
            }
        });

        tv_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog textTips = new AlertDialog.Builder(getActivity())
                        .setTitle("开发人员：")
                        .setMessage("由刘莹与黄涵两人独立自主设计APP内容需求并自主完善APP功能\n" +
                                "授课指导老师：赵艳红\n" +
                                "来自南京工业大学")
                        .create();
                textTips.show();
            }
        });


    }

    private void initView() {
        tv_feedback = getView().findViewById(R.id.tv_feedback);
        tv_group = getView().findViewById(R.id.tv_group);
        tv_egg = getView().findViewById(R.id.tv_egg);
    }


    // 多击事件（点击13次触发彩蛋）
    //将每次的点击事件依次存放到一个定长的容器中。
    //再对比第一次跟最后一次之间的时间差，如果满足一定的条件的。就认为是多击事件。
    private void MutiClick() {
        final long[] mHits = new long[13];
        tv_egg.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.arraycopy(mHits, 1, mHits, 0, mHits.length - 1);
                mHits[mHits.length - 1] = SystemClock.uptimeMillis();
                if (mHits[0] >= (SystemClock.uptimeMillis() - 5000)) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), NEVER.class);//this前面为当前activty名称，class前面为要跳转到得activity名称
                    startActivity(intent);
                }
            }
        });
    }

}