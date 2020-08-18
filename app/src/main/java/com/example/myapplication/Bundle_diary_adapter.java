package com.example.myapplication;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;


public class Bundle_diary_adapter extends RecyclerView.Adapter<Bundle_diary_adapter.DiaryViewHolder> {

    private ArrayList<Bundle_diary_data> diaryArrayList;
    private Context context; //BundleDiaryActivity에서 this로 받아옴
    Calendar calendar;
    private int year,month,day,week;
    private int position;
    public static int check_diary = 0;

    public Bundle_diary_adapter(ArrayList<Bundle_diary_data> diaryArrayList, Context context) { // context -> BundleDiaryActivity 뷰 받기 위해 선언
        this.diaryArrayList = diaryArrayList; //데이터들을 담기 위해 리스트 선언
        this.context = context;
    }


//    public class DiaryViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener { //View.OnCreateContextMenuListener -> 컨텍스트메뉴 구현준비
        public class DiaryViewHolder extends RecyclerView.ViewHolder { //View.OnCreateContextMenuListener -> 컨텍스트메뉴 구현준비
        protected TextView diary_title;
        protected TextView diary_content;
        protected TextView diary_date;
        protected TextView diary_day;
        protected TextView diary_week;
        LinearLayout todaydairy_item;


        public DiaryViewHolder(@NonNull View itemView) { //BundleDiary에 들어갈 요소들 연결
            super(itemView);
//            diary_week = itemView.findViewById(R.id.diary_week);
            diary_title = itemView.findViewById(R.id.diary_title);
            diary_day = itemView.findViewById(R.id.diary_day);
            diary_content = itemView.findViewById(R.id.diary_content);
            diary_date = itemView.findViewById(R.id.diary_date);
            todaydairy_item = itemView.findViewById(R.id.todaydairy_item); //아이템 전체 레이아웃 연결 시키기

//            itemView.setOnCreateContextMenuListener(this); //길게 터치 하면 contextmenu 를 불러온다
        }

//        @Override
//        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) { //해당 뷰를 길게 누르면 메뉴바 생성
//            MenuItem edit = contextMenu.add(Menu.NONE, 1000, contextMenu.NONE, "수정"); //첫번째 인자 - 그룹 지정(단일 일 때는 NONE), 두번째 인자- ID
//            MenuItem delete = contextMenu.add(Menu.NONE, 2000, contextMenu.NONE, "삭제"); //세번째 인자 -순서, 네번째 인자 - 텍스트
//            edit.setOnMenuItemClickListener(selectmenu);
//            delete.setOnMenuItemClickListener(selectmenu);
//        }

//        private final MenuItem.OnMenuItemClickListener selectmenu = new MenuItem.OnMenuItemClickListener() { //메뉴바에서 무엇을 선택 했는지에 따른 기능 구현
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//                switch (menuItem.getItemId()) { //메뉴에서 무엇을 선택 했는지 아이디 받아오기
//                    case 1000: //코드 1000을 선택 (여기서는 수정 버튼)
//
//                        AlertDialog.Builder builder = new AlertDialog.Builder(context); // 다이어리 내용 간단하게 수정하기 위해 다이얼로그 생성
//                        View view = LayoutInflater.from(context).inflate(R.layout.bundle_diary_edit_content, null, false);
//                        builder.setView(view);
//
//                        Button edit_button = view.findViewById(R.id.diary_edit_button);
//                        final EditText edit_title = view.findViewById(R.id.diary_edit_title);
//                        final Button edit_date_button = view.findViewById(R.id.diary_edit_date_button);
//                        final TextView edit_year_text = view.findViewById(R.id.diary_edit_year_text);
//                        final TextView edit_month_text = view.findViewById(R.id.diary_edit_month_text);
//                        final TextView edit_day_text = view.findViewById(R.id.diary_edit_day_text);
//                        final AlertDialog dialog = builder.create(); //다이얼로그 화면에 생성
//
//                        edit_title.setText(diaryArrayList.get(position).getDiary_title());
//                        edit_year_text.setText(diaryArrayList.get(position).getDiary_year()); //다이얼로그 킬 때 날짜가 일기장에 날짜가 보이도록
//                        edit_month_text.setText(diaryArrayList.get(position).getDiary_month());
//                        edit_day_text.setText(diaryArrayList.get(position).getDiary_day());
//
//                        diary_content.setText(diaryArrayList.get(position).getDiary_content()); //콘텐츠 내용은 그대로 받아서 사용
//
////                        diary_title.setText(diaryArrayList.get(position).getDiary_title());
//
//                        edit_date_button.setOnClickListener(new View.OnClickListener() { // 날짜 변경 버튼을 눌렀을 때
//                            @Override
//                            public void onClick(View view) { //날짜 변경 버튼을 누른다
//                                Toast.makeText(context,"날짜 변경",Toast.LENGTH_LONG).show();
//
//                                calendar = Calendar.getInstance();
//                                year = calendar.get(Calendar.YEAR);
//                                month = calendar.get(Calendar.MONTH);
//                                day = calendar.get(Calendar.DAY_OF_MONTH);
//
//                                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() { //데이트피커 생성
//                                    @Override
//                                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//                                        edit_year_text.setText(String.valueOf(year)); //날짜 정보를 받아서 입력해준다
//                                        edit_month_text.setText(String.valueOf(month+1));//month는 0부터 시작하기 때문에 원하는 달을 구하기 위해서 +1 해줘야 함
//                                        edit_day_text.setText(String.valueOf(day)); //날짜 정보를 받아서 입력해준다
//                                    }
//                                },
//                                        year,month,day
//                                );
//                                datePickerDialog.show(); //datePickerDialog 보여주기
//                            }
//                        });
//
//
//
//                        edit_button.setOnClickListener(new View.OnClickListener() { // 수정 완료 버튼을 누름름
//                            @Override
//                            public void onClick(View view) {
//
//                                String strtitle = edit_title.getText().toString();
//                                String stryear = edit_year_text.getText().toString();
//                                String strmonth = edit_month_text.getText().toString();
//                                String strday = edit_day_text.getText().toString();
//
//                                diary_date.setText(stryear+"년 "+strmonth+"월 "+strday+"일 ");
//                                String strdate = diary_date.getText().toString();
//
////                                String strweek = diary_date.setText(year+"년 "+month+"월 "+day+"일 "+weekday+"요일");
//
//                                String strcontent = diary_content.getText().toString();
//
//
//                                Bundle_diary_data bundle_diary_data = new Bundle_diary_data(strtitle, strcontent, strdate,strday,strmonth,stryear);
//                                diaryArrayList.set(getAdapterPosition(),bundle_diary_data); //위에 받은 데이터로 수정
//                                notifyDataSetChanged();
//                                dialog.dismiss(); // AlertDialog 사라지기
//                            }
//                        });
//
//
//                        dialog.show();
//                        break;
//                    case 2000: //코드 1000을 선택 (여기서는 삭제 버튼)
//
//                        remove(getAdapterPosition()); //해당 포지션 아이템 제거
//                        notifyItemRangeChanged(getAdapterPosition(), diaryArrayList.size()); //리사이클러뷰 범위 새로코침
//                }
//                return false;
//            }
//        };

    }


    @NonNull
    @Override
    public DiaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bundle_diary_item, parent, false);
        DiaryViewHolder holder = new DiaryViewHolder(view);
        return holder;
    }




    @Override
    public void onBindViewHolder(@NonNull DiaryViewHolder holder, final int position) {
        this.position = position; //position 값을 다른 메소드에서도 쓸 수 있도록 전역변수에 연결해줌

//        Log.e("채크 다이어리",String.valueOf(Bundle_diary_adapter.check_diary));
//        Log.e("position",String.valueOf(position));

//        if (diaryArrayList.get(position).getDiary_title().length()>16) {
//            holder.diary_title.setText(diaryArrayList.get(position).getDiary_title().substring(0,15)+"...");
//        } else {
//            holder.diary_title.setText(diaryArrayList.get(position).getDiary_title());
//        } //값이 전달되기 이전이여서 아예 없기 때문에 비교를 할 수 없음.....



        holder.diary_title.setText(diaryArrayList.get(position).getDiary_title()); //해당 리사이클러뷰 위치에 해당 데이터를 리스트에 받아서 넣는데 data클래스를 참조함
        holder.diary_content.setText(diaryArrayList.get(position).getDiary_content());
        holder.diary_date.setText(diaryArrayList.get(position).getDiary_date());
        holder.diary_day.setText(diaryArrayList.get(position).getDiary_day());
//        holder.diary_week.setText(diaryArrayList.get(position).getDiary_week());


        holder.todaydairy_item.setOnClickListener(new View.OnClickListener() { //아이템 레이아웃을 클릭 했을 때
            @Override
            public void onClick(View view) { //나의 일기 아이템을 클릭 했을 때
                Intent intent = new Intent(view.getContext(), TodayDiaryCompleteActivity.class); //화면 전환하기 (view의 현재 context에서 넘거가기)
                check_diary = 1; //BundleDiaryActivity 에서 TodayDiaryCompleteActivity 넘어 갈 때 데이터 전달이 없어서 생기는 오류 없애기 위한 변수
                intent.putExtra("position", position); //포지션값 넘기기

                view.getContext().startActivity(intent); //해당 뷰에서 넘어가기 (view.getContext() 코드가 매우 중요)
            }
        });
        holder.itemView.setTag(position);
    }



    @Override
    public int getItemCount() {
        return (diaryArrayList != null ? diaryArrayList.size() : 0); // diaryarrayList가 null이 아니면 리스트 사이즈 만큼 반환, 아니면 0을 반환
    }

    public void remove(int position) {
        try {
            diaryArrayList.remove(position);
            notifyItemRemoved(position);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

}
