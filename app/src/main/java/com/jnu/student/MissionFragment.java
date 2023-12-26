package com.jnu.student;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.jnu.student.data.FragmentAdapter;
import com.jnu.student.data.MissionAdapter;
import com.jnu.student.data.Points;

public class MissionFragment extends Fragment {

    static ViewPager2 viewPager;
    private FragmentAdapter fragmentAdapter;
    public MissionFragment() {
        // Required empty public constructor
    }

    private String []tabHeaderStrings = {"每日任务","每周任务","普通任务"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_mission, container, false);
        // 获取ViewPager2和TabLayout的实例
        viewPager = rootView.findViewById(R.id.view_pager);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);
        // 创建适配器

        fragmentAdapter = new FragmentAdapter(requireActivity().getSupportFragmentManager(), getLifecycle());
        viewPager.setAdapter(fragmentAdapter);


        // 将TabLayout和ViewPager2进行关联
        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(tabHeaderStrings[position])
        ).attach();


        return rootView;
    }
}