package controller;


public class DetallePVM
{
private String idMarca;

private String valor;

private String descripcion;

private String idTipo;

private String idMedida;

private String referencia;

private String cantidad;

public String getCantidad() {
	return cantidad;
}

public void setCantidad(String cantidad) {
	this.cantidad = cantidad;
}

public String getIdMarca ()
{
return idMarca;
}

public void setIdMarca (String idMarca)
{
this.idMarca = idMarca;
}

public String getValor ()
{
return valor;
}

public void setValor (String valor)
{
this.valor = valor;
}

public String getDescripcion ()
{
return descripcion;
}

public void setDescripcion (String descripcion)
{
this.descripcion = descripcion;
}

public String getIdTipo ()
{
return idTipo;
}

public void setIdTipo (String idTipo)
{
this.idTipo = idTipo;
}

public String getIdMedida ()
{
return idMedida;
}

public void setIdMedida (String idMedida)
{
this.idMedida = idMedida;
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
return "ClassPojo [idMarca = "+idMarca+", valor = "+valor+", descripcion = "+descripcion+", idTipo = "+idTipo+", idMedida = "+idMedida+", referencia = "+referencia+", cantidad = "+cantidad+"]";
}
}

