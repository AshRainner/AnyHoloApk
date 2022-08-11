package com.example.hololivefinder.OnAirModel;

public class Localized
{
    private String description;

    private String title;

    public String getDescription ()
    {
        return description;
    }

    public void setDescription (String description)
    {
        this.description = description;
    }

    public String getTitle ()
    {
        return title;
    }

    public void setTitle (String title)
    {
        this.title = title;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [description = "+description+", title = "+title+"]";
    }
}