package com.anyholo.OnAirModel;

public class OnItems
{
    private OnSnippet snippet;

    private String kind;

    private String etag;

    private String id;

    public OnSnippet getSnippet ()
    {
        return snippet;
    }

    public void setSnippet (OnSnippet snippet)
    {
        this.snippet = snippet;
    }

    public String getKind ()
    {
        return kind;
    }

    public void setKind (String kind)
    {
        this.kind = kind;
    }

    public String getEtag ()
    {
        return etag;
    }

    public void setEtag (String etag)
    {
        this.etag = etag;
    }

    public String getId ()
    {
        return id;
    }

    public void setId (String id)
    {
        this.id = id;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [snippet = "+snippet+", kind = "+kind+", etag = "+etag+", id = "+id+"]";
    }
}