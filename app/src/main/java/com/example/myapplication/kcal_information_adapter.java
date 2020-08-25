package com.example.myapplication;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;

public class kcal_information_adapter extends RecyclerView.Adapter<kcal_information_adapter.kcalViewHolder> {

    private ArrayList<kcal_information_data> kcalArrayList;
    private Context context; //kcal_informationActivity 에서 this로 받아옴

    public kcal_information_adapter(ArrayList<kcal_information_data> kcalArrayList, Context context) {
        this.kcalArrayList = kcalArrayList;
        this.context = context;
        Fresco.initialize(context); //Fresco를 이 화면에서 사용하겠다
    }


    public class kcalViewHolder extends RecyclerView.ViewHolder {

        private SimpleDraweeView kcl_image_item;
        private TextView kcl_title_item;
        private TextView kcl_contents_item;

        public kcalViewHolder(@NonNull View itemView) {
            super(itemView);
            kcl_image_item = itemView.findViewById(R.id.kcl_image_item);
            kcl_title_item = itemView.findViewById(R.id.kcl_title_item);
            kcl_contents_item = itemView.findViewById(R.id.kcl_contents_item);
        }
    }

    @NonNull
    @Override
    public kcalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.kcal_information_item,parent,false);
        kcalViewHolder holder = new kcalViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull kcalViewHolder holder, int position) {



//        Log.e("아답터 안에서 확인",kcalArrayList.get(position).getTitle());
        Uri uri = Uri.parse(kcalArrayList.get(position).getThumbnail()); //fresco를 이용하여 이미지 보여주기
        holder.kcl_image_item.setImageURI(uri);

        holder.kcl_title_item.setText(kcalArrayList.get(position).getTitle());
        holder.kcl_contents_item.setText(kcalArrayList.get(position).getDescription());

    }

    @Override
    public int getItemCount() {
        return  (kcalArrayList != null ? kcalArrayList.size() : 0);
    }


}
