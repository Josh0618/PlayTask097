package com.jnu.student;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jnu.student.data.DataBankReward;
import com.jnu.student.data.Points;
import com.jnu.student.data.Reward;
import com.jnu.student.data.RewardAdapter;

import java.util.ArrayList;


public class RewardFragment extends Fragment {

    public RewardFragment() {
        // Required empty public constructor
    }
    public static RewardFragment newInstance() {
        RewardFragment fragment = new RewardFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    private ArrayList<Reward> rewards = new ArrayList<>();
    private RewardAdapter rewardAdapter;
    public static TextView pointTextView;
    ActivityResultLauncher<Intent> addItemLauncher;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView=inflater.inflate(R.layout.fragment_reward, container, false);
        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recycle_view_rewards);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        rewards = new DataBankReward().LoadRewards(requireActivity());
        if(0 == rewards.size()) {
            rewards.add(new Reward("玩两个小时游戏", 100, 1));
            rewards.add(new Reward("看两个小时电影", 100, 0));
        }

        // 创建并设置适配器
        rewardAdapter = new RewardAdapter(rewards);
        mainRecyclerView.setAdapter(rewardAdapter);
        rewardAdapter.setSignalListener(this::onSignalReceived);

        // 在 RecyclerView 上注册上下文菜单
        registerForContextMenu(mainRecyclerView);

        pointTextView = rootView.findViewById(R.id.textView_points);
        pointTextView.setText(String.valueOf(Points.points));

        addItemLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data != null){
                            String name = data.getStringExtra("name");
                            int point = data.getIntExtra("point", 11);
                            int type = data.getIntExtra("type", 1);

                            rewards.add(new Reward(name, point, type));
                            rewardAdapter.notifyItemInserted(rewards.size());
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        // 处理取消操作
                    }
                }
        );

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_new_reward, menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_reward) {
            Intent intent = new Intent(this.getActivity(), NewRewardActivity.class);
            addItemLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void onSignalReceived() {
        pointTextView.setText(String.valueOf(Points.points));
    }
}