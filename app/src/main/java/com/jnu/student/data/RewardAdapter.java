package com.jnu.student.data;

import android.annotation.SuppressLint;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jnu.student.DailyTasksFragment;
import com.jnu.student.NormalTasksFragment;
import com.jnu.student.R;
import com.jnu.student.RewardFragment;
import com.jnu.student.WeeklyTasksFragment;

import java.util.ArrayList;

public class RewardAdapter extends RecyclerView.Adapter<RewardAdapter.ViewHolder> {
    private ArrayList<Reward> rewardArrayList;
    private static SignalListener signalListener;
    public interface SignalListener {
        void onSignalReceived();
    }
    public void setSignalListener(SignalListener listener) {
        signalListener = listener;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private final CheckBox checkBox;
        private final TextView textViewName;
        private final TextView textViewPoint;
        private final TextView textViewTimes;
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        }
        public ViewHolder(View rewardView) {
            super(rewardView);
            checkBox = itemView.findViewById(R.id.checkbox_task);
            textViewName = rewardView.findViewById(R.id.text_view_reward_title);
            textViewPoint = rewardView.findViewById(R.id.textview_point);
            textViewTimes = itemView.findViewById(R.id.textView_times);

            rewardView.setOnCreateContextMenuListener(this);
        }

        public TextView getTextViewName() {
            return textViewName;
        }
        public TextView getTextViewPoint() {return textViewPoint;}
        public TextView getTextViewTimes() {return textViewTimes;}

    }
    public RewardAdapter(ArrayList<Reward> rewards) {
        this.rewardArrayList = rewards;
    }

    @Override
    public RewardAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reward, viewGroup, false);
        return new RewardAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.getTextViewName().setText(rewardArrayList.get(position).getName());
        viewHolder.getTextViewPoint().setText("-" + rewardArrayList.get(position).getPoint());
        if (rewardArrayList.get(position).getType() == 0) {
            viewHolder.getTextViewTimes().setText(rewardArrayList.get(position).getComplete() + "/1");
        } else {
            viewHolder.getTextViewTimes().setText(rewardArrayList.get(position).getComplete() + "/∞");
        }


        viewHolder.checkBox.setChecked(false);
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkBox.isChecked()){
                    Points.points = Points.points - Integer.parseInt(String.valueOf(rewardArrayList.get(position).getPoint()));
                    rewardArrayList.get(position).setComplete(rewardArrayList.get(position).getComplete()+1);
                }
                else {
                    Points.points = Points.points + Integer.parseInt(String.valueOf(rewardArrayList.get(position).getPoint()));
                    rewardArrayList.get(position).setComplete(rewardArrayList.get(position).getComplete()-1);
                }
                if (signalListener != null) {
                    signalListener.onSignalReceived();
                }
                DailyTasksFragment.pointTextView.setText(String.valueOf(Points.points));
                WeeklyTasksFragment.pointTextView.setText(String.valueOf(Points.points));
                NormalTasksFragment.pointTextView.setText(String.valueOf(Points.points));
                RewardFragment.pointTextView.setText(String.valueOf(Points.points));
                // 处理单次奖励的删除
                if (rewardArrayList.get(position).getType() == 0) {
                    viewHolder.getTextViewTimes().setText(rewardArrayList.get(position).getComplete() + "/1");
                    rewardArrayList.remove(position);
                    notifyItemRangeChanged(position, rewardArrayList.size() - position);
                }
                // 更新无限奖励的完成状态
                else if (rewardArrayList.get(position).getType() == 1) {
                    viewHolder.checkBox.setChecked(false);
                    viewHolder.getTextViewTimes().setText(rewardArrayList.get(position).getComplete() + "/∞");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return rewardArrayList.size();
    }
}
