package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Dm_adapter extends RecyclerView.Adapter<Dm_adapter.DmViewHolder> {
    private ArrayList<Dm_data> dataArrayList;
    final static int send = 1000;
    final static int receive = 1001;


    public Dm_adapter(ArrayList<Dm_data> dataArrayList) {
        this.dataArrayList = dataArrayList;
    } //ArrayList 데이터 타입을 Dm_data으로 한다 (Dm_data는 클래스명)

    public class DmViewHolder extends RecyclerView.ViewHolder {
        protected TextView my_dmtext;
        TextView my_dmtime;

        public DmViewHolder(@NonNull View itemView) {
            super(itemView);
            my_dmtext = itemView.findViewById(R.id.my_dmtext);
            my_dmtime = itemView.findViewById(R.id.my_dmtime);
        }
    }

    @NonNull
    @Override
    public DmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) { //dm_itm뷰를 객체로 만듦
        View view = null;
        if (viewType == send) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dm_item, parent, false);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dm_item2, parent, false);
        }
        DmViewHolder holder = new DmViewHolder(view);
        return holder;
    }



    @Override
    public int getItemViewType(int position) {
        if (dataArrayList.get(position).getItemViewType()==true) { //dataArrayList.get(position).getItemViewType() 이 true 이면 view타입을 send로 한다
            return send;
        }
        return receive;
//        return super.getItemViewType(position);

    }

    @Override
    public void onBindViewHolder(@NonNull DmViewHolder holder, int position) {
        holder.my_dmtext.setText(dataArrayList.get(position).getDm_content()); //my_dmtext에 list의 해당 포지션에 해당 content 내용을 담는다
        holder.my_dmtime.setText(dataArrayList.get(position).getDm_time());
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() { //dataArrayList가 null값이 아니면 size만큼 반환, null값이면 0을 반환
        return (dataArrayList != null ? dataArrayList.size() : 0);
    }


}
