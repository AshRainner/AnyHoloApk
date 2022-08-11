package com.example.hololivefinder;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.hololivefinder.MemberModel.MemberView;

import java.util.ArrayList;
import java.util.List;

public class GridAdapter extends BaseAdapter {
    ArrayList<MemberView> items = new ArrayList< MemberView>();
    Context context;
    public void addItem( MemberView imageId){
        items.add(imageId);
    }
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

    public void setItems(ArrayList< MemberView> list){
        items=list;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
         MemberView memberView = items.get(i);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.member_grid, viewGroup, false);
        }
        TextView textView = view.findViewById(R.id.memberName);
        ImageView imageView = view.findViewById(R.id.memberImage);
        if(memberView.getOnAir().equals("live"))//혹시 live 중이라면 뷰의 배경색을 변경시킴
            view.setBackgroundColor(Color.parseColor("#ff9aa4"));//#FFCDD2 #F06292 #EC407A ff9aa4
        else//아니면 푸른색
            view.setBackgroundColor(Color.parseColor("#5fddef"));//#B3E5FC #88d2ff 5fddef
        Glide.with(view).load(memberView.getImageUrl()).apply(new RequestOptions().circleCrop()).into(imageView);//url를 이용하여 이미지 뷰에 이미지 세팅
        textView.setText(memberView.getMemberName());
        return view;
    }
}
