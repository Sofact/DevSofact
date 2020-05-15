package controller;

public class DetalleMarca
{
    private String marId;

    private String marDesc;

    public String getMarId ()
    {
        return marId;
    }

    public void setMarId (String marId)
    {
        this.marId = marId;
    }

    public String getMarDesc ()
    {
        return marDesc;
    }

    public void setMarDesc (String marDesc)
    {
        this.marDesc = marDesc;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [marId = "+marId+", marDesc = "+marDesc+"]";
    }
}
