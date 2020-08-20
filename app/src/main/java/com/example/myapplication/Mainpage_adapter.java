package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Mainpage_adapter extends RecyclerView.Adapter<Mainpage_adapter.MainViewHolder> {

    private ArrayList<Mainpage_data> mainpage_dataArrayList; //액티비티에서 리스트로 받기 위해 adapter에서도 선언
    private Context context; // 액태비티의 context를 받기 위해 선언

    public Mainpage_adapter(ArrayList<Mainpage_data> mainpage_dataArrayList, Context context) {
        this.mainpage_dataArrayList = mainpage_dataArrayList;
        this.context = context;
    }


    public class MainViewHolder extends RecyclerView.ViewHolder {
        private TextView main_diary_nickname;
        private TextView main_diary_title;
        private TextView main_heart_count;
        private ImageView main_diary_image;
        private ImageView main_mydiary_check;
        androidx.constraintlayout.widget.ConstraintLayout otherpeople_dairy_sumnail;

        public MainViewHolder(@NonNull View itemView) {
            super(itemView);
            main_diary_nickname = itemView.findViewById(R.id.main_diary_nickname);
            main_diary_title = itemView.findViewById(R.id.main_diary_title);
            main_heart_count = itemView.findViewById(R.id.main_heart_count);
            main_diary_image = itemView.findViewById(R.id.main_diary_image);
            main_mydiary_check = itemView.findViewById(R.id.main_mydiary_check);

            otherpeople_dairy_sumnail = itemView.findViewById(R.id.otherpeople_dairy_sumnail_item);

        }
    }


    @NonNull
    @Override
    public MainViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.mainpage_item, parent, false); //mainpage_item 레이어와 연결
        Mainpage_adapter.MainViewHolder holder = new Mainpage_adapter.MainViewHolder(view);

        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull MainViewHolder holder, final int position) {
        Log.e("확인","확인");
        holder.main_diary_image.setImageBitmap(mainpage_dataArrayList.get(position).getDiary_image()); //my_profile의 해당 포지션에 해당 content 이미지를 bitmap으로 담는다
        Log.e("확인","확인");
        holder.main_diary_nickname.setText(mainpage_dataArrayList.get(position).getNickname()); //my_massage의 해당 포지션에 해당 content 내용을 담는다
        Log.e("확인","확인");
        holder.main_diary_title.setText(mainpage_dataArrayList.get(position).getDiary_title()); //my_massage의 해당 포지션에 해당 content 내용을 담는다
        holder.main_heart_count.setText(mainpage_dataArrayList.get(position).getHeartcount()); //my_massage의 해당 포지션에 해당 content 내용을 담는다

        if (mainpage_dataArrayList.get(position).getDiary_id().equals(logInActivity.my_id)){
            holder.main_mydiary_check.setVisibility(View.VISIBLE);
        }






        holder.otherpeople_dairy_sumnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //공유일기 선택 했을 때 화면 전환

                Intent intent = new Intent(view.getContext(), OtherpeopleDiaryActivity.class); //화면 전환하기 (view의 현재 context에서 넘거가기)

//                Log.e("타이틀", mainpage_dataArrayList.get(position).getDiary_title());
//                Log.e("내용", mainpage_dataArrayList.get(position).getDiary_content());

                intent.putExtra("position", position); //포지션값 넘기기
                intent.putExtra("title", mainpage_dataArrayList.get(position).getDiary_title()); //제목값 넘기기
                intent.putExtra("content", mainpage_dataArrayList.get(position).getDiary_content()); //내용값 넘기기
                intent.putExtra("date", mainpage_dataArrayList.get(position).getDiary_date()); //내용값 넘기기

//                intent.putExtra("date", mainpage_dataArrayList.get(position).get()); //날짜값 넘기기

                view.getContext().startActivity(intent); //해당 뷰에서 넘어가기 (view.getContext() 코드가 매우 중요)

            }
        });
    }


    @Override
    public int getItemCount() {
        return (mainpage_dataArrayList != null ? mainpage_dataArrayList.size() : 0);

    }


}
