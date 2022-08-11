package com.example.hololivefinder.ProfileModel;

public class Thumbnails
{
    private Default defaults;

    private High high;

    private Medium medium;

    public Default getDefault ()
    {
        return defaults;
    }

    public void setDefault (Default defaults)
    {
        this.defaults = defaults;
    }

    public High getHigh ()
    {
        return high;
    }

    public void setHigh (High high)
    {
        this.high = high;
    }

    public Medium getMedium ()
    {
        return medium;
    }

    public void setMedium (Medium medium)
    {
        this.medium = medium;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [default = "+defaults+", high = "+high+", medium = "+medium+"]";
    }
}