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
import com.jnu.student.WeeklyTasksFragment;

import java.util.ArrayList;


public class MissionAdapter extends RecyclerView.Adapter<MissionAdapter.ViewHolder>{
    private ArrayList<Mission> missionArrayList;
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

            menu.add(0, 0, this.getAdapterPosition(), "删除" + this.getAdapterPosition());
        }
        public ViewHolder(View missionView) {
            super(missionView);
            checkBox = itemView.findViewById(R.id.checkbox_task);
            textViewName = missionView.findViewById(R.id.text_view_mission_title);
            textViewPoint = missionView.findViewById(R.id.textview_point);
            textViewTimes = itemView.findViewById(R.id.textView_times);

            missionView.setOnCreateContextMenuListener(this);
        }

        public TextView getTextViewName() {
            return textViewName;
        }
        public TextView getTextViewPoint() {return textViewPoint;}
        public TextView getTextViewTimes() {return textViewTimes;}




    }
    public MissionAdapter(ArrayList<Mission> missions) {
        this.missionArrayList = missions;
    }

    @Override
    public MissionAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_mission, viewGroup, false);
        return new MissionAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, @SuppressLint("RecyclerView") final int position) {
        viewHolder.getTextViewName().setText(missionArrayList.get(position).getName());
        viewHolder.getTextViewPoint().setText("+" + missionArrayList.get(position).getPoint());
        viewHolder.getTextViewTimes().setText(missionArrayList.get(position).getComplete() +"/"+ missionArrayList.get(position).getTimes());

        viewHolder.checkBox.setChecked(missionArrayList.get(position).isCompleted());
        viewHolder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (viewHolder.checkBox.isChecked()){
                    Points.points = Points.points + Integer.parseInt(String.valueOf(missionArrayList.get(position).getPoint()));
                    missionArrayList.get(position).setComplete(missionArrayList.get(position).getComplete()+1);
                }
                else {
                    Points.points = Points.points - Integer.parseInt(String.valueOf(missionArrayList.get(position).getPoint()));
                    missionArrayList.get(position).setComplete(missionArrayList.get(position).getComplete()-1);
                }
                if (signalListener != null) {
                    signalListener.onSignalReceived();
                }
                DailyTasksFragment.pointTextView.setText(String.valueOf(Points.points));
                WeeklyTasksFragment.pointTextView.setText(String.valueOf(Points.points));
                NormalTasksFragment.pointTextView.setText(String.valueOf(Points.points));
                // 更新任务的完成状态
                viewHolder.checkBox.setChecked(missionArrayList.get(position).isCompleted());
                viewHolder.getTextViewTimes().setText(missionArrayList.get(position).getComplete() +"/"+ missionArrayList.get(position).getTimes());
            }
        });
    }

    @Override
    public int getItemCount() {
        return missionArrayList.size();
    }

}

