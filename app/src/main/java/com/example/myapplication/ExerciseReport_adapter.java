package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseReport_adapter extends RecyclerView.Adapter<ExerciseReport_adapter.ReportViewHolder> {


    private ArrayList<ExerciseReport_data> exerciseReport_ArrayList;
    private Context context; //ExerciseReportCalendarActivity에서 this로 받아옴


    public ExerciseReport_adapter(ArrayList<ExerciseReport_data> exerciseReport_ArrayList, Context context) {
        this.exerciseReport_ArrayList = exerciseReport_ArrayList;
        this.context = context;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        private TextView report_date;
        private TextView report_title;
        private TextView report_level;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            report_date = itemView.findViewById(R.id.report_date);
            report_title = itemView.findViewById(R.id.report_title);
            report_level = itemView.findViewById(R.id.report_level);

        }
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_reoirt_item,parent,false);
        ReportViewHolder holder = new ReportViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, int position) {
        holder.report_date.setText(exerciseReport_ArrayList.get(position).getReport_date());
        holder.report_title.setText(exerciseReport_ArrayList.get(position).getReport_title());
        holder.report_level.setText(exerciseReport_ArrayList.get(position).getReport_level());
        holder.itemView.setTag(position);

    }

    @Override
    public int getItemCount() {
        return (exerciseReport_ArrayList != null ? exerciseReport_ArrayList.size() : 0); // exerciseReport_ArrayList가 null이 아니면 리스트 사이즈 만큼 반환, 아니면 0을 반환
    }


}
