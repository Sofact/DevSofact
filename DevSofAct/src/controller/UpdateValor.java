package controller;

public class UpdateValor {
	
	 private DetallePVMU[] detallePVMU;

	    public DetallePVMU[] getDetallePVMU ()
	    {
	        return detallePVMU;
	    }

	    public void setDetallePVMU (DetallePVMU[] detallePVMU)
	    {
	        this.detallePVMU = detallePVMU;
	    }

	    @Override
	    public String toString()
	    {
	        return "ClassPojo [detallePVMU = "+detallePVMU+"]";
	    }
}
