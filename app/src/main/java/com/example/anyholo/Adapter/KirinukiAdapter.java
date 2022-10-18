package com.example.anyholo.Adapter;

import android.content.Context;
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

import java.util.ArrayList;

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
        ImageView kirinukiImage = view.findViewById(R.id.kirinukiImage);
        Glide.with(view).load(kirinukiView.getThumnailUrl()).into(kirinukiImage);//url를 이용하여 이미지 뷰에 이미지 세팅
        chanelName.setText(kirinukiView.getChannelName());
        videoName.setText(kirinukiView.getVideoTitle());
        return view;
    }
}
