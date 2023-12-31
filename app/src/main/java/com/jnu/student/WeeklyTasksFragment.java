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

import com.jnu.student.data.DataBankMission;
import com.jnu.student.data.Mission;
import com.jnu.student.data.MissionAdapter;
import com.jnu.student.data.Points;

import java.util.ArrayList;

public class WeeklyTasksFragment extends Fragment {

    public WeeklyTasksFragment() {
        // Required empty public constructor
    }

    public static WeeklyTasksFragment newInstance() {
        WeeklyTasksFragment fragment = new WeeklyTasksFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    private ArrayList<Mission> weeklyTasks = new ArrayList<>();
    private MissionAdapter missionAdapter;
    public static TextView pointTextView;
    ActivityResultLauncher<Intent> addItemLauncher;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_weekly_tasks, container, false);
        RecyclerView mainRecyclerView = rootView.findViewById(R.id.recycle_view_missions);
        mainRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));

        weeklyTasks = new DataBankMission().LoadMissions(requireActivity());
        if(0 == weeklyTasks.size()) {
            weeklyTasks.add(new Mission("整理房间", 70, 1));
        }

        // 创建并设置适配器
        missionAdapter = new MissionAdapter(weeklyTasks);
        mainRecyclerView.setAdapter(missionAdapter);
        missionAdapter.setSignalListener(this::onSignalReceived);

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
                            int point = data.getIntExtra("point", 0);
                            int times = data.getIntExtra("times", 1);

                            weeklyTasks.add(new Mission(name, point, times));
                            missionAdapter.notifyItemInserted(weeklyTasks.size());
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
        inflater.inflate(R.menu.menu_new_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_new_task) {
            Intent intent = new Intent(this.getActivity(), NewMissionActivity.class);
            addItemLauncher.launch(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case 0:
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle("Delete Mission");
                builder.setMessage("Are you sure you want to delete this Mission?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        weeklyTasks.remove(item.getOrder());
                        missionAdapter.notifyItemRemoved(item.getOrder());

                        new DataBankMission().SaveMissions(requireActivity(), weeklyTasks);
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
    public void onSignalReceived() {
        pointTextView.setText(String.valueOf(Points.points));
    }


}