package com.example.hololivefinder.ProfileModel;

public class Snippet
{
    private String country;

    private String publishedAt;

    private Localized localized;

    private String description;

    private String title;

    private Thumbnails thumbnails;

    public String getCountry ()
    {
        return country;
    }

    public void setCountry (String country)
    {
        this.country = country;
    }

    public String getPublishedAt ()
    {
        return publishedAt;
    }

    public void setPublishedAt (String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public Localized getLocalized ()
    {
        return localized;
    }

    public void setLocalized (Localized localized)
    {
        this.localized = localized;
    }

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

    public Thumbnails getThumbnails ()
    {
        return thumbnails;
    }

    public void setThumbnails (Thumbnails thumbnails)
    {
        this.thumbnails = thumbnails;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [country = "+country+", publishedAt = "+publishedAt+", localized = "+localized+", description = "+description+", title = "+title+", thumbnails = "+thumbnails+"]";
    }
}
