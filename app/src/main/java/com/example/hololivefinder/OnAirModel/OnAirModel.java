package com.example.hololivefinder.OnAirModel;

public class OnAirModel // api에서 받은 responce를 받을 모델들
{
    private OnItems[] items;

    public OnItems[] getItems ()
    {
        return items;
    }

    public void setItems (OnItems[] items)
    {
        this.items = items;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [items = "+items+"]";
    }
}