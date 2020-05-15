
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	 	<link rel="stylesheet" type="text/css" href="style.css">
		<script src="http://code.jquery.com/jquery-1.11.1.min.js"></script>
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.4/jquery.min.js"></script>
		<link
			href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/css/bootstrap.min.css"
			rel="stylesheet" id="bootstrap-css">
		
		<link rel="stylesheet"
			href="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/3.3.5/css/bootstrap.min.css" />
		<link rel="stylesheet"
			href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/css/bootstrap-datepicker.min.css" />
		<script
			src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-datepicker/1.5.0/js/bootstrap-datepicker.min.js"></script>
		<link rel="stylesheet" type="text/css" href="last.css">
		<link rel="stylesheet" 	href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
		<!-- link rel="stylesheet" href="/resources/demos/style.css" -->
		<script src="https://code.jquery.com/jquery-1.12.4.js"></script>
		<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
		<script
			src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.0/js/bootstrap.min.js"></script>


<!-- script fechas -->		
<script>

 $( document ).ready(function() {
            $('#fechaIni').datepicker({ dateFormat: 'dd/mm/yy' }).val();
            $('#fechaFin').datepicker({ dateFormat: 'dd/mm/yy' }).val();
        });
 		
</script>	


<!-- script creacion tabla resutlado de busqueda -->	
<script>
$(document).ready(function(){
	$('a[id=buscar]').on('click', function(){
	var nombre= document.getElementById("primerNombre").value;
	var identificacion= document.getElementById("identificacion").value;
	var fechaIni = $('#fechaIni').datepicker().val();
	var fechaFin = $('#fechaFin').datepicker().val();
	var factura = document.getElementById("numFactura").value;

	
	
		$.ajax({
			type: 'POST',
			url: 'BuscarFacturaServlet',
			data: {nombre: nombre , identificacion: identificacion, fechaIni: fechaIni, fechaFin: fechaFin, factura: factura} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
				var separado = response.split(":");
				
				document.getElementById("table_facturas").innerHTML = "";
				$('table[id=table_facturas]').append('<thead class="thead-dark"><tr><th width= "7%"> FACTURA No </th><th width= "12%">	FECHA FACTURA </th> <th width= "30%">	CLIENTE  </th> <th width= "10%">	TOTAL </th><th width= "2%">	</span> </th></tr></thead>');
					
				
				for(var i = 0; i< separado.length -1; i++){
					
					var numFactura = separado[i].split("|")[0];
					var fechaFactura = separado[i].split("|")[1];
					var nombre = separado[i].split("|")[2];
					var total = separado[i].split("|")[3];
					var idFactura = separado[i].split("|")[4];
					
					
					var fila = $("#table_facturas tr").length;
					$('table[id=table_facturas]').append('<tr ><td width= "10%" id=>'+numFactura+'</td><td width= "12%">'+fechaFactura+' </td><td width= "30%">'+nombre+'</td><td width= "10%">'+total+'</td><td width= "2%"><span class="glyphicon glyphicon-ok" onclick="borrar('+idFactura+')"></span></td></tr>')
				
					
				}
				
			}
		});
	})
});

</script>


<!-- script  llama servlet generar nota credito -->	

	
<!-- script creacion tabla resutlado de busqueda -->	
<script>

function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

	function borrar(valor){
	var fecha = "";
	//fecha = $('#fecha').datepicker({ dateFormat: 'dd/MM/yyyy' }).val();
	
	$.ajax({
			type: 'POST',
			url: 'BuscarNota',
			data: {idFactura: valor} ,
			statusCode: {
				404: function(){
					alert("pagina no encontrada ValorProductoServlet contacte con soporte si el problema persiste");
				},
				500: function(){
					alert("Debe seleccionar todos los items Obligatorios");
				}
			},
			success: function(response){
				var separado = response.split("|");
				
				var jsonFactura = JSON.parse(separado[0]);
				var jsonCliente = JSON.parse(separado[1]);
				var jsonDetalle = JSON.parse(separado[2]);
				var jsonProducto = JSON.parse(separado[3])
				
				
				
				
				
				var cliente= jsonCliente["cliNombre"] + " "+ jsonCliente["cliSegundoNombre"] + " " + jsonCliente["cliApellidos"];
				var identificacion= jsonCliente["cliNumIdent"];
				var direccion= jsonCliente["cliDireccion"];
				var telefono= jsonCliente["cliTelefono"];
				var email = jsonCliente["cliEmail"];
				var fecha = jsonFactura["facFechaFactura"];
				var total = jsonFactura["facTotal"];
				var subtotal = jsonFactura["facSubTotal"];
				var iva = jsonFactura["facIva"];
				var facturaNumero = jsonFactura["facNumFactura"];
				
				
				
				document.getElementById("facturaNum").innerHTML= facturaNumero;
				document.getElementById("clientefac").innerHTML= cliente;
				document.getElementById("identifac").innerHTML= identificacion;
				document.getElementById("dirfac").innerHTML= direccion;
				document.getElementById("telfac").innerHTML= telefono;
				document.getElementById("fechafac").innerHTML= formatDate(fecha);
				document.getElementById("emailfac").innerHTML= email;
				document.getElementById("subtotalf").value = subtotal;
				document.getElementById("ivaf").value = iva;
				document.getElementById("totalf").value = total;
										
				
				document.getElementById("table_productosf").innerHTML="<thead class='thead-dark'><tr><th width= '7%'> CANT </th><th width= '12%'>	REFERENCIA </th> <th width= '10%'>	MARCA  </th> <th width= '29%'>	DESCRIPCION </th><th width= '10%'>	MEDIDA </th><th width= '14%'>VALOR UNITARIO </th><th width= '14%'>VALOR TOTAL </th></tr></thead>";
	
	
				for (var i = 0 ; i< jsonDetalle.length ; i++) {
				   
					var cantidad = jsonDetalle[i]["fadCantidad"];
					var producto = jsonProducto[i]["producto"];
					var referencia = jsonProducto[i]["referencia"];
					var tipoProducto = jsonProducto[i]["tipoProducto"];
					var marca = jsonProducto[i]["marca"];
					var medida = jsonProducto[i]["medida"];
					var valorUnitario = jsonDetalle[i]["fadValorUnitario"];
					var total = jsonDetalle[i]["fadSubtotal"];
			
					
					var fila = $("#table_productosf tr").length;
					$('table[id=table_productosf]').append('<tr ><td width= "7%" id=>'+cantidad+'</td><td width= "12%">'+referencia+' </td><td width= "10%">'+marca+'</td><td width= "29%">'+tipoProducto + " " +producto+'</td><td width= "10%">'+medida+'</td><td width= "14%">'+valorUnitario+'</td><td width= "14%">'+total+'</td></tr>')
				
					//var 
					
				}
				
		
			}
		});
	
	//var factu ="0004215";

	 $('#modalResumenFactura').modal('show');
	 
	
	 

	}
	

	//}

</script>	

<script type="text/javascript">
function pasarVariables(pagina) {
  variable = document.getElementById("facturaNum").innerHTML;
  pagina +="?factura="+ variable;
  //pagina = pagina.substring(0,pagina.length-1);
  location.href=pagina;
}</script>

	</head>
	<body>
	
	   <header> 
	<br>
	<div class="col-xs-12" >
	<table>
		<tr>
		<td width= "50%">
		</td>
			<td width= "50%">
	    		 <img src="./admin/SofActBlue.png" width="170px" height="50px">
	   		 
	    <td width= "50%">
	     <div><h3><%=request.getUserPrincipal().getName()%></h3></div>
	  	</td>
	  	</tr>
	</table>
	</div>
	</header>
	   
	
<nav class="navbar navbar-default sidebar" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>      
    </div>
    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li ><a href="home.jsp">Home<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>  
        <li ><a  href="./FacturaServlet">Facturas<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>        
        <li ><a href="FacturaPos.jsp">Facturas POS<span	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
        <li class="active"><a href="#">Notas Credito<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
        <li ><a href="./NotaDebitoServlet">Notas Debito<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
        <li ><a href="#">Reportes<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
        <li data-toggle="collapse" data-target="#service" class="collapsed" ><a >Administración<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
      		 <ul class="sub-menu collapse nav navbar-nav" id="service"> 
                  <li ><a href="NuevoCliente.jsp">Crear Cliente<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
                  <li ><a href="CrearUsuario.jsp">Crear Usuario<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
                  <li ><a href="crearProducto.jsp">Crear Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
                  <li><a href="UpdateProducto.jsp">Actualizar Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
                  <li ><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
                  <li ><a href="CrearMarca.jsp">Crear Marca<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
                  <li ><a href="CrearMedida.jsp">Crear Medida<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>
                  

      </ul>
    </div>
  </div>
</nav>
	    
	     
	     <article style= "height: 1450px">
	     <br>
	     <h3>CREAR NOTA CREDITO</h3>
	   	 <HR />
	    
	      <br>
	     
	     <table id= "tabla_form" >
	     	<tr>
	     		<td><label for="sel1">Nombre: </label>
	     		</td>
	     		<td>
					<input type="text" class="form-control" id="primerNombre" name="primerNombre">				
	     		</td>
	     		
	     		<td> <label for="usr">Identificacion:  </label>
	     		</td>
	     		<td><table><tr>
					 <td>
					<input type="text" class="form-control" id="identificacion" name="identificacion" >
						   
					</td></tr>
					</table>
	     		</td>
	     	</tr>
	     	
	     	<!-- inicia segunda linea del formulario -->
	     	<tr>
	     		<td><label for="usr"><span class = "rojo"> * </span> Fecha de Inicio: </label></td>
				<td><input type="text" class="form-control" id="fechaIni" name="fechaIni" /></td>
	     		<td><label for="usr"><span class = "rojo"> * </span>Fecha Fin: </label></td>
				<td><input type="text" class="form-control" id="fechaFin" name="fechaFin" /></td>
	     	</tr>
	     	
	     	<!-- inicia segunda linea del formulario -->
	     	<tr>
	     		<td><label for="sel1">Numero de Factura: </label> </div>
	     		</td>
	     		<td> <input type="text" class="form-control" id="numFactura" name="numFactura">
	     		</td>
	     		
	     	</tr>
	     
	     </table >
	    
	   <div>
		<a id="buscar" name="buscar" class="btn btn-info btn-sm">
			<div class="glyphicon glyphicon-list-alt"></div> BUSCAR
		</a>
	</div>
	     
		
		 <hr />
		 
		<div class="table-responsive" id="div_tabla">
			<div class="col-xs-12">
				<table class="table table-bordered" id="table_facturas">
				</table>
			</div>
		</div>
		 
	     </article>
	     <aside> 
	     </aside>
	     <footer> POWERED BY: <br>
	     		JULIAN JAIMES<br>
	     		JHUALC<br>
	     </footer> 
	     
	   
	<!-- Modal Resume factura antes de imprimir-->


	<div class="modal fade" id="modalResumenFactura" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div>
					<img src="./img/encabezado.png" width="80%"></img>
				</div>
				<form>
					<div class="modal-body">
						<table id="tabla_form">
							<tr>
								<td>
									<label id="numFac" >Factura No:</label>
								</td>
								<td>
									<label id="facturaNum" name="facturaNum"></label>
								</td>
							</tr>
							<tr>
								<td><label for="sel1">Cliente: </label></td>
								<td><label id="clientefac" name="clientefac"></label></td>
								<td><label for="usr">Identificacion: </label></td>
								<td><label id="identifac"> </label></td>

							</tr>
							<!-- inicia segunda linea del formulario -->
							<tr>
								<td><label id="sel1">Direccion: </label></td>
								<td><label id="dirfac"></label></td>
								<td><label id="usr">Telefono: </label></td>
								<td><label id="telfac"></label></td>
							</tr>
							<tr>
								<td><label id="sel1">Email: </label></td>
								<td><label id="emailfac"></label></td>
								<td><label id="usr">Fecha Facturación: </label></td>
								<td><label id="fechafac"></label></td>
							</tr>
						</table>
						<div class="table-responsive" id="div_tablaf">
							<div class="col-xs-12">
								<table class="table table-bordered" id="table_productosf">
									<thead class="thead-dark">
										<tr>
											<th width="7%">CANT</th>
											<th width="12%">REFERENCIA</th>
											<th width="10%">MARCA</th>
											<th width="29%">DESCRIPCION</th>
											<th width="10%">MEDIDA</th>
											<th width="14%">VALOR UNITARIO</th>
											<th width="14%">VALOR TOTAL</th>
											<th width="0%"> </th>
										</tr>
									</thead>

								</table>
							</div>
						</div>
						<div class="col-xs-12">
							<table>
								<tr>
									<td width="70%"></td>
									<td width="15%"><h6>SUBTOTAL $</h6></td>
									<td width="15%"><input type="text" class="form-control"
										align="right" id="subtotalf" value="200.000" readonly>
									</td>
								</tr>
								<tr>
									<td width="70%"></td>
									<td width="15%"><h6>IVA $</h6></td>
									<td width="15%"><input type="text" class="form-control"
										align="right" id="ivaf" style="align: right" value="20.000"
										readonly></td>
								</tr>
								<tr>
									<td width="70%"></td>
									<td width="15%"><h6>TOTAL $</h6></td>
									<td width="15%"><input type="text" class="form-control"
										align="right" id="totalf" style="align: right" value="220.000"
										readonly></td>
								</tr>
							</table>
						</div>


						<div class="modal-footer">
							<button type="button" class="btn btn-secondary"
								data-dismiss="modal">Regresar</button>
							<a href="javascript:pasarVariables('GenerarNotaCredito.jsp')"+ factu><input type="button" class="btn btn-primary" id="genera" name="genera" value ="Generar Nota Credito"></a>
						</div>

					</div>
				</form>
			</div>

		</div>
	</body>	
</html>