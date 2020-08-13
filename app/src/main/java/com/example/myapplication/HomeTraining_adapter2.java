package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class HomeTraining_adapter2 extends RecyclerView.Adapter<HomeTraining_adapter2.HomeTrainingViewHolder> {
    private ArrayList<HomeTraining_data> HomeTrainingArrayList;
    private Context context;

    public HomeTraining_adapter2(ArrayList<HomeTraining_data> homeTrainingArrayList, Context context) {
        this.HomeTrainingArrayList = homeTrainingArrayList;
        this.context = context;
    }

    public class HomeTrainingViewHolder extends RecyclerView.ViewHolder {

        ImageView thumbnail_image_item;
        ImageView homet_clock_item;
        ImageView homet_level_star1_item, homet_level_star2_item, homet_level_star3_item, homet_level_star4_item, homet_level_star5_item;
        TextView homet_title_item;
        TextView homet_level_text_item;
        TextView homet_timenum_item;
        TextView homet_timetext_item;
        TextView homet_timenum_item2;
        TextView homet_timetext_item2;
        ImageView homet_bookmark_image;
        LinearLayout exercise_item;


        public HomeTrainingViewHolder(@NonNull View itemView) {
            super(itemView);

            thumbnail_image_item = itemView.findViewById(R.id.thumbnail_image_item);
            homet_clock_item = itemView.findViewById(R.id.homet_clock_item);
            homet_level_star1_item = itemView.findViewById(R.id.homet_level_star1_item);
            homet_level_star2_item = itemView.findViewById(R.id.homet_level_star2_item);
            homet_level_star3_item = itemView.findViewById(R.id.homet_level_star3_item);
            homet_level_star4_item = itemView.findViewById(R.id.homet_level_star4_item);
            homet_level_star5_item = itemView.findViewById(R.id.homet_level_star5_item);
            homet_title_item = itemView.findViewById(R.id.homet_title_item);
            homet_level_text_item = itemView.findViewById(R.id.homet_level_text_item);
            homet_timenum_item = itemView.findViewById(R.id.homet_timenum_item);
            homet_timetext_item = itemView.findViewById(R.id.homet_timetext_item);
            homet_timenum_item2 = itemView.findViewById(R.id.homet_timenum_item2);
            homet_timetext_item2 = itemView.findViewById(R.id.homet_timetext_item2);
            exercise_item = itemView.findViewById(R.id.exercise_item);
            homet_bookmark_image = itemView.findViewById(R.id.homet_bookmark_image); //북마크 버튼 연결


        }
    }

    @NonNull
    @Override
    public HomeTrainingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_training_item, parent, false); //view 객체 안에 home_training_item 뷰 정보를 넣는다
        HomeTrainingViewHolder holder = new HomeTrainingViewHolder(view);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull final HomeTrainingViewHolder holder, final int position) {
        holder.thumbnail_image_item.setImageResource(HomeTrainingArrayList.get(position).getThumbnail_image()); //thumbnail_image_item에 list의 해당 포지션에 해당 image 내용을 담는다
        holder.homet_clock_item.setImageResource(HomeTrainingArrayList.get(position).getHomet_clock_item());
        holder.homet_level_star1_item.setImageResource(HomeTrainingArrayList.get(position).getHomet_level_star1());
        holder.homet_level_star2_item.setImageResource(HomeTrainingArrayList.get(position).getHomet_level_star2());
        holder.homet_level_star3_item.setImageResource(HomeTrainingArrayList.get(position).getHomet_level_star3());
        holder.homet_level_star4_item.setImageResource(HomeTrainingArrayList.get(position).getHomet_level_star4());
        holder.homet_level_star5_item.setImageResource(HomeTrainingArrayList.get(position).getHomet_level_star5());
        holder.homet_title_item.setText(HomeTrainingArrayList.get(position).getHomet_title()); //homet_title_item에 list의 해당 포지션에 해당 getHomet_title 내용을 담는다
        holder.homet_level_text_item.setText(HomeTrainingArrayList.get(position).getHomet_level_text());
        holder.homet_timenum_item.setText(HomeTrainingArrayList.get(position).getHomet_timenum());
        holder.homet_timetext_item.setText(HomeTrainingArrayList.get(position).getHomet_timetext());
        holder.homet_timenum_item2.setText(HomeTrainingArrayList.get(position).getHomet_timenum2());
        holder.homet_timetext_item2.setText(HomeTrainingArrayList.get(position).getHomet_timetext2());
        holder.homet_bookmark_image.setImageResource(HomeTrainingArrayList.get(position).getHomet_bookmark_image());

        holder.exercise_item.setOnClickListener(new View.OnClickListener() { //exercise_item 레이아웃을 클릭 했을 때 기능 구현
            @Override
            public void onClick(View view) { //item의 레이아웃을 클릭 할 때 화면 전환 (데이터 전달하면서 화면 전환)
                Intent intent = new Intent(view.getContext(), HomeTrainingDetailActivity.class); //view.getContext()는 view(현재) 클래스 context를 가져오는것
                intent.putExtra("homet_title_item", HomeTrainingArrayList.get(position).getHomet_title());
                intent.putExtra("homet_timenum_item", HomeTrainingArrayList.get(position).getHomet_timenum());
                intent.putExtra("homet_timetext_item", HomeTrainingArrayList.get(position).getHomet_timetext());
                intent.putExtra("homet_timenum_item2", HomeTrainingArrayList.get(position).getHomet_timenum2());
                intent.putExtra("homet_timetext_item2", HomeTrainingArrayList.get(position).getHomet_timetext2());


                Bitmap sendBitmap = BitmapFactory.decodeResource(context.getResources(), HomeTrainingArrayList.get(position).getThumbnail_image());
                //Activity가 아닌 다른 클래스에서 getResources()를 사용하려면 자기 자신(this)을 인자값으로 넘겨 주시면 된다
                //Activity에서는 생략해도 Activity.this 가 있는것과 같은 기능을 한다
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                sendBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();
                intent.putExtra("image", byteArray);

                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), "운동 준비", Toast.LENGTH_SHORT).show();
            }
        });


        holder.homet_bookmark_image.setOnClickListener(new View.OnClickListener() { //북마크 버튼을 클릭했을 때 기능 구현
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(), "북마크가 취소 되었습니다", Toast.LENGTH_SHORT).show();

                remove(holder.getAdapterPosition()); //해당 포지션 아이템 제거
                notifyItemRangeChanged(holder.getAdapterPosition(), HomeTrainingArrayList.size()); //리사이클러뷰 범위 새로코침


            }
        });


//        holder.thumbnail_image_item.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(view.getContext(), position+"클릭 되었습니다.", Toast.LENGTH_SHORT).show();
//            }
//        });
//        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {//HomeTrainingArrayList null값이 아니면 size만큼 반환, null값이면 0을 반환
        return (HomeTrainingArrayList != null ? HomeTrainingArrayList.size() : 0);
    }

    public void remove(int position) {
        try {
            HomeTrainingArrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

}
