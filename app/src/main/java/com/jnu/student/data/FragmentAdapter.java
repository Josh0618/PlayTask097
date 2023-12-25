package com.jnu.student.data;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.jnu.student.DailyTasksFragment;

public class FragmentAdapter extends FragmentStateAdapter {
    private static final int NUM_TABS = 1;
    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        // 根据位置返回对应的Fragment实例
        switch (position) {
            case 0:
                return new DailyTasksFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return NUM_TABS;
    }
}
