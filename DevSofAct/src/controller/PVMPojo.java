package controller;

public class PVMPojo
{
    private DetallePVM[] detallePVM;

    public DetallePVM[] getDetallePVM ()
    {
        return detallePVM;
    }

    public void setDetallePVM (DetallePVM[] detallePVM)
    {
        this.detallePVM = detallePVM;
    }

    @Override
    public String toString()
    {
        return "ClassPojo [detallePVM = "+detallePVM+"]";
    }
}
