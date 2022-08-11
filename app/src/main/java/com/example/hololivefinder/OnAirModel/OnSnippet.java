package com.example.hololivefinder.OnAirModel;

import com.example.hololivefinder.ProfileModel.Localized;

public class OnSnippet
{
    private String publishedAt;

    private String defaultAudioLanguage;

    private Localized localized;

    private String description;

    private String title;

    private Thumbnails thumbnails;

    private String channelId;

    private String categoryId;

    private String channelTitle;

    private String[] tags;

    private String liveBroadcastContent;

    public String getPublishedAt ()
    {
        return publishedAt;
    }

    public void setPublishedAt (String publishedAt)
    {
        this.publishedAt = publishedAt;
    }

    public String getDefaultAudioLanguage ()
    {
        return defaultAudioLanguage;
    }

    public void setDefaultAudioLanguage (String defaultAudioLanguage)
    {
        this.defaultAudioLanguage = defaultAudioLanguage;
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

    public String getChannelId ()
    {
        return channelId;
    }

    public void setChannelId (String channelId)
    {
        this.channelId = channelId;
    }

    public String getCategoryId ()
    {
        return categoryId;
    }

    public void setCategoryId (String categoryId)
    {
        this.categoryId = categoryId;
    }

    public String getChannelTitle ()
    {
        return channelTitle;
    }

    public void setChannelTitle (String channelTitle)
    {
        this.channelTitle = channelTitle;
    }

    public String[] getTags ()
    {
        return tags;
    }

    public void setTags (String[] tags)
    {
        this.tags = tags;
    }

    public String getLiveBroadcastContent ()
    {
        return liveBroadcastContent;
    }

    public void setLiveBroadcastContent (String liveBroadcastContent)
    {
        this.liveBroadcastContent = liveBroadcastContent;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [publishedAt = "+publishedAt+", defaultAudioLanguage = "+defaultAudioLanguage+", localized = "+localized+", description = "+description+", title = "+title+", thumbnails = "+thumbnails+", channelId = "+channelId+", categoryId = "+categoryId+", channelTitle = "+channelTitle+", tags = "+tags+", liveBroadcastContent = "+liveBroadcastContent+"]";
    }
}