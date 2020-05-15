package model;

public class ResumenDetalles {
	
	private int pvmId;
	private Producto producto;
	private TipoProducto tipoProducto;
	private Marca marca;
	private ProductoValorMedida productoValorMedida;
	private UnidadMedida unidadMedida;
	
	
	
	public int getPvmId() {
		return pvmId;
	}
	public void setPvmId(int pvmId) {
		this.pvmId = pvmId;
	}
	public Producto getProducto() {
		return producto;
	}
	public void setProducto(Producto producto) {
		this.producto = producto;
	}
	public TipoProducto getTipoProducto() {
		return tipoProducto;
	}
	public void setTipoProducto(TipoProducto tipoProducto) {
		this.tipoProducto = tipoProducto;
	}
	public Marca getMarca() {
		return marca;
	}
	public void setMarca(Marca marca) {
		this.marca = marca;
	}
	public ProductoValorMedida getProductoValorMedida() {
		return productoValorMedida;
	}
	public void setProductoValorMedida(ProductoValorMedida productoValorMedida) {
		this.productoValorMedida = productoValorMedida;
	}
	public UnidadMedida getUnidadMedida() {
		return unidadMedida;
	}
	public void setUnidadMedida(UnidadMedida unidadMedida) {
		this.unidadMedida = unidadMedida;
	}

}
