package com.example.anyholo.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anyholo.Model.KirinukiView;
import com.example.anyholo.Model.MemberView;
import com.example.anyholo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class KirinukiAdapter extends BaseAdapter {
    ArrayList<KirinukiView> items = new ArrayList< KirinukiView>();
    Context context;
    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return items.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    public void setItems(ArrayList< KirinukiView> list){
        items=list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        KirinukiView kirinukiView = items.get(i);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.kirinuki_item, viewGroup, false);
        }
        TextView chanelName = view.findViewById(R.id.ChanelName);
        TextView videoName = view.findViewById(R.id.videoName);
        TextView uptime = view.findViewById(R.id.uptime);
        ImageView kirinukiImage = view.findViewById(R.id.kirinukiImage);
        Glide.with(view).load(kirinukiView.getThumnailUrl()).into(kirinukiImage);//url를 이용하여 이미지 뷰에 이미지 세팅
        chanelName.setText(kirinukiView.getChannelName());
        videoName.setText(kirinukiView.getVideoTitle());
        Log.d("결과 : ",getTime(kirinukiView.getUploadTime()));
        uptime.setText(getTime(kirinukiView.getUploadTime()));
        Log.d("키리누키 어뎁터","시작");
        return view;
    }
    public String getTime(String time){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Calendar uptime = Calendar.getInstance();
            uptime.setTime(sdf.parse(time));
            Calendar onedayafter = Calendar.getInstance();
            onedayafter.setTime(sdf.parse(time));
            onedayafter.add(Calendar.DAY_OF_MONTH,+1);
            Log.d("업로드 시간 : ",sdf.format(uptime.getTime()));
            Log.d("하루 전 : ",sdf.format(onedayafter.getTime()));
            Calendar now = Calendar.getInstance();
            //한국이라 9시간 더해줘야함
            now.add(Calendar.HOUR,9);
            Log.d("현재 시간 : ",sdf.format(now.getTime()));
            Log.d("업로드 시간 < 현재 시간 : ",String.valueOf(now.compareTo(uptime)));
            Log.d("하루 전 < 현재 시간 : ",String.valueOf(now.compareTo(onedayafter)));
            if(now.before(onedayafter)) { // 현재시간은 언제나 업로드 타임보다 앞이라 하루 뒤 시간을 넘었는지만 체크하면됨
                long diffSec = (now.getTimeInMillis()-uptime.getTimeInMillis())/1000;
                long diffHour = diffSec/(60*60);
                //Log.d("시간 차 : ",String.valueOf(diffHour));
                if(diffHour!=0){
                    return diffHour+"시간 전";
                }
                long diffMinute = diffSec/(60);
                //Log.d("분 차 : ",String.valueOf(diffMinute));
                return diffMinute+"분 전";
            }
            sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d = sdf.parse(time);
            return sdf.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
