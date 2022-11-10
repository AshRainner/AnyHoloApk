package com.example.anyholo.Adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.anyholo.Model.MemberView;
import com.example.anyholo.R;
import com.example.anyholo.inferface.FavoriteHandle;

import java.util.ArrayList;
import java.util.HashMap;

public class LiveAdapter extends BaseAdapter {
    ArrayList<MemberView> items = new ArrayList< MemberView>();
    Context context;
    private FavoriteHandle favorite;
    private HashMap<String,Boolean> favoriteMap;
    public LiveAdapter(FavoriteHandle favorite){
        this.favorite=favorite;
    }
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

    public void setItems(ArrayList< MemberView> list,HashMap<String,Boolean> favoriteMap){
        items=list;
        this.favoriteMap=favoriteMap;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        MemberView memberView = items.get(i);
        if(view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.member_item, viewGroup, false);
        }
        TextView textView = view.findViewById(R.id.memberName);
        ImageView imageView = view.findViewById(R.id.memberImage);
        if(memberView.getOnAir().equals("live"))//혹시 live 중이라면 뷰의 배경색을 변경시킴
            view.setBackgroundResource(R.drawable.grid_live);
        else if(memberView.getOnAir().equals("upcoming")) {
            view.setBackgroundResource(R.drawable.grid_upcoming);
        }
        else
            view.setBackgroundResource(R.drawable.grid_nolive);
        Glide.with(view).load(memberView.getImageUrl()).apply(new RequestOptions()).circleCrop().into(imageView);//url를 이용하여 이미지 뷰에 이미지 세팅
        textView.setText(memberView.getMemberName());
        CheckBox favoriteBtn = view.findViewById(R.id.favoriteBtn);
        if(favoriteMap.get(memberView.getMemberName())) {
            favoriteBtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#f2ff3c")));
            favoriteBtn.setChecked(true);
        }else{
            favoriteBtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#757575")));
            favoriteBtn.setChecked(false);
        }
        favoriteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(favoriteBtn.isChecked()) {
                    favoriteBtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#f2ff3c")));
                    favorite.onFavoriteUpdate(memberView.getMemberName(),true);
                }
                else {
                    favoriteBtn.setButtonTintList(ColorStateList.valueOf(Color.parseColor("#757575")));
                    favorite.onFavoriteUpdate(memberView.getMemberName(),false);
                }
            }
        });
        favoriteBtn.bringToFront();
        return view;
    }
}
