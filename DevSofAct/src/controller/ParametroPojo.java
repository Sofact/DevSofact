package controller;


public class ParametroPojo
{
	 private Parametro[] parametro;

	    public Parametro[] getParametro ()
	    {
	        return parametro;
	    }

	    public void setParametro (Parametro[] TipoProductoParam)
	    {
	        this.parametro = TipoProductoParam;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [parametro = "+parametro+"]";
	    }
}

