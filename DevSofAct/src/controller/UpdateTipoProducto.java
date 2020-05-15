package controller;

public class UpdateTipoProducto
{
    private DetalleTP[] detalleTP;

    public DetalleTP[] getDetalleTP ()
    {
        return detalleTP;
    }

    public void setDetalleTP (DetalleTP[] detalleTP)
    {
        this.detalleTP = detalleTP;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [detalleTP = "+detalleTP+"]";
    }
}
