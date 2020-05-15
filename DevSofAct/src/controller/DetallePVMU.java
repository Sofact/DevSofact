package controller;

public class DetallePVMU
{
private String proId;

private String pvmId;

private String valor;

private String producto;

private String referencia;

private String cantidad;

public String getCantidad() {
	return cantidad;
}

public void setCantidad(String cantidad) {
	this.cantidad = cantidad;
}

public String getProId ()
{
return proId;
}

public void setProId (String proId)
{
this.proId = proId;
}

public String getPvmId ()
{
return pvmId;
}

public void setPvmId (String pvmId)
{
this.pvmId = pvmId;
}

public String getValor ()
{
return valor;
}

public void setValor (String valor)
{
this.valor = valor;
}

public String getProducto ()
{
return producto;
}

public void setProducto (String producto)
{
this.producto = producto;
}

public String getReferencia ()
{
return referencia;
}

public void setReferencia (String referencia)
{
this.referencia = referencia;
}

@Override
public String toString()
{
return "ClassPojo [proId = "+proId+", pvmId = "+pvmId+", valor = "+valor+", producto = "+producto+", referencia = "+referencia+", cantidad = "+cantidad+"]";
}
}