package controller;


public class Parametro
{
	private String paramDescripcion;
	
	public String getParamDescripcion ()
	{
		return paramDescripcion;
	}
	
	public void setTipDescripcion (String paramDescripcion)
	{
		this.paramDescripcion = paramDescripcion;
	}
	
	@Override
	public String toString()
	{
		return "ClassPojo [paramDescripcion = "+paramDescripcion+"]";
	}
}