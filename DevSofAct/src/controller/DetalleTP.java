package controller;


public class DetalleTP
{
private String tipId;

private String tipDesc;

public String getTipId ()
{
return tipId;
}

public void setTipId (String tipId)
{
this.tipId = tipId;
}

public String getTipDesc ()
{
return tipDesc;
}

public void setTipDesc (String tipDesc)
{
this.tipDesc = tipDesc;
}

@Override
public String toString()
{
return "ClassPojo [tipId = "+tipId+", tipDesc = "+tipDesc+"]";
}
}
	

