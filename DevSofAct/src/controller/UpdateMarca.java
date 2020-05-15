package controller;

public class UpdateMarca
{
    private DetalleMarca[] detalleMarca;

    public DetalleMarca[] getDetalleMarca ()
    {
        return detalleMarca;
    }

    public void setDetalleMarca (DetalleMarca[] detalleMarca)
    {
        this.detalleMarca = detalleMarca;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [detalleMarca = "+detalleMarca+"]";
    }
}