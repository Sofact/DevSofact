
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




<script>

//************************************************************************************************************
// carga valores para session script
//************************************************************************************************************

$(document).ready(function(){
	
		$.ajax({
			type: 'GET',
			url: 'PropertiesServlet',
			data: 'i= ',
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en servidor");
				}
			},
			success: function(res){
				
				
				sessionStorage.setItem('iva', res);
			}
			
		});
		
});
</script>



<!-- scripts autocompletar y carga de selects -->


<script>

//************************************************************************************************************
// funcion formateo de fechas
//************************************************************************************************************


function formatDate(date) {
    var d = new Date(date),
        month = '' + (d.getMonth() + 1),
        day = '' + d.getDate(),
        year = d.getFullYear();

    if (month.length < 2) month = '0' + month;
    if (day.length < 2) day = '0' + day;

    return [year, month, day].join('-');
}

//************************************************************************************************************
// carga valores para session script
//************************************************************************************************************



$(document).ready(function(){
	
		cadVariables = location.search.substring(1,location.search.length);
		
		if 	(cadVariables != "enviar="){
						
		$.ajax({
			type: 'GET',
			url: 'BuscarFacturaServlet',
			data: cadVariables,
			statusCode: {
				404: function(){
					alert("Pagina no encontrada");
				},
				500: function(){
					alert("Error en servidor");
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
				var cliId = jsonCliente["cliId"];
				var fecha = jsonFactura["facFecaPago"];
				var total = jsonFactura["facTotal"];
				var subtotal = jsonFactura["facSubTotal"];
				var iva = jsonFactura["facIva"];
				var facturaNumero = jsonFactura["facNumFactura"];
				
				
				
				
				document.getElementById("numeroFactura").innerHTML= facturaNumero;
				document.getElementById("nombre").innerHTML= cliente;
				document.getElementById("identificacion").innerHTML= identificacion;
				document.getElementById("direccion").innerHTML= direccion;
				document.getElementById("telefono").innerHTML= telefono;
				document.getElementById("fechaFin").innerHTML= formatDate(fecha);
				document.getElementById("email").innerHTML= email;
				document.getElementById("subtotal").value = subtotal;
				document.getElementById("iva").value = iva;
				document.getElementById("total").value = total;
				document.getElementById("cliId").value = cliId;
				
				
				
				
				var fila = 0;
				for (var i = 0 ; i< jsonDetalle.length ; i++) {
				   
					var cantidad = jsonDetalle[i]["fadCantidad"];
					var producto = jsonProducto[i]["producto"];
					var referencia = jsonProducto[i]["referencia"];
					var tipoProducto = jsonProducto[i]["tipoProducto"];
					var marca = jsonProducto[i]["marca"];
					var medida = jsonProducto[i]["medida"];
					var valorUnitario = jsonDetalle[i]["fadValorUnitario"];
					var total = jsonDetalle[i]["fadSubtotal"];
					var pvmId = jsonDetalle[i]["pvmId"];
					
					fila = $("#table_productos tr").length  
			
					$('table[id=table_productos]').append('<tr ><td width= "7%" id=>'+cantidad+'</td><td width= "12%">'+referencia+' </td><td width= "10%">'+marca+'</td><td width= "29%">'+tipoProducto + " " +producto+'</td><td width= "10%">'+medida+'</td><td width= "14%">'+valorUnitario+'</td><td width= "14%">'+total+'</td><td width= "3%"><span class="glyphicon glyphicon-remove" id="delete")"></span></td><td style="display: none;">'+pvmId+'</td></tr>')
				
					//var 
					
				}
				
			}
			
		});
		}else{
			location.href ="/DevSofAct/NotaCredito.jsp";
		}
});


$(document).on('click', '#delete', function (event) {
    event.preventDefault();
    $(this).closest('tr').remove();
    var total= 0;
    $('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total = total + parseFloat($(this).text());
								
							}
						})
					})
					
					
					document.getElementById('total').value = total;
					document.getElementById('iva').value = total * sessionStorage.getItem('iva');
					document.getElementById('subtotal').value = total - (total * sessionStorage.getItem('iva'));
});

function borrar(valor){
        
        	
        	document.getElementById("table_productos").deleteRow(valor);
        	var total= 0;
        	$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total = total + parseFloat($(this).text());
								
							}
						})
					})
					
					
					document.getElementById('total').value = total;
					document.getElementById('iva').value = total * sessionStorage.getItem('iva');
					document.getElementById('subtotal').value = total - (total * sessionStorage.getItem('iva'));
        	//$('#agregar_registro').show(3000);       
        	
        	location.href ="#";
        	 }
</script>



<!-- scripts Save factura -->
<script>
$(document).ready(function(){
	$('button[id=enviar]').on('click', function(){
	
	

	var detalles = [];
	

	var contador =0;
	
	$('#table_productosf tr').each(function (index){
		var cant, ref, marca, descrip, medida, valor, total, pvmid;
		
		$(this).children("td").each(function(index2){
		
		
			switch(index2){
				case 0:
					cant= $(this).text();
					
					break;
				case 1:
					ref= $(this).text();
					break;
				case 2:
					marca= $(this).text();
					break;
				case 3:
					descrip= $(this).text();
					break;	
				case 4:
					medida= $(this).text();
					break;	
				case 5:
					valor= $(this).text();
					break;
				case 6:
					total= $(this).text();
					break;
				case 7:
					pvmid= $(this).text();
					break;
				
			}
		
			//alert("hasta antes del JSon " +$(this).text() + "contador: " + contador);
			
		//	alert("hasta aca llega y sigue");
		})
		//subtot = valor - (valor * (descuento /100));
		if (contador == 0){
			contador++;
		}else{
			
			//alert("Dentro del else:" +valor);
			detalles.push ({
				"cantidad" 	: cant,
				"total"		: total,
				"pvmid"		: pvmid,
			//	"descuento" : descuento,
				"subtotal"	: valor
			});
			
		//	alert("Saliendo del else");
			}
			
		// jsonFactura.detalles.push({pvid: pvmid ,cantidad: cant, tot: total});
		})
		
		
		var jsonFactura = JSON.stringify(eval({
						
						subtotal: $('#subtotal').val(),
						facturaNum: $('#numeroFactura').text(),
						cliente: $('#cliId').val(),
						total: $('#total').val(),
						iva: $('#iva').val(),
						fecha: $('#fechaFin').text() ,
						observacion: $('#observacion').val(),
						detalleFactura : detalles
						}))
						
						
		//jsonFactura.push(detalles);
		//alert ("si se pudo" + );
		
		$.ajax({
			type: 'POST',
			url: 'SaveNotaServlet',
			dataType: 'json',
			data: jsonFactura , 
			statusCode: {
				404: function(){
					alert("pagina no encontrada AlorProductoServlet");
				},
				500: function(){
					alert("Error servidor ValorProductoServlet");
				}
			},
			success: function(response){
				alert (response);
				
				 
			}
			
		});
		//}
	})
	
	
	
});

</script>


<!-- script carga Nota credito  generada -->
<script>
$(document).ready(function(){
	$('a[id=generar]').on('click', function(){
	
	fila = $("#table_productos tr").length  ;
	
	if(fila == 0){
		 $('#modalResumenFactura').modal('hide');
		alert ("Debe existir almenos un detalle apra generar una nota credito");
		
		}
	else{
	 $('#modalResumenFactura').modal('show');

	var cliente= $('#nombre').text();
	var identificacion= $('#identificacion').text();
	var direccion= $('#direccion').text();
	var telefono= $('#telefono').text();
	var fecha = $('#fechaFin').text();
	var email = $('#email').text();
	var cliId = document.getElementById("cliId").value;
	var observacion = $('#observacion').val();
	var cont = 0;
	
	
	
	
	document.getElementById("clientefac").innerHTML= cliente;
	document.getElementById("identifac").innerHTML= identificacion;
	document.getElementById("dirfac").innerHTML= direccion;
	document.getElementById("telfac").innerHTML= telefono;
	document.getElementById("fechafac").innerHTML= fecha;
	document.getElementById("emailfac").innerHTML= email;
	document.getElementById("observacionf").innerHTML = observacion;
	document.getElementById("cliIdfac").innerHTML = cliId;
	
	
	
	
	document.getElementById("table_productosf").innerHTML="<thead class='thead-dark'><tr><th width= '7%'> CANT </th><th width= '12%'>	REFERENCIA </th> <th width= '10%'>	MARCA  </th> <th width= '29%'>	DESCRIPCION </th><th width= '10%'>	MEDIDA </th><th width= '14%'>VALOR UNITARIO </th><th width= '14%'>VALOR TOTAL </th></tr></thead>";
	//$('#table_productos')
	$('#table_productos tr').each(function (index){
		var cant, ref, marca, descrip, medida, valor, total;
		$(this).children("td").each(function(index2){
			switch(index2){
				case 0:
					cant= $(this).text();
					break;
				case 1:
					ref= $(this).text();
					break;
				case 2:
					marca= $(this).text();
					break;
				case 3:
					descrip= $(this).text();
					break;	
				case 4:
					medida= $(this).text();
					break;	
				case 5:
					valor= $(this).text();
					break;
				case 6:
					total= $(this).text();
					break;
				case 8:
					pvmId= $(this).text();
					break;
				
			}
		})
		if(cont > 0){
		
			
		
			$('table[id=table_productosf]').append('<tr ><td width= "10%" id=>'+cant+'</td><td width= "12%">'+ref+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+descrip+'</td><td width= "10%">'+medida+'</td> <td width= "10%">'+valor+'</td><td width= "14%">'+total+'</td><td style="display: none;">'+pvmId+'</td></tr>')
			
		}cont++;
	})
	document.getElementById("subtotalf").value = document.getElementById("subtotal").value;
	document.getElementById("ivaf").value = document.getElementById("iva").value;
	document.getElementById("totalf").value = document.getElementById("total").value;
	}
	})
});
</script>


<!-- scripts llenado tabla de factura -->
<script>
$(document).ready(function(){
	$('a[id=addValor]').on('click', function(){
	
	var tipo_p = "NOTA";
	var medi = "UNIDAD";
	var cantidad = "1";
	var marca = "N/A";

	
	var producto = "CREDITO";
	var referencia = "N/CREDITO";
	var pvmId = 1;
	var valorDescuento = document.getElementById("totalNota").value;
	var total = document.getElementById("totalNota").value;
					
	var fila = $("#table_productos tr").length;
	$('table[id=table_productos]').append('<tr ><td width= "10%" id=>'+cantidad+'</td><td width= "12%">'+referencia+' </td><td width= "12%">'+marca+'</td><td width= "30%">'+tipo_p + " "+producto+ ' </td><td width= "10%">'+medi+'</td> <td width= "10%">'+valorDescuento+'</td><td width= "14%">'+total+'</td><td width= "7%"><span class="glyphicon glyphicon-remove" id="delete" ></span></td><td style="display: none;">'+pvmId+'</td></tr>')
				
	var total= 0;
				$('#table_productos tr').each(function (index){
						
						
						$(this).children('td').each(function (index2){
							
							if(index2 == 6){
								total = total + parseFloat($(this).text());
								//alert (total);
							}
						})
					})
					document.getElementById('total').value = total;
					document.getElementById('iva').value = total * sessionStorage.getItem('iva');
					document.getElementById('subtotal').value = total - (total * sessionStorage.getItem('iva'));
	
		
	})
});

</script>


</head>
<body>

	<header> SofAct

	<p>
		
		 <p>Usuario: <%=request.getUserPrincipal().getName()%></p>  
	</header>


	<nav class=" navbar navbar-default sidebar" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-sidebar-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-sidebar-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li><a href="home.jsp">Home<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
				<li ><a href="./FacturaServlet">Facturas<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li ><a href="FacturaPos.jsp">Facturas POS<span	style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-list-alt"></span></a></li>
				<li ><a href="NotaCredito.jsp">Notas Credito<span style="font-size: 16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>
				<li><a href="./NotaDebitoServlet">Notas Debito<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
				<li><a href="#">Reportes<span style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-usd"></span></a></li>
				<li data-toggle="collapse" data-target="#service" class="collapsed"><a>Administración<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-cog"></span></a></li>
				<ul class="sub-menu collapse nav navbar-nav" id="service">
					<li><a href="NuevoCliente.jsp">Crear Cliente<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-globe"></span></a></li>
					<li><a href="crearProducto.jsp">Crear Usuario<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a></li>
					<li><a href="crearProducto.jsp">Crear Producto<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-shopping-cart"></span></a></li>
					<li><a href="CrearTipoProducto.jsp">Crear Tipo Producto<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-barcode"></span></a></li>
					<li><a href="CrearMarca.jsp">Crear Marca<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-eye-open"></span></a></li>
					<li><a href="CrearMedida.jsp">Crear Medida<span
							style="font-size: 16px;"
							class="pull-right hidden-xs showopacity glyphicon glyphicon-wrench"></span></a></li>


				</ul>
			</ul>
		</div>
	</div>
	</nav>


	<article style="height:1170px"> <br>
	<br>

	<table id="tabla_form">
		<tr>
			<td><label for="self">Factura No: </label></td>
			<td><label id="numeroFactura" name="numeroFactura"></label>
			
			
		</tr>
		<tr>
			<td><label for="sel1">Nombre: </label></td>
			<td><label id="nombre" name="nombre"></label>
			
			<td><label for="usr">Identificacion: </label></td>
			<td><label  id="identificacion" name= "identificacion"> </label></td>
			<td style="display: none;" ><label id="cliId" ></label><td>
		</tr>
		<!-- inicia segunda linea del formulario -->
		<tr>
			<td><label for="sel1">Direccion: </label>
				</td>
			<td><label id="direccion" name="direccion"></label>
			</td>
			<td><label for="usr">Telefono: </label></td>
			<td><label id="telefono" name="telefono"></label>
			</td>
		</tr>
		
		<!-- inicia cuarta linea del formulario -->
		<tr>
			<td><label for="sel1">e-Mail: </label>
				</td>
			<td><label id="email" name="email"></label>
			</td>
			<td><label for="usr">Fecha de Facturación: </label></td>
			<td><label id="fechaFin" name="fechaFin"></label></td>
		</tr>
	</table>

	

	<hr />
	
	
	<div class="table-responsive" id="div_tabla">
		<div class="col-xs-12">
			<table class="table table-bordered" id="table_productos">
				<thead class="thead-dark">
					<tr>
						<th width="7%">CANT</th>
						<th width="12%">REFERENCIA</th>
						<th width="10%">MARCA</th>
						<th width="30%">DESCRIPCION</th>
						<th width="10%">MEDIDA</th>
						<th width="14%">VALOR UNITARIO</th>
						<th width="14%">VALOR TOTAL</th>
						<th width="5%">OPT</th>
					</tr>
				</thead>

			</table>
		</div>
	</div>
	
	<div class="col-xs-12">
		<table align="center">
			<tr>
				<td>
					<h3>Descripcion:</h3>
				</td>
			</tr>
			<tr>
				<td>
					<textarea rows="8" cols="100" id="observacion"></textarea>
				</td>
			</tr>
			<tr>
				<td>
					<table>
						<tr>
							<td>
								<h4>Valor Adicional Nota Crédito:</h4>
							</td>
							<td>
								<input type="text" class="form-control" id="totalNota">
							</td>
							<td>
								<a id="addValor" name="addValor" class="btn btn-info btn-sm">
									<div class="glyphicon glyphicon-ok"></div> Agregar Valor
								</a>
							</td>
						</tr>	
					</table>
				</td>
				
			</tr>
		</table>
	</div>
	
	<div class="col-xs-12">
		<table>
			<tr>
				<td width="70%"></td>
				<td width="15%"><h5>SUBTOTAL $</h5></td>
				<td width="15%"><input type="text" class="form-control"
					align="right" id="subtotal" readonly></td>
			</tr>
			<tr>
				<td width="70%"></td>
				<td width="15%"><h5>IVA $</h5></td>
				<td width="15%"><input type="text" class="form-control"
					align="right" id="iva" style="align: right" readonly></td>
			</tr>
			<tr>
				<td width="70%"></td>
				<td width="15%"><h5>TOTAL $</h5></td>
				<td width="15%"><input type="text" class="form-control"
					align="right" id="total" style="align: right" readonly></td>
			</tr>
		</table>
	</div>
	<div>
		<a id="generar" name="generar" class="btn btn-info btn-sm">
			<div class="glyphicon glyphicon-list-alt"></div> Generar Nota Crédito
		</a>
	</div>


	</article>
	<aside> </aside>
	<footer> POWERED BY: <br>
	JULIAN JAIMES<br>
	JHUALC<br>
	</footer>



	<!-- Modal Resume factura antes de imprimir-->


	<div class="modal fade" id="modalResumenFactura" role="dialog"
		aria-labelledby="exampleModalLabel" aria-hidden="false">
		<div class="modal-dialog" role="document">
			<div class="modal-content">

				<div>
					<img src="./img/encabezado.png" width="80%"></img>
				</div>
				<form>
					<div class="modal-body">
						<table id="tabla_form">
							<tr>
								<td><label for="sel1">Cliente: </label></td>
								<td><label id="clientefac" name="clientefac"></label></td>
								<td><label for="usr">Identificacion: </label></td>
								<td><label id="identifac"> </label></td>
								<td style="display: none;"><label id="cliIdfac"> </label></td>

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
							<table align="center">
								<tr>
									<td>
										<h3>Descripcion:</h3>
									</td>
								</tr>
								<tr>
									<td>
										<label  id="observacionf"></label>
									</td>
								</tr>
								
							</table>
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
							<button class="btn btn-primary" id="enviar" name="enviar">Enviar</button>
						</div>

					</div>
				</form>
			</div>

		</div>
</body>





</html>